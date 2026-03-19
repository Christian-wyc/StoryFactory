package com.storyfactory.architect.service;

import com.storyfactory.architect.dto.CharacterCardDTO;
import com.storyfactory.architect.entity.CharacterCard;
import com.storyfactory.architect.entity.WorldSetting;
import com.storyfactory.architect.repository.CharacterCardRepository;
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
public class CharacterAgent {

    private final ChatClient chatClient;
    private final CharacterCardRepository characterCardRepository;
    private final WorldSettingRepository worldSettingRepository;

    @Autowired
    public CharacterAgent(ChatClient.Builder builder, CharacterCardRepository characterCardRepository, WorldSettingRepository worldSettingRepository) {
        this.chatClient = builder.build();
        this.characterCardRepository = characterCardRepository;
        this.worldSettingRepository = worldSettingRepository;
    }

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public List<CharacterCard> generateCharacters(Long novelId, String premise) {
        List<WorldSetting> worldSettings = worldSettingRepository.findByNovelId(novelId);
        String settingsStr = worldSettings.stream()
                .map(s -> s.getType() + ":" + s.getName() + "(" + s.getAttributes() + ")")
                .collect(Collectors.joining("; "));

        var outputConverter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<CharacterCardDTO>>() {});

        String prompt = """
                你是一个专业的人物小传专家。根据小说梗概和世界观设定，设计出 3-5 个核心角色卡。
                每个角色必须有明确的动机、鲜明的性格标签和不为人知的秘密。
                
                小说梗概: {premise}
                世界观设定: {settings}
                
                请输出 JSON 格式的列表，每个对象包含 name, roleType, motivation, personalityTags (JSON数组), hiddenSecrets 字段。
                {format}
                """;

        List<CharacterCardDTO> charactersDTO = chatClient.prompt()
                .user(u -> u.text(prompt)
                        .param("premise", premise)
                        .param("settings", settingsStr)
                        .param("format", outputConverter.getFormat()))
                .call()
                .entity(new ParameterizedTypeReference<List<CharacterCardDTO>>() {});

        List<CharacterCard> characters = charactersDTO.stream().map(dto -> {
            CharacterCard card = new CharacterCard();
            card.setNovelId(novelId);
            card.setName(dto.getName());
            card.setRoleType(dto.getRoleType());
            card.setMotivation(dto.getMotivation());
            card.setPersonalityTags(dto.getPersonalityTags());
            card.setHiddenSecrets(dto.getHiddenSecrets());
            return card;
        }).collect(Collectors.toList());

        return characterCardRepository.saveAll(characters);
    }
}
