<template>
  <div class="app-container notion-theme">
    <el-container>
      <el-header height="60px" class="notion-header">
        <div class="logo-box">
          <span class="logo-icon">📚</span>
          <span class="logo-text">StoryFactory</span>
        </div>
        <el-menu
            :default-active="activeIndex"
            class="notion-menu"
            mode="horizontal"
            router
        >
          <el-menu-item index="/">工作台</el-menu-item>
          <el-menu-item index="/novel">项目库</el-menu-item>
          <el-menu-item index="/outline">大纲</el-menu-item>
          <el-menu-item index="/character">角色</el-menu-item>
          <el-menu-item index="/world">设定</el-menu-item>
        </el-menu>
      </el-header>

      <el-main class="notion-main">
        <div class="main-wrapper">
          <router-view />
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      activeIndex: '/'
    }
  },
  watch: {
    '$route.path'(newPath) {
      this.activeIndex = newPath;
    }
  }
}
</script>

<style>
/* Notion 极简风核心变量 */
:root {
  --nt-bg: #ffffff;
  --nt-text-main: #37352f;
  --nt-text-muted: #787774;
  --nt-hover-bg: #f1f1ef;
  --nt-border: #e9e9e7;
  --nt-shadow: rgba(15, 15, 15, 0.05) 0px 0px 0px 1px, rgba(15, 15, 15, 0.1) 0px 2px 4px;
}

/* 全局重置 */
body {
  margin: 0;
  padding: 0;
  background-color: var(--nt-bg);
  color: var(--nt-text-main);
  /* 使用系统默认的无衬线字体，保证最佳阅读体验 */
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, "Apple Color Emoji", Arial, sans-serif, "Segoe UI Emoji", "Segoe UI Symbol";
  -webkit-font-smoothing: antialiased;
}

.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 极简导航栏 */
.notion-header {
  background: var(--nt-bg) !important;
  border-bottom: 1px solid var(--nt-border);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 40px !important;
}

.logo-box {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.logo-icon {
  font-size: 22px;
}

.logo-text {
  font-size: 16px;
  font-weight: 600;
  color: var(--nt-text-main);
}

.notion-menu {
  background: transparent !important;
  border: none !important;
  height: 100%;
}

.notion-menu .el-menu-item {
  color: var(--nt-text-muted) !important;
  font-size: 14px;
  font-weight: 500;
  border: none !important;
  padding: 0 16px !important;
  height: 60px !important;
  line-height: 60px !important;
  transition: all 0.2s ease;
  background: transparent !important;
}

.notion-menu .el-menu-item:hover {
  color: var(--nt-text-main) !important;
  background: var(--nt-hover-bg) !important;
}

.notion-menu .el-menu-item.is-active {
  color: var(--nt-text-main) !important;
  font-weight: 600;
  position: relative;
}

/* 底部小黑条指示器 */
.notion-menu .el-menu-item.is-active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 16px;
  right: 16px;
  height: 2px;
  background-color: var(--nt-text-main);
  border-radius: 2px 2px 0 0;
}

.notion-main {
  padding: 40px 0;
  display: flex;
  justify-content: center;
}

.main-wrapper {
  width: 100%;
  max-width: 900px; /* 限制阅读宽度，提高舒适度 */
  padding: 0 20px;
}

/* 全局覆盖 Element Plus 样式 */
.el-card {
  background: var(--nt-bg) !important;
  border: 1px solid var(--nt-border) !important;
  border-radius: 8px !important;
  box-shadow: var(--nt-shadow) !important;
  color: var(--nt-text-main) !important;
  transition: box-shadow 0.2s ease;
}

.el-button--primary {
  background-color: var(--nt-text-main) !important;
  border-color: var(--nt-text-main) !important;
  color: #fff !important;
  border-radius: 6px !important;
  font-weight: 500 !important;
  transition: opacity 0.2s;
}

.el-button--primary:hover {
  background-color: var(--nt-text-main) !important;
  opacity: 0.85;
}

.el-button {
  border-radius: 6px !important;
}
</style>