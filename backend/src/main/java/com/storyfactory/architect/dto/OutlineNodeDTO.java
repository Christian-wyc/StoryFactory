package com.storyfactory.architect.dto;

import lombok.Data;
import java.util.Map;

@Data
public class OutlineNodeDTO {
    private String title;
    private String summary;
    private Map<String, Object> contextSnapshot;
}
