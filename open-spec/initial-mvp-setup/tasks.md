# Tasks
- [x] Task 1: 初始化项目骨架 (MVP): 创建基于 Spring Boot 3.4+ 的项目，配置 Spring AI Alibaba 核心依赖。
  - [x] SubTask 1.1: 配置 `pom.xml` 加入 Spring AI Alibaba, MySQL, Redis, Milvus 依赖。
  - [x] SubTask 1.2: 创建项目基础包结构 (oracle, architect, engine, brain, workshop, publisher)。
  - [x] SubTask 1.3: 验证 Spring Boot 应用能成功启动并连接数据库。

- [ ] Task 2: 实现 WSM 世界状态机 (The Brain): 设计并实现基础角色属性和环境状态的存取逻辑。
  - [ ] SubTask 2.1: 定义 `WorldState` 实体类及数据库表。
  - [ ] SubTask 2.2: 实现 `WSMService` 用于读写角色状态 (如等级、金币)。
  - [ ] SubTask 2.3: 编写单元测试验证 WSM 状态的一致性。

- [ ] Task 3: 搭建 RAG 基础链路 (Oracle/Brain): 集成 Milvus 向量库，实现基础的世界观设定存储与检索。
  - [ ] SubTask 3.1: 配置 Milvus VectorStore 并实现简单的文档切片入库。
  - [ ] SubTask 3.2: 实现检索接口，支持基于关键词的背景知识检索。

- [ ] Task 4: 构建 MVP Graph 流程 (The Engine): 使用 Spring AI Alibaba Graph 串联小说生成链路。
  - [ ] SubTask 4.1: 定义 `NovelGenerationGraph` 状态节点 (Outline -> Content -> Summary)。
  - [ ] SubTask 4.2: 实现大纲生成节点 (The Architect) 的基础 Prompt。
  - [ ] SubTask 4.3: 实现摘要回填节点，将生成的本章摘要写入 WSM。

# Task Dependencies
- [Task 2] depends on [Task 1]
- [Task 3] depends on [Task 1]
- [Task 4] depends on [Task 2] 和 [Task 3]
