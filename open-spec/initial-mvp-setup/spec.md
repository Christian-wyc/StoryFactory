# StoryFactory 初始 MVP 架构 Spec

## Why
StoryFactory 旨在通过工业化流水线解决 AI 创作长篇小说时的逻辑一致性和质量稳定性问题。本 Spec 聚焦于 Phase 1：基础设施搭建 (MVP)，为后续复杂的 Agent 协作和分镜生成奠定基础。

## What Changes
- 初始化基于 Spring Boot 3.4+ 的项目骨架。
- 集成 Spring AI Alibaba 及其 Graph 编排能力。
- 实现 **The Brain** (记忆与状态库) 的基础模型：世界状态机 (WSM) 的持久化方案。
- 搭建基础 RAG (检索增强生成) 链路，对接 Milvus 向量库。
- 构建 MVP 级别的 Graph 流程：大纲生成 -> 章节正文生成 -> 剧情摘要回填 WSM。

## Impact
- **Affected specs**: 无 (初始 Spec)
- **Affected code**: 
    - `pom.xml` (依赖管理)
    - `src/main/java/com/storyfactory/brain` (WSM 实现)
    - `src/main/java/com/storyfactory/engine` (Graph 流程定义)
    - `src/main/java/com/storyfactory/oracle` (基础 RAG 接口)

## ADDED Requirements
### Requirement: 基础 RAG 链路
系统 SHALL 提供基于 Milvus 的向量检索功能，用于存储和检索全书的世界观设定和历史背景。

#### Scenario: 成功检索背景
- **WHEN** 引擎在生成特定章节时，请求相关的背景设定。
- **THEN** 系统从 Milvus 中检索出最相关的 Top-K 个知识片段，并注入 Prompt。

### Requirement: WSM 状态机 (The Brain)
系统 SHALL 能够记录和更新小说的“世界状态”，包括角色属性（等级、金钱等）和环境信息。

#### Scenario: 更新主角状态
- **WHEN** 某一章生成结束后，提取到主角获得 100 金币。
- **THEN** WSM 自动更新 MySQL 中的对应角色记录。

### Requirement: MVP Graph 流程
系统 SHALL 通过 Spring AI Alibaba Graph 实现“大纲 -> 正文 -> 摘要”的自动化流转。

#### Scenario: 自动生成章节并回填
- **WHEN** 输入一个卷纲。
- **THEN** 系统自动拆解章纲，生成正文，并将本章摘要存入 WSM。

## MODIFIED Requirements
无

## REMOVED Requirements
无
