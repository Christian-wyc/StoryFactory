package com.storyfactory.architect.service;

import com.storyfactory.architect.dto.WorldSettingDTO;
import com.storyfactory.architect.entity.WorldSetting;
import com.storyfactory.architect.repository.WorldSettingRepository;
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
public class WorldBuilderAgent {

    private final ChatClient chatClient;
    private final WorldSettingRepository worldSettingRepository;

    @Autowired
    public WorldBuilderAgent(ChatClient.Builder builder, WorldSettingRepository worldSettingRepository) {
        this.chatClient = builder.build();
        this.worldSettingRepository = worldSettingRepository;
    }

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public List<WorldSetting> buildWorld(Long novelId, String premise) {
        var outputConverter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<WorldSettingDTO>>() {});

        String prompt = """
                你是一个专业的世界观架构师。根据小说的一句话梗概，推演出该小说的世界观设定。
                包括力量体系、地理环境、核心冲突等。
                小说梗概: {premise}
                
                请输出 JSON 格式的列表，每个对象包含 type, name, attributes 字段。
                {format}
                """;

        List<WorldSettingDTO> settingsDTO = chatClient.prompt()
                .user(u -> u.text(prompt)
                        .param("premise", premise)
                        .param("format", outputConverter.getFormat()))
                .call()
                .entity(new ParameterizedTypeReference<List<WorldSettingDTO>>() {});

        List<WorldSetting> settings = settingsDTO.stream().map(dto -> {
            WorldSetting setting = new WorldSetting();
            setting.setNovelId(novelId);
            setting.setType(dto.getType());
            setting.setName(dto.getName());
            setting.setAttributes(dto.getAttributes());
            return setting;
        }).collect(Collectors.toList());

        return worldSettingRepository.saveAll(settings);
    }
}
