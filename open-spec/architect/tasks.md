# Tasks
- [x] Task 1: 基础设施与数据持久层 (Infrastructure): 实现编剧模块的 DDL 和持久化逻辑。
  - [x] SubTask 1.1: 编写 DDL 脚本，创建 `novel`, `world_setting`, `character_card`, `outline_node` 核心业务表。
  - [x] SubTask 1.2: 实现 JPA 实体类映射，支持 MySQL JSON 字段 (`@JdbcTypeCode(SqlTypes.JSON)`)。
  - [x] SubTask 1.3: 实现各业务域的基础 Repository 层接口。
  - [x] SubTask 1.4: 提供基础实体的 CRUD RESTful 接口。

- [x] Task 2: 接入 Spring AI Alibaba 与提示词工程 (AI Agent Services): 实现世界观和角色卡的 AI 推演与生成。
  - [x] SubTask 2.1: 实现 `WorldBuilderAgent`：根据梗概自动生成世界观 JSON。
  - [x] SubTask 2.2: 实现 `CharacterAgent`：根据世界设定生成角色卡 JSON（动机、秘密等）。
  - [x] SubTask 2.3: 配置 `BeanOutputConverter` 确保结构化输出映射到 Entity/DTO。
  - [x] SubTask 2.4: 引入 `ContextAssembler` 为生成操作聚合上下文。

- [x] Task 3: 递归大纲生成引擎 (Outline Tree): 实现“卷 -> 章 -> 分镜”的层级化拆解逻辑。
  - [x] SubTask 3.1: 开发卷纲生成逻辑：根据小说构思生成 Level 1 (VOLUME) 节点。
  - [x] SubTask 3.2: 开发章纲拆解逻辑：结合世界观将卷节点拆解为 Level 2 (CHAPTER) 节点。
  - [x] SubTask 3.3: 开发分镜拆解逻辑：结合角色卡将章节点拆解为 Level 3 (BEAT) 节点。
  - [x] SubTask 3.4: 实现 OutlineNode 的树状深度查询接口。

- [x] Task 4: 异步化任务调度与健壮性优化 (Async & Robustness): 提供工厂级别的任务追踪和容错。
  - [x] SubTask 4.1: 引入 `@Async` 或任务队列，将 AI 生成接口改为异步执行。
  - [x] SubTask 4.2: 实现 `TaskStatusTracker` 机制，前端可通过 TaskID 查询进度。
  - [x] SubTask 4.3: 引入 `RetryTemplate` 或重试逻辑，应对 AI 输出格式不正确的情况。

# Task Dependencies
- [Task 2] depends on [Task 1]
- [Task 3] depends on [Task 2]
- [Task 4] depends on [Task 3]
