<template>
  <div class="notion-home">
    <div class="hero-section">
      <h1 class="page-title">小说制造工厂</h1>
      <p class="page-desc">基于工程化思维构建的长篇小说创作流水线。从灵感到完本，拒绝剧情崩坏。</p>
      <div class="action-row">
        <el-button type="primary" size="large" @click="$router.push('/novel')">
          <span class="btn-icon">✨</span> 新建小说
        </el-button>
        <el-button size="large" @click="$router.push('/novel')">
          浏览项目库
        </el-button>
      </div>
    </div>

    <el-divider class="nt-divider"></el-divider>

    <div class="modules-section">
      <h2 class="section-title">核心车间</h2>

      <el-row :gutter="24">
        <el-col :span="8">
          <el-card class="module-card" shadow="hover" @click="$router.push('/outline')">
            <div class="card-icon">🌳</div>
            <h3>智能大纲</h3>
            <p>基于架构引擎生成三层递归逻辑链，确保故事结构严密且连贯。</p>
          </el-card>
        </el-col>

        <el-col :span="8">
          <el-card class="module-card" shadow="hover" @click="$router.push('/character')">
            <div class="card-icon">🎭</div>
            <h3>角色设定</h3>
            <p>构建包含动机、性格缺陷与隐藏秘密的结构化人物档案。</p>
          </el-card>
        </el-col>

        <el-col :span="8">
          <el-card class="module-card" shadow="hover" @click="$router.push('/world')">
            <div class="card-icon">🌍</div>
            <h3>世界构建</h3>
            <p>定义物理规则、地理风貌与力量体系，打造沉浸式背景。</p>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home'
}
</script>

<style scoped>
.notion-home {
  padding-top: 20px;
  animation: fadeIn 0.5s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.hero-section {
  margin-bottom: 40px;
}

.page-title {
  font-size: 40px;
  font-weight: 700;
  letter-spacing: -0.02em;
  margin: 0 0 16px 0;
  color: var(--nt-text-main);
}

.page-desc {
  font-size: 18px;
  color: var(--nt-text-muted);
  line-height: 1.6;
  max-width: 600px;
  margin: 0 0 32px 0;
}

.action-row {
  display: flex;
  gap: 16px;
}

.btn-icon {
  margin-right: 6px;
}

.nt-divider {
  border-top-color: var(--nt-border);
  margin: 40px 0;
}

.section-title {
  font-size: 20px;
  font-weight: 600;
  margin: 0 0 24px 0;
  color: var(--nt-text-main);
}

.module-card {
  cursor: pointer;
  height: 220px;
  display: flex;
  flex-direction: column;
  padding: 10px;
}

.module-card:hover {
  transform: translateY(-2px);
  box-shadow: rgba(15, 15, 15, 0.1) 0px 4px 12px !important;
}

.card-icon {
  font-size: 32px;
  margin-bottom: 16px;
}

.module-card h3 {
  font-size: 16px;
  font-weight: 600;
  margin: 0 0 8px 0;
}

.module-card p {
  font-size: 14px;
  color: var(--nt-text-muted);
  line-height: 1.5;
  margin: 0;
}
</style>