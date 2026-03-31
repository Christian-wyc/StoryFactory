package com.storyfactory.pipeline;

import lombok.Data;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 流水线上下文 - 模块间数据传递容器
 */
@Data
public class PipelineContext {
    private String pipelineId;
    private Map<String, Object> data = new HashMap<>();
    private PipelineStatus status;
    private Instant createdAt = Instant.now();

    public PipelineContext() {
        this.pipelineId = UUID.randomUUID().toString();
    }

    public void put(String key, Object value) {
        this.data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) this.data.get(key);
    }
}
