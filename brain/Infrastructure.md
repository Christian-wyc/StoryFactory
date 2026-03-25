# 🚀 StoryFactory (The Brain) 部署与技能清单

## 一、 Linux 服务器环境部署清单 (Infrastructure)

> **核心原则**：所有中间件全部容器化 (Docker) 部署，保持宿主机环境纯净。

### 1. 基础环境 (直接安装在宿主机)
- [ ] **Docker Engine**: 容器运行环境 (必须)。
- [ ] **Docker Compose**: 容器编排工具，用于一键启动所有数据库 (必须)。
- [ ] **JDK 21**: Java 运行环境 (推荐 21，完美支持 Spring Boot 3.4+ 的虚拟线程，提升高并发性能)。
- [ ] **Git**: 用于拉取和管理代码。

### 2. 核心中间件 (通过 `docker-compose.yml` 一键拉起)
- [ ] **ArangoDB**: 核心多模态图数据库。
    - *作用*：接管所有角色状态 (JSON) 和复杂人际关系 (Graph)。
    - *端口*：`8529` (Web UI 与 API 端口)。
- [ ] **Redis**: 高频缓存服务器。
    - *作用*：存储大模型短对话上下文、分布式锁。
    - *端口*：`6379`。
- [ ] **Milvus 2.x** *(前期开发阶段可暂缓安装)*: 向量数据库。
    - *作用*：长文本剧情和世界观设定的语义检索 (RAG)。
    - *端口*：`19530`。

---

## 二、 核心技能树清单 (Skill Roadmap)

要完美落地 The Brain v2.0 的架构，你需要掌握以下核心技术。按照优先级从高到低排列：

### 🎯 优先级 P0：项目生死线 (必须完全掌握)

#### 1. ArangoDB 基础与图数据库思维
这是你替换 MySQL 后的核心武器，你需要转变原有的“关系型建表”思维。
- [ ] **基本概念**：理解 Document Collection (文档集合) 和 Edge Collection (边集合) 的区别。
- [ ] **AQL (ArangoDB Query Language)**：掌握其原生查询语言。
    - *必学语句*：如何做图谱遍历查询 (Graph Traversal)。比如：`FOR v, e IN 1..2 OUTBOUND 'Entities/char_1' Relationships RETURN v` (查询林动身边一到两层关系的所有人)。
- [ ] **Spring Data ArangoDB**：掌握在 Spring Boot 中如何使用 `@Document`, `@Edge`, `ArangoRepository` 进行数据读写。

#### 2. Spring AI Alibaba 高级特性
不要只把它当成一个“发 HTTP 请求调大模型”的工具包，重点学习它的编排能力。
- [ ] **Function Calling (函数调用 / @Tool)**：这是状态提取器 (State Extractor) 的核心。必须学会如何定义一个 Java 方法，让大模型在想修改小说状态时**自动触发**它。
- [ ] **Prompt Template (提示词模板)**：学习如何将 ArangoDB 里查出来的大段 JSON 动态地、优雅地注入到 System Prompt 中。
- [ ] **Structured Output (结构化输出)**：强制大模型以严格的 JSON 格式返回分析结果。

### 🚀 优先级 P1：工业化架构落地 (决定系统健壮性)

#### 3. 事件溯源架构思维 (Event Sourcing)
- [ ] **Append-Only (只追加) 原则**：理解为什么状态变更只能 `INSERT` 日志，绝对不能 `UPDATE` 原记录。
- [ ] **状态重塑 (State Rebuilding)**：学习如何通过回放 `EventLogs` 中的数据，重新计算出主角在第 N 章的精确状态。

#### 4. Spring Boot 高级与规则校验
- [ ] **AOP 与拦截器**：在写库之前，利用拦截器进行“物理规则”的强校验（背包没药不准吃）。
- [ ] **全局异常处理 (Global Exception Handler)**：当模型违反物理规则时，捕获异常并将其转化为新的 Prompt 打回给大模型要求重试。

### 🛠️ 优先级 P2：锦上添花 (开发中后期再深入)

#### 5. Milvus 与 RAG (检索增强生成)
- [ ] **Embedding (向量化)**：了解如何把一段小说文本转化为多维向量。
- [ ] **Chunking 策略**：十几万字的小说，如何切分成合适的段落存入 Milvus，才能让大模型找得最准。