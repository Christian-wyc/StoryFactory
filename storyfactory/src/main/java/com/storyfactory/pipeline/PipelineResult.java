package com.storyfactory.pipeline;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 流水线执行结果
 */
@Data
@AllArgsConstructor
public class PipelineResult {
    private boolean success;
    private Object data;
    private String message;

    public static PipelineResult success(Object data) {
        return new PipelineResult(true, data, null);
    }

    public static PipelineResult failure(String message) {
        return new PipelineResult(false, null, message);
    }
}
