graph TD
%% 定义样式
classDef user fill:#f9f9f9,stroke:#333,stroke-width:2px;
classDef core fill:#e1f5fe,stroke:#0288d1,stroke-width:2px;
classDef brain fill:#fff3e0,stroke:#f57c00,stroke-width:2px,stroke-dasharray: 5 5;
classDef output fill:#e8f5e9,stroke:#388e3c,stroke-width:2px;

    %% 外部输入
    User((作者/创作者)):::user

    %% 核心流水线 (自上而下)
    subgraph "StoryFactory 核心生产流水线 (Agentic Workflow)"
        M1["1. 神谕模块 (The Oracle)<br/>[市场数据 -> 爆款题材评测]"]:::core
        M2["2. 编剧模块 (The Architect)<br/>[题材 -> 角色卡 & 三层大纲]"]:::core
        M3["3. 生产引擎 (The Engine)<br/>[大纲 -> 分镜拆解 -> 正文生成]"]:::core
        M5["5. 精修车间 (The Workshop)<br/>[人机协作断点 & 分支回滚]"]:::core
        M6["6. 物流模块 (The Publisher)<br/>[多平台分发 & 排版]"]:::core
    end

    %% 底层数据与状态中心
    subgraph "底层数字孪生基座 (Data Infrastructure)"
        M4[("4. 记忆与状态库 (The Brain)<br/>- 长期记忆 (Milvus 设定集)<br/>- 短期记忆 (Redis 上下文)<br/>- WSM 状态机 (MySQL 实体/环境)")]:::brain
    end

    %% 终点
    Readers((全网读者)):::output

    %% 数据流向连线 (主流程)
    User -- "提供初步灵感/关键词" --> M1
    M1 -- "输出：确定的世界观标签 & 商业化缝合方向" --> M2
    M2 -- "输出：严密的卷纲、章纲、角色数据" --> M3
    M3 -- "输出：初稿文本 (按分镜生成)" --> M5
    M5 -- "输出：定稿的最终章节" --> M6
    M6 -- "一键发布" --> Readers

    %% 与 The Brain 的双向交互 (核心中枢)
    M2 <== "写入: 初始角色属性、大纲设定<br/>读取: 题材背景" ==> M4
    M3 <== "读取: 前情提要、当前实体状态<br/>写入: 状态变更事件 (Function Calling)" ==> M4
    M5 <== "读取: 剧情树枝<br/>指令: 触发状态机回滚" ==> M4