<template>
  <div class="outline-editor-container">
    <el-card class="outline-card">
      <template #header>
        <div class="card-header">
          <span>{{ novelTitle || '大纲编辑' }}</span>
          <div class="header-buttons">
            <el-button type="primary" @click="generateOutline">AI 生成大纲</el-button>
            <el-button type="success" @click="saveOutline">保存大纲</el-button>
          </div>
        </div>
      </template>
      <div class="outline-content">
        <el-tree
          :data="outlineData"
          node-key="id"
          :expand-on-click-node="false"
          :default-expand-all="true"
          @node-click="handleNodeClick"
        >
          <template #default="{ node, data }">
            <div class="tree-node">
              <span @dblclick="editNode(data)">{{ data.label }}</span>
              <div class="node-actions">
                <el-button type="text" size="small" @click.stop="addChild(data)">添加子节点</el-button>
                <el-button type="text" size="small" @click.stop="deleteNode(data)">删除</el-button>
              </div>
            </div>
          </template>
        </el-tree>
        
        <!-- 节点编辑对话框 -->
        <el-dialog
          :title="editDialogTitle"
          v-model="editDialogVisible"
          width="500px"
        >
          <el-form :model="currentNode" label-width="80px">
            <el-form-item label="节点标题">
              <el-input v-model="currentNode.label" placeholder="请输入节点标题"></el-input>
            </el-form-item>
            <el-form-item label="节点内容">
              <el-input type="textarea" v-model="currentNode.content" placeholder="请输入节点详细内容" rows="6"></el-input>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="editDialogVisible = false">取消</el-button>
              <el-button type="primary" @click="saveNode">保存</el-button>
            </span>
          </template>
        </el-dialog>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'OutlineEditor',
  data() {
    return {
      novelTitle: '',
      outlineData: [
        {
          id: 1,
          label: '第一卷 初入江湖',
          content: '主角开始踏上江湖的故事',
          children: [
            {
              id: 11,
              label: '第一章 退婚',
              content: '主角被未婚妻退婚，激发奋斗决心'
            },
            {
              id: 12,
              label: '第二章 获得系统',
              content: '主角意外获得系统，开启逆袭之路'
            },
            {
              id: 13,
              label: '第三章 修炼突破',
              content: '主角开始修炼，突破第一个境界'
            }
          ]
        },
        {
          id: 2,
          label: '第二卷 崭露头角',
          content: '主角在江湖中逐渐崭露头角',
          children: [
            {
              id: 21,
              label: '第四章 宗门大比',
              content: '主角参加宗门大比，获得优异成绩'
            },
            {
              id: 22,
              label: '第五章 遭遇强敌',
              content: '主角遭遇强大敌人，险象环生'
            }
          ]
        }
      ],
      editDialogVisible: false,
      editDialogTitle: '编辑节点',
      currentNode: {
        id: '',
        label: '',
        content: ''
      }
    }
  },
  mounted() {
    // 从URL获取小说ID和标题
    const query = this.$route.query
    if (query.title) {
      this.novelTitle = decodeURIComponent(query.title)
    }
  },
  methods: {
    handleNodeClick(data) {
      console.log('点击节点:', data)
    },
    editNode(data) {
      this.currentNode = { ...data }
      this.editDialogTitle = '编辑节点'
      this.editDialogVisible = true
    },
    addChild(data) {
      const newId = Date.now()
      const newNode = {
        id: newId,
        label: '新节点',
        content: ''
      }
      if (!data.children) {
        data.children = []
      }
      data.children.push(newNode)
    },
    deleteNode(data) {
      this.$confirm('确定要删除这个节点吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.removeNode(this.outlineData, data.id)
        this.$message.success('删除成功')
      }).catch(() => {
        // 取消删除
      })
    },
    removeNode(tree, id) {
      for (let i = 0; i < tree.length; i++) {
        if (tree[i].id === id) {
          tree.splice(i, 1)
          return true
        }
        if (tree[i].children) {
          if (this.removeNode(tree[i].children, id)) {
            return true
          }
        }
      }
      return false
    },
    saveNode() {
      if (!this.currentNode.label) {
        this.$message.error('请输入节点标题')
        return
      }
      // 找到并更新节点
      this.updateNode(this.outlineData, this.currentNode)
      this.editDialogVisible = false
      this.$message.success('保存成功')
    },
    updateNode(tree, updatedNode) {
      for (let i = 0; i < tree.length; i++) {
        if (tree[i].id === updatedNode.id) {
          tree[i] = { ...updatedNode }
          return true
        }
        if (tree[i].children) {
          if (this.updateNode(tree[i].children, updatedNode)) {
            return true
          }
        }
      }
      return false
    },
    generateOutline() {
      this.$message.info('AI 正在生成大纲，请稍候...')
      // 模拟AI生成大纲
      setTimeout(() => {
        this.$message.success('大纲生成成功')
      }, 2000)
    },
    saveOutline() {
      this.$message.success('大纲保存成功')
    }
  }
}
</script>

<style scoped>
.outline-editor-container {
  padding: 20px;
}

.outline-card {
  max-width: 1200px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
}

.header-buttons {
  display: flex;
  gap: 10px;
}

.outline-content {
  padding: 20px 0;
}

.tree-node {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.node-actions {
  display: flex;
  gap: 5px;
  opacity: 0;
  transition: opacity 0.3s;
}

.tree-node:hover .node-actions {
  opacity: 1;
}

.dialog-footer {
  width: 100%;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
