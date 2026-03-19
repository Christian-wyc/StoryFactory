<template>
  <div class="home-container">
    <el-card class="welcome-card">
      <template #header>
        <div class="card-header">
          <span>欢迎使用 StoryFactory</span>
        </div>
      </template>
      <div class="welcome-content">
        <h2>小说制造工厂</h2>
        <p>从灵感到完本，用工程化思维解决 AI 写长篇小说的"逻辑崩坏"与"失忆"痛点。</p>
        <div class="feature-list">
          <el-col :span="8">
            <el-card shadow="hover">
              <div class="feature-icon">📝</div>
              <h3>智能大纲</h3>
              <p>基于 AI 生成详细的小说大纲，确保剧情逻辑连贯</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover">
              <div class="feature-icon">🎭</div>
              <h3>人物设定</h3>
              <p>创建丰富的人物角色，自动追踪人物关系和发展</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover">
              <div class="feature-icon">🌍</div>
              <h3>世界构建</h3>
              <p>构建完整的世界观，包括地理、历史、文化等设定</p>
            </el-card>
          </el-col>
        </div>
        <div class="action-buttons">
          <el-button type="primary" size="large" @click="createNovel">创建新小说</el-button>
          <el-button type="success" size="large" @click="manageNovels">管理小说</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Home',
  methods: {
    createNovel() {
      this.$router.push('/novel')
    },
    manageNovels() {
      this.$router.push('/novel')
    }
  }
}
</script>

<style scoped>
.home-container {
  padding: 20px;
}

.welcome-card {
  max-width: 1200px;
  margin: 0 auto;
}

.card-header {
  font-size: 18px;
  font-weight: bold;
}

.welcome-content {
  text-align: center;
}

.welcome-content h2 {
  font-size: 32px;
  margin-bottom: 20px;
  color: #303133;
}

.welcome-content p {
  font-size: 16px;
  color: #606266;
  margin-bottom: 40px;
}

.feature-list {
  display: flex;
  gap: 20px;
  margin-bottom: 40px;
}

.feature-icon {
  font-size: 48px;
  margin-bottom: 16px;
}

.feature-list h3 {
  font-size: 18px;
  margin-bottom: 12px;
  color: #303133;
}

.feature-list p {
  font-size: 14px;
  color: #606266;
  margin-bottom: 0;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
}
</style>
