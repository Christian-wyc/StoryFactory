# 🚀 StoryFactory 快速开始与使用指南

目前我们已经完成了 **编剧模块 (The Architect)** 的核心开发。你可以通过以下步骤在本地跑起来并体验“一句话生成世界观和大纲”的流程。

## 1. 环境准备
确保你的本地环境已经安装并启动了以下服务：
- **Java 17+**
- **Maven 3.8+**
- **MySQL 8.0+** (必须支持 JSON 字段)
    - 创建数据库：`CREATE DATABASE story_factory DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`
- **Redis** (默认端口 6379)
- **阿里云 DashScope API Key**

## 2. 配置项目
修改 `src/main/resources/application.yml` 中的配置，或者通过环境变量传入：
```bash
export SPRING_AI_ALIBABA_API_KEY="sk-你的真实APIKey"
```

## 3. 启动项目
在项目根目录下运行：
```bash
mvn spring-boot:run
```
服务默认在 `8080` 端口启动。

## 4. 体验编剧流水线 (API 测试)

你可以使用 Postman、curl 或浏览器插件进行测试。这里是一个完整的工作流演示：

### Step 4.1: 创建一本新书 (Project Hub)
告诉系统你要写什么。
```bash
curl -X POST http://localhost:8080/api/architect/novels \n-H "Content-Type: application/json" \n-d '{
    "title": "赛博剑仙录",
    "genre": "科幻修真",
    "premise": "在一个被超级公司垄断的赛博朋克世界，主角发现传统的修真功法可以通过植入特殊义体来实现，他决定用古老的剑意对抗机械霸权。"
}'
```
> **记录返回的 `id` (例如: 1)**，后续步骤需要用到。

### Step 4.2: 让 AI 推演世界观 (World Lore)
这是一个异步任务，因为 AI 需要思考。
```bash
curl -X POST "http://localhost:8080/api/architect/tasks/world/1?premise=在一个被超级公司垄断的赛博朋克世界，主角发现传统的修真功法可以通过植入特殊义体来实现，他决定用古老的剑意对抗机械霸权。"
```
> **返回一个 `taskId` (UUID)**。
> 你可以通过轮询任务状态查看是否完成：`curl http://localhost:8080/api/architect/tasks/{taskId}`
> 成功后，可以查看生成的世界观：`curl http://localhost:8080/api/architect/world-settings/novel/1`

### Step 4.3: 让 AI 生成核心角色卡 (Character Cast)
```bash
curl -X POST "http://localhost:8080/api/architect/tasks/characters/1?premise=在一个被超级公司垄断的赛博朋克世界，主角发现传统的修真功法可以通过植入特殊义体来实现，他决定用古老的剑意对抗机械霸权。"
```
> **同样返回 `taskId`**。
> 成功后，查看生成的角色：`curl http://localhost:8080/api/architect/characters/novel/1`

### Step 4.4: 递归生成大纲树 (Outline Tree)

**生成卷纲 (Volume)**
```bash
curl -X POST http://localhost:8080/api/architect/tasks/volumes/1
```
> 查看生成的卷：`curl http://localhost:8080/api/architect/outlines/novel/1`
> 找到第一卷的 `id` (假设为 10)。

**拆解章纲 (Chapter)**
```bash
curl -X POST http://localhost:8080/api/architect/tasks/chapters/10
```

**拆解分镜 (Beat)**
找到某一章的 `id` (假设为 20)。
```bash
curl -X POST http://localhost:8080/api/architect/tasks/beats/20
```
