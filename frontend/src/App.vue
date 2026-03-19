<template>
  <div class="app-container">
    <el-container>
      <el-header height="60px" class="header">
        <div class="logo">StoryFactory</div>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-menu-item index="1">首页</el-menu-item>
          <el-menu-item index="2">小说管理</el-menu-item>
          <el-menu-item index="3">大纲编辑</el-menu-item>
          <el-menu-item index="4">人物设定</el-menu-item>
          <el-menu-item index="5">世界构建</el-menu-item>
        </el-menu>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      activeIndex: '1'
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath)
    }
  }
}
</script>

<style>
.app-container {
  height: 100vh;
  background-color: #f5f7fa;
}

.header {
  background-color: #409EFF;
  color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.logo {
  font-size: 24px;
  font-weight: bold;
}

.el-menu-demo {
  background-color: transparent;
  border-bottom: none;
}

.el-menu-demo .el-menu-item {
  color: white;
}

.el-menu-demo .el-menu-item.is-active {
  color: white;
  background-color: rgba(255, 255, 255, 0.2);
}

.el-main {
  padding: 20px;
}
</style>
