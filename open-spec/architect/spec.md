# 编剧模块 (The Architect) Spec

## Why
编剧模块是 AI 写作工厂的“模具车间”，负责在正式生成正文前，将小说的设定、人物和结构固化为结构化数据，确保长篇创作的逻辑一致性和剧情节奏。

## What Changes
- 实现 **项目中枢 (Project Hub)**：管理小说元数据。
- 实现 **设定集辞典 (World Lore)**：存储世界观规则（支持 JSON 动态属性）。
- 实现 **角色反应堆 (Character Cast)**：管理角色卡（动机、性格、秘密）。
- 实现 **树状大纲引擎 (Outline Tree)**：支持递归生成“卷 -> 章 -> 分镜”。
- 引入 **ContextAssembler**：自动聚合设定和角色背景，为大纲生成提供上下文。
- 实现 **异步生成机制**：通过 TaskID 追踪长耗时的 AI 生成任务。

## Impact
- **Affected specs**: 初始 MVP Setup Spec (Phase 1 补充)
- **Affected code**: 
    - `src/main/java/com/storyfactory/architect/` (核心实现)
    - `src/main/resources/db/migration/` (数据库 Schema)

## ADDED Requirements
### Requirement: 递归大纲生成
系统 SHALL 支持根据上一级节点的摘要和上下文，递归生成下一级细化大纲。

#### Scenario: 从卷纲生成章纲
- **WHEN** 用户触发特定卷的“拆解章纲”操作。
- **THEN** 系统调用 AI 结合世界观设定，生成 10-20 个关联的章节节点。

### Requirement: 结构化设定管理
系统 SHALL 允许通过 JSON 字段存储不同类型的设定（如：等级制度、地理风貌）。

#### Scenario: 动态属性存储
- **WHEN** AI 推演并返回一个新的门派设定。
- **THEN** 系统将其 attributes 以 JSON 格式存入 `world_setting` 表。

### Requirement: 异步任务追踪
系统 SHALL 为所有 AI 生成操作提供 TaskID，并支持进度查询。

#### Scenario: 生成任务轮询
- **WHEN** 前端发起大规模大纲生成。
- **THEN** 后端立即返回 TaskID，并后台执行，前端可通过接口获取完成状态。

## MODIFIED Requirements
无

## REMOVED Requirements
无
