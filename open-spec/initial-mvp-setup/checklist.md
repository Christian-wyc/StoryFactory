# Checklist
- [x] Spring Boot 3.4+ 项目初始化完成，依赖项 (Spring AI Alibaba, MySQL, Redis, Milvus) 配置正确
- [x] 项目包结构 (oracle, architect, engine, brain, workshop, publisher) 符合模块设计要求
- [ ] WSM (The Brain) 状态机能够正确存取角色属性 (如等级、金钱)
- [ ] RAG 链路能够从 Milvus 中检索出世界观背景
- [ ] MVP Graph 流程 (大纲 -> 正文 -> 摘要) 能完成一轮完整的生成循环
- [ ] 所有 WSM 的摘要更新均能持久化到数据库中
