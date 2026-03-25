# 📖 StoryFactory - The Brain (记忆与状态库) 架构设计方案

## 一、 模块定位
在 StoryFactory 的工业化小说流水线中，**The Brain** 不是一个被动的静态数据库，而是整个小说世界的**“数字孪生基座” (Digital Twin Foundation)** 和**“唯一真实来源” (Single Source of Truth)**。

它负责将感性、模糊的小说文本，抽象并降维成极其严密的数学状态与逻辑图谱，为前端的“编剧大纲”和后端的“生产流水线”提供绝对的物理法则约束。

---

## 二、 核心职责
The Brain 的核心职责划分为三个记忆维度，以应对不同颗粒度的数据交互：

1. **绝对状态维系 (World State Machine, WSM)**：
    * 负责追踪小说中所有实体（角色、物品、门派、场景）的动态数值与库存变化。
    * 负责维护错综复杂的人际关系与物理位置拓扑（谁在谁身边、谁恨谁）。
    * **目标**：实现 0 容错，彻底杜绝 AI 大模型写小说时出现的“吃设定”、“物品无中生有”和“降智越级”现象。
2. **长期记忆检索 (Long-Term RAG)**：
    * 负责存储并检索十万字以上的世界观设定集、历史章节原文、长线伏笔。
    * **目标**：在生成后期章节时，能够精准唤醒前期埋下的线索。
3. **短期工作上下文 (Short-Term Context)**：
    * 负责管理当前章节/分镜生成时的 Agent 多轮对话历史、高频状态缓存。
    * **目标**：保障极速读写响应，隔离临时会话与持久化状态。

---

## 三、 系统架构
The Brain 内部采用 **三层内聚架构**，对外仅暴露标准化的上下文读取与状态写入 API：

### 1. 接口与网关层 (API / Facade)
* **Context Aggregator (上下文聚合器)**：接收外部 `The Engine` 的请求。内置“实体嗅探器”，通过轻量级 NLP 提取当前分镜涉及的角色与场景名，向下游发起精准查询。
* **State Extractor (状态抽取器)**：基于 Spring AI Alibaba 的 Function Calling (`@Tool`) 特性，解析大模型返回的自然语言，将其转换为结构化的状态变更指令（如 `{"action": "REMOVE_ITEM", "target": "林动", "item": "回血丹"}`）。

### 2. 记忆引擎层 (Memory Engines)
* **WSM Rule Engine (状态规则校验引擎)**：The Brain 的核心安全阀。拦截并校验 State Extractor 提取的指令。执行诸如“资产守恒校验”（背包里没有的物品不能消耗）和“逻辑边界校验”（不能瞬间跨越地图）。
* **RAG Retrieval Engine (向量检索调度引擎)**：负责文本的分块 (Chunking)、向量化 (Embedding) 以及相似度检索。

### 3. 物理存储层 (Storage)
见下文“底层存储技术”详解。

---

## 四、 底层存储技术
为完美支撑网文特有的“多变 JSON 属性”和“复杂关系网”，The Brain v2.0 废弃了传统关系型数据库，采用以下前沿存储矩阵：

### 1. 核心状态与图谱：ArangoDB (多模态图数据库)
承担 80% 的核心业务，替代 MySQL。分为三个核心 Collection：
* **实体快照集 (`Entities` Document Collection)**：
  以无模式 JSON (Schema-free) 存储角色的基础设定（种族、天赋）和动态状态（等级、HP、物品栏）。
* **关系图谱集 (`Relationships` Edge Collection)**：
  以有向边（Edge）存储实体间的复杂联系。例如 `[林动] --(仇恨:80)--> [王腾]`，或 `[林动] --(位于)--> [黑风崖]`。
* **事件流水集 (`EventLogs` Document Collection)**：
  **事件溯源（Event Sourcing）核心**。只追加 (Append-only) 大模型引发的所有状态变更日志，不直接修改原始数据，用于支持“时空回滚”。

### 2. 长期记忆设定集：Milvus 2.x (向量数据库)
负责高维向量检索。配合 BGE/OpenAI Embedding 模型，存储小说原始文本与长篇大纲，支持基于语义的模糊找回。

