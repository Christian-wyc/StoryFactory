
# 一、全局鸟瞰图
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
        M2["2. 编剧模块 (The Architect)<br/>[题材 -> 角色图谱 & 三层大纲]"]:::core
        M3["3. 生产引擎 (The Engine)<br/>[大纲 -> 分镜拆解 -> 正文生成]"]:::core
        M5["5. 精修车间 (The Workshop)<br/>[人机协作断点 & 分支回滚]"]:::core
        M6["6. 物流模块 (The Publisher)<br/>[多平台分发 & 排版]"]:::core
    end

    %% 底层数据与状态中心 (v2.0 升级版)
    subgraph "底层数字孪生基座 (Data Infrastructure v2.0)"
        M4[("4. 记忆与状态库 (The Brain)<br/>- 长期记忆 (Milvus 设定集)<br/>- 短期会话 (Redis 上下文)<br/>- WSM 状态机 (ArangoDB 图谱/事件流)")]:::brain
    end

    %% 终点
    Readers((全网读者)):::output

    %% 数据流向连线 (主流程)
    User -- "提供初步灵感/关键词" --> M1
    M1 -- "输出：确定的世界观标签 & 商业化缝合方向" --> M2
    M2 -- "输出：严密的卷纲、章纲、初始角色图谱" --> M3
    M3 -- "输出：初稿文本 (按分镜生成)" --> M5
    M5 -- "输出：定稿的最终章节" --> M6
    M6 -- "一键发布" --> Readers

    %% 与 The Brain 的双向交互 (核心中枢)
    M2 <== "写入: 初始实体快照与关系网 (ArangoDB)<br/>写入: 宏观世界观长文 (Milvus)" ==> M4
    M3 <== "读取: 实体嗅探 + 图谱扩散提取精简上下文<br/>写入: 追加状态变更事件流水 (Event Sourcing)" ==> M4
    M5 <== "读取: 剧情多分支树状图<br/>指令: 废弃后续事件流水，实现无损状态回滚" ==> M4

---
# 二、 核心车间的数据交接清单（接口契约）
为了确保开发时不偏移，我们需要明确每个模块“吃进去什么”和“吐出来什么”。这就是系统串联的骨架：

## 1. 神谕 (The Oracle) ➔ 交接给 ➔ 编剧 (The Architect)
   输入：作者的一句模糊想法（例：“我想写一个赛博朋克加修仙的故事”）。

内部动作：抓取网文热榜，分析标签，评估商业潜力。

输出（JSON 契约）：核心卖点标签 (Tags)、建议的升级体系、预期读者爽点分布。

## 2. 编剧 (The Architect) ➔ 交接给 ➔ 生产与记忆 (Engine & Brain)
   输入：神谕模块传来的设定基调。

内部动作：调用模型生成详尽的世界观，建立具备“性格缺陷”和“初始属性”的角色卡，并生成 L1(卷)、L2(章)、L3(分镜) 级别的递归大纲。

输出动作：

向 The Brain 写入：全套的初始状态机数据（此时主角刚出生，0级，没装备），并将长篇设定存入 Milvus 向量库。

向 The Engine 下发：当前需要执行的“分镜大纲”。

## 3. 生产引擎 (The Engine) ⇔ 联动 ⇔ 记忆库 (The Brain)
   这是整个工厂最忙碌的“活塞运动”。

输入：当前的分镜任务（如：“林动在悬崖边反杀王腾”）。

## 内部动作：

从 The Brain 拉取“林动”和“王腾”当前的 HP、等级、手里拿的武器。

双模型博弈（DeepSeek 负责逻辑梳理，Qwen 负责华丽文笔）生成正文。

输出动作：

将生成的 800 字文本交给精修车间。

向 The Brain 写入状态变更（如：更新王腾的状态为“死亡”，主角经验值+100）。

## 4. 精修车间 (The Workshop) ⇔ 控制 ⇔ 全局
   定位：这是系统唯一的“人工干预台”，带有前端可视化界面。

内部动作：作者在这里阅读引擎生成的文本。如果满意，点击“确认入库”；如果不满意，直接在界面上修改提示词重写，或者触发“状态回滚”。

## 5. 物流 (The Publisher)
   输入：精修车间“确认入库”的最终文本。

动作：合规性扫描、敏感词替换、自动排版，最后通过 API 分发。