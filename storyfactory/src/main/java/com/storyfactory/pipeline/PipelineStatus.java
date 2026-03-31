package com.storyfactory.pipeline;

/**
 * 流水线状态枚举
 */
public enum PipelineStatus {
    PENDING,           // 待执行
    RUNNING,           // 执行中
    ORACLE_ANALYZING, // 神谕分析中
    ARCHITECT_PLANNING,// 编剧规划中
    ENGINE_GENERATING, // 引擎生成中
    WORKSHOP_REVIEWING,// 精修审查中
    PUBLISHING,        // 发布中
    COMPLETED,         // 完成
    FAILED             // 失败
}
