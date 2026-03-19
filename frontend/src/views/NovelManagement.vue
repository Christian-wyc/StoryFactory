<template>
  <div class="novel-management-container">
    <el-card class="novel-card">
      <template #header>
        <div class="card-header">
          <span>小说管理</span>
          <el-button type="primary" @click="openCreateDialog">创建新小说</el-button>
        </div>
      </template>
      <el-table :data="novelList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="title" label="小说标题" width="200"></el-table-column>
        <el-table-column prop="genre" label="类型"></el-table-column>
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.status === 'ongoing' ? 'success' : 'info'">
              {{ scope.row.status === 'ongoing' ? '连载中' : '已完成' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="editNovel(scope.row)">编辑</el-button>
            <el-button type="success" size="small" @click="viewOutline(scope.row)">大纲</el-button>
            <el-button type="danger" size="small" @click="deleteNovel(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 创建/编辑小说对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form :model="novelForm" label-width="80px">
        <el-form-item label="小说标题">
          <el-input v-model="novelForm.title" placeholder="请输入小说标题"></el-input>
        </el-form-item>
        <el-form-item label="小说类型">
          <el-select v-model="novelForm.genre" placeholder="请选择小说类型">
            <el-option label="玄幻" value="fantasy"></el-option>
            <el-option label="科幻" value="sci-fi"></el-option>
            <el-option label="都市" value="urban"></el-option>
            <el-option label="历史" value="historical"></el-option>
            <el-option label="仙侠" value="xianxia"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="小说状态">
          <el-radio-group v-model="novelForm.status">
            <el-radio label="ongoing">连载中</el-radio>
            <el-radio label="completed">已完成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="小说简介">
          <el-input type="textarea" v-model="novelForm.description" placeholder="请输入小说简介" rows="4"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveNovel">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'NovelManagement',
  data() {
    return {
      novelList: [
        {
          id: 1,
          title: '斗破苍穹',
          genre: '玄幻',
          status: 'completed',
          createTime: '2024-01-01 10:00:00'
        },
        {
          id: 2,
          title: '三体',
          genre: '科幻',
          status: 'completed',
          createTime: '2024-01-02 11:00:00'
        },
        {
          id: 3,
          title: '都市传说',
          genre: '都市',
          status: 'ongoing',
          createTime: '2024-01-03 12:00:00'
        }
      ],
      dialogVisible: false,
      dialogTitle: '创建新小说',
      novelForm: {
        id: '',
        title: '',
        genre: '',
        status: 'ongoing',
        description: ''
      }
    }
  },
  methods: {
    openCreateDialog() {
      this.dialogTitle = '创建新小说'
      this.novelForm = {
        id: '',
        title: '',
        genre: '',
        status: 'ongoing',
        description: ''
      }
      this.dialogVisible = true
    },
    editNovel(novel) {
      this.dialogTitle = '编辑小说'
      this.novelForm = { ...novel }
      this.dialogVisible = true
    },
    saveNovel() {
      if (!this.novelForm.title) {
        this.$message.error('请输入小说标题')
        return
      }
      if (!this.novelForm.genre) {
        this.$message.error('请选择小说类型')
        return
      }
      if (this.novelForm.id) {
        // 编辑现有小说
        const index = this.novelList.findIndex(item => item.id === this.novelForm.id)
        if (index !== -1) {
          this.novelList[index] = { ...this.novelForm }
        }
      } else {
        // 创建新小说
        const newNovel = {
          ...this.novelForm,
          id: this.novelList.length + 1,
          createTime: new Date().toLocaleString()
        }
        this.novelList.push(newNovel)
      }
      this.dialogVisible = false
      this.$message.success('保存成功')
    },
    deleteNovel(id) {
      this.$confirm('确定要删除这本小说吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.novelList.findIndex(item => item.id === id)
        if (index !== -1) {
          this.novelList.splice(index, 1)
        }
        this.$message.success('删除成功')
      }).catch(() => {
        // 取消删除
      })
    },
    viewOutline(novel) {
      this.$router.push(`/outline?id=${novel.id}&title=${encodeURIComponent(novel.title)}`)
    }
  }
}
</script>

<style scoped>
.novel-management-container {
  padding: 20px;
}

.novel-card {
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

.dialog-footer {
  width: 100%;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
