# Checklist
- [x] 核心业务表 (`novel`, `world_setting`, `character_card`, `outline_node`) DDL 脚本已执行，且支持 JSON 字段。
- [x] JPA 实体类映射正确，能成功在 MySQL 中读写 JSON 格式的设定和角色属性。
- [x] `WorldBuilderAgent` 能根据一句话梗概生成符合结构化定义的世界观 JSON 并自动入库。
- [x] `CharacterAgent` 能结合世界观背景生成具备“动机”和“秘密”的角色卡。
- [x] 大纲树逻辑正确：输入一卷能正确拆解出多章，且 ParentID 关联无误。
- [x] 异步任务机制可用：前端发起生成请求后能立即拿到 TaskID，且后台任务完成后状态更新。
- [x] `ContextAssembler` 能自动为生成大纲提供所需的设定集和角色背景信息。
- [x] AI 生成的 JSON 格式校验通过，若不匹配能自动触发重试。
