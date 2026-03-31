package com.storyfactory.pipeline;

import java.util.Collections;
import java.util.List;

/**
 * 流水线模块统一接口
 * 所有模块必须实现此接口以参与流水线编排
 */
public interface PipelineModule {

    /**
     * 获取模块名称
     */
    String getName();

    /**
     * 执行模块功能
     * @param context 流水线上下文，包含前面模块的输出
     * @return 执行结果，包含输出数据和状态
     * @throws PipelineException 模块执行异常
     */
    PipelineResult execute(PipelineContext context) throws PipelineException;

    /**
     * 获取前置依赖的模块名称
     * @return 依赖模块名称列表
     */
    default List<String> getDependencies() {
        return Collections.emptyList();
    }

    /**
     * 获取模块执行中的状态
     */
    default PipelineStatus getRunningStatus() {
        return PipelineStatus.RUNNING;
    }
}
