package com.storyfactory.architect.service;

import com.storyfactory.architect.dto.OutlineNodeDTO;
import com.storyfactory.architect.entity.Novel;
import com.storyfactory.architect.entity.OutlineNode;
import com.storyfactory.architect.repository.NovelRepository;
import com.storyfactory.architect.repository.OutlineNodeRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OutlineEngine {

    private final ChatClient chatClient;
    private final OutlineNodeRepository outlineNodeRepository;
    private final NovelRepository novelRepository;
    private final ContextAssembler contextAssembler;

    @Autowired
    public OutlineEngine(ChatClient.Builder builder, OutlineNodeRepository outlineNodeRepository,
                         NovelRepository novelRepository, ContextAssembler contextAssembler) {
        this.chatClient = builder.build();
        this.outlineNodeRepository = outlineNodeRepository;
        this.novelRepository = novelRepository;
        this.contextAssembler = contextAssembler;
    }

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public List<OutlineNode> generateVolumes(Long novelId) {
        Novel novel = novelRepository.findById(novelId).orElseThrow();
        ContextAssembler.ArchitectContext context = contextAssembler.assembleContext(novelId);

        var outputConverter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<OutlineNodeDTO>>() {});

        String prompt = """
                你是一个资深的网文主编。请根据小说梗概、世界观和角色设定，规划出全书的“卷纲”（Level 1）。
                通常长篇小说分为 3-5 卷，每卷都有独立的大高潮。
                
                小说标题: {title}
                小说梗概: {premise}
                世界观: {world}
                核心角色: {characters}
                
                请输出 JSON 格式的列表，包含 title, summary, contextSnapshot (存储该卷结束后的世界状态预测)。
                {format}
                """;

        List<OutlineNodeDTO> nodesDTO = chatClient.prompt()
                .user(u -> u.text(prompt)
                        .param("title", novel.getTitle())
                        .param("premise", novel.getPremise())
                        .param("world", context.getWorldSettings())
                        .param("characters", context.getCharacters())
                        .param("format", outputConverter.getFormat()))
                .call()
                .entity(new ParameterizedTypeReference<List<OutlineNodeDTO>>() {});

        return saveNodes(novelId, null, "VOLUME", nodesDTO);
    }

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public List<OutlineNode> splitToChapters(Long parentNodeId) {
        OutlineNode parentNode = outlineNodeRepository.findById(parentNodeId).orElseThrow();
        Long novelId = parentNode.getNovelId();
        ContextAssembler.ArchitectContext context = contextAssembler.assembleContext(novelId);

        var outputConverter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<OutlineNodeDTO>>() {});

        String prompt = """
                你是一个资深的网文主编。请将当前的“卷纲”拆解为具体的“章纲”（Level 2）。
                每一卷通常包含 15-20 章。请确保剧情连贯，起承转合自然。
                
                当前卷标题: {parentTitle}
                当前卷摘要: {parentSummary}
                世界观: {world}
                核心角色: {characters}
                
                请输出 JSON 格式的列表，包含 title, summary, contextSnapshot。
                {format}
                """;

        List<OutlineNodeDTO> nodesDTO = chatClient.prompt()
                .user(u -> u.text(prompt)
                        .param("parentTitle", parentNode.getTitle())
                        .param("parentSummary", parentNode.getSummary())
                        .param("world", context.getWorldSettings())
                        .param("characters", context.getCharacters())
                        .param("format", outputConverter.getFormat()))
                .call()
                .entity(new ParameterizedTypeReference<List<OutlineNodeDTO>>() {});

        return saveNodes(novelId, parentNodeId, "CHAPTER", nodesDTO);
    }

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public List<OutlineNode> splitToBeats(Long parentNodeId) {
        OutlineNode parentNode = outlineNodeRepository.findById(parentNodeId).orElseThrow();
        Long novelId = parentNode.getNovelId();
        ContextAssembler.ArchitectContext context = contextAssembler.assembleContext(novelId);

        var outputConverter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<OutlineNodeDTO>>() {});

        String prompt = """
                你是一个资深的分镜导演。请将当前的“章纲”拆解为极细致的“分镜/节纲”（Level 3 / Beats）。
                每一章通常包含 5-8 个分镜。每个分镜应包含具体的画面描述、出场人物和核心冲突。
                这些分镜将直接指导 AI 进行最终的正文创作。
                
                当前章标题: {parentTitle}
                当前章摘要: {parentSummary}
                世界观: {world}
                核心角色: {characters}
                
                请输出 JSON 格式的列表，包含 title, summary, contextSnapshot。
                {format}
                """;

        List<OutlineNodeDTO> nodesDTO = chatClient.prompt()
                .user(u -> u.text(prompt)
                        .param("parentTitle", parentNode.getTitle())
                        .param("parentSummary", parentNode.getSummary())
                        .param("world", context.getWorldSettings())
                        .param("characters", context.getCharacters())
                        .param("format", outputConverter.getFormat()))
                .call()
                .entity(new ParameterizedTypeReference<List<OutlineNodeDTO>>() {});

        return saveNodes(novelId, parentNodeId, "BEAT", nodesDTO);
    }

    private List<OutlineNode> saveNodes(Long novelId, Long parentId, String type, List<OutlineNodeDTO> dtos) {
        List<OutlineNode> nodes = dtos.stream().enumerate().map(pair -> {
            int index = pair.index();
            OutlineNodeDTO dto = pair.value();
            OutlineNode node = new OutlineNode();
            node.setNovelId(novelId);
            node.setParentId(parentId);
            node.setNodeType(type);
            node.setOrderIndex(index);
            node.setTitle(dto.getTitle());
            node.setSummary(dto.getSummary());
            node.setContextSnapshot(dto.getContextSnapshot());
            return node;
        }).collect(Collectors.toList());

        return outlineNodeRepository.saveAll(nodes);
    }
}
