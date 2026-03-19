package com.storyfactory.architect.service;

import com.storyfactory.architect.entity.CharacterCard;
import com.storyfactory.architect.entity.WorldSetting;
import com.storyfactory.architect.repository.CharacterCardRepository;
import com.storyfactory.architect.repository.WorldSettingRepository;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContextAssembler {

    @Autowired
    private WorldSettingRepository worldSettingRepository;

    @Autowired
    private CharacterCardRepository characterCardRepository;

    @Data
    @Builder
    public static class ArchitectContext {
        private String worldSettings;
        private String characters;
    }

    public ArchitectContext assembleContext(Long novelId) {
        List<WorldSetting> worldSettings = worldSettingRepository.findByNovelId(novelId);
        String settingsStr = worldSettings.stream()
                .map(s -> s.getType() + ":" + s.getName() + "(" + s.getAttributes() + ")")
                .collect(Collectors.joining("; "));

        List<CharacterCard> characterCards = characterCardRepository.findByNovelId(novelId);
        String charactersStr = characterCards.stream()
                .map(c -> c.getName() + "(角色类型: " + c.getRoleType() + ", 动机: " + c.getMotivation() + ")")
                .collect(Collectors.joining("; "));

        return ArchitectContext.builder()
                .worldSettings(settingsStr)
                .characters(charactersStr)
                .build();
    }
}
