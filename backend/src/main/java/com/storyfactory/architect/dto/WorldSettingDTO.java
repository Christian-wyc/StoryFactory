package com.storyfactory.architect.dto;

import lombok.Data;
import java.util.Map;

@Data
public class WorldSettingDTO {
    private String type;
    private String name;
    private Map<String, Object> attributes;
}
