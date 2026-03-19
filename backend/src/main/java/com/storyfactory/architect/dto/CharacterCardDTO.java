package com.storyfactory.architect.dto;

import lombok.Data;
import java.util.List;

@Data
public class CharacterCardDTO {
    private String name;
    private String roleType;
    private String motivation;
    private List<String> personalityTags;
    private String hiddenSecrets;
}