### 3. 高频缓存与会话：Redis
存储当前生产流水线中正在处理的 Agent 记忆窗口（Memory Buffer）和分布式锁。

---

## 五、 业务工作流

系统运转高度依赖以下三条核心工作流：

### 流程 A：动态上下文剪裁 (读链路)
*当 The Engine 准备生成“林动在黑风崖激战”的分镜时：*
1. **提取**：Context Aggregator 提取关键词 `[林动, 黑风崖]`。
2. **扩散**：向 ArangoDB 发送图查询 (Graph Traversal)，以林动为起点，抓取周围 1 层的关系网（发现王腾也在黑风崖，且对林动有敌意）。
3. **拼装**：将提取到的精简 JSON 状态（林动重伤、王腾满血、当前场景禁空）与 Milvus 检索到的黑风崖环境描写合并。
4. **输出**：生成一段无懈可击的 System Prompt，注入给剧情生成大模型。

### 流程 B：状态提取与强校验 (写链路)
*当大模型生成完上述战斗分镜的文本后：*
1. **触发**：推理模型触发 Function Calling，输出指令 `[王腾死亡, 林动消耗1颗回血丹]`。
2. **校验**：Rule Engine 介入，查询 ArangoDB 确认林动包里确实有回血丹，允许操作。
3. **落盘**：将该操作写入 ArangoDB 的 `EventLogs`（事件流水集）。
4. **刷新**：异步计算出最新的实体状态，更新 `Entities` 快照集。

### 流程 C：平行宇宙与时空回滚 (作者干预链路)
*当作者在“精修车间”不满意这段剧情，要求退回上一个分镜时：*
1. **指令下达**：前端发起 `Rollback(scene_id)` 请求。
2. **流水废弃**：The Brain 在 ArangoDB 的 `EventLogs` 中，将该 `scene_id` 及之后的所有事件标记为 `DELETED`。
3. **状态重塑**：系统根据剩余的有效流水，瞬间重新计算并恢复林动和王腾在上一个分镜的精确状态（王腾复活，回血丹恢复）。整个过程不产生任何脏数据。

## 架构流程图
graph TD
%% 定义外部交互
Engine(("The Engine<br/>生产流水线"))
Architect(("The Architect<br/>编剧模块"))

    %% The Brain 内部架构
    subgraph "The Brain (记忆与状态库内部架构 v2.0)"
        direction TB
        
        %% 接口层
        subgraph "1. 接口与网关层 (API / Facade)"
            ContextAggregator["Context Aggregator<br/>(上下文聚合器 / 实体嗅探)"]
            StateExtractor["State Extractor<br/>(状态抽取器 / Function Calling)"]
        end

        %% 核心逻辑层
        subgraph "2. 记忆引擎层 (Memory Engines)"
            RuleEngine["WSM Rule Engine<br/>(状态强规则校验引擎)"]
            RAGEngine["RAG Retrieval Engine<br/>(向量检索调度引擎)"]
        end

        %% 物理存储层
        subgraph "3. 物理存储层 (Storage - 重构后)"
            ArangoDB[("ArangoDB (多模态图数据库)<br/>- Entities (实体快照文档)<br/>- Relationships (关系图谱边)<br/>- EventLogs (事件溯源流水)")]
            Milvus[("Milvus 2.x<br/>长文本/设定向量")]
            Redis[("Redis<br/>当前会话/高频缓存")]
        end
        
        %% 内部调用关系 (读链路)
        ContextAggregator -->|1. 语义检索| RAGEngine
        ContextAggregator -->|2. 短期会话| Redis
        ContextAggregator -->|3. 图谱扩散/快照读取| ArangoDB
        
        %% 内部调用关系 (写链路)
        StateExtractor -->|1. 传递状态变更指令| RuleEngine
        RuleEngine -->|2. 校验逻辑/追加事件流水| ArangoDB
        RAGEngine --> Milvus
    end

    %% 外部与 The Brain 的交互
    Engine -- "1. 预检索: 提取场景上下文" --> ContextAggregator
    Architect -- "写入大纲与设定" --> RAGEngine
    Architect -. "初始化角色/世界状态机" .-> ArangoDB
    Engine -- "2. 回传新生成的文本用于更新状态" --> StateExtractor