package com.storyfactory.pipeline;

import lombok.Getter;

/**
 * 流水线执行异常
 */
@Getter
public class PipelineException extends Exception {
    private final String module;
    private final String detail;

    public PipelineException(String module, String message) {
        super(String.format("[%s] %s", module, message));
        this.module = module;
        this.detail = message;
    }
}
