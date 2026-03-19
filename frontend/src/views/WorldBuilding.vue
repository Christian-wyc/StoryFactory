<template>
  <div class="world-building-container">
    <el-card class="world-card">
      <template #header>
        <div class="card-header">
          <span>世界构建</span>
          <el-button type="primary" @click="openCreateDialog">创建新设定</el-button>
        </div>
      </template>
      <div class="world-content">
        <el-tabs v-model="activeTab">
          <el-tab-pane label="设定列表" name="list">
            <el-table :data="worldSettings" style="width: 100%">
              <el-table-column prop="id" label="ID" width="80"></el-table-column>
              <el-table-column prop="name" label="设定名称" width="150"></el-table-column>
              <el-table-column prop="type" label="设定类型" width="120"></el-table-column>
              <el-table-column prop="description" label="设定描述"></el-table-column>
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <el-button type="primary" size="small" @click="editSetting(scope.row)">编辑</el-button>
                  <el-button type="danger" size="small" @click="deleteSetting(scope.row.id)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="世界地图" name="map">
            <div class="world-map">
              <p>世界地图功能开发中...</p>
            </div>
          </el-tab-pane>
          <el-tab-pane label="时间线" name="timeline">
            <div class="timeline">
              <p>时间线功能开发中...</p>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-card>

    <!-- 创建/编辑设定对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="600px"
    >
      <el-form :model="settingForm" label-width="100px">
        <el-form-item label="设定名称">
          <el-input v-model="settingForm.name" placeholder="请输入设定名称"></el-input>
        </el-form-item>
        <el-form-item label="设定类型">
          <el-select v-model="settingForm.type" placeholder="请选择设定类型">
            <el-option label="地理" value="geography"></el-option>
            <el-option label="历史" value="history"></el-option>
            <el-option label="文化" value="culture"></el-option>
            <el-option label="宗教" value="religion"></el-option>
            <el-option label="政治" value="politics"></el-option>
            <el-option label="经济" value="economy"></el-option>
            <el-option label="力量体系" value="power"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设定描述">
          <el-input type="textarea" v-model="settingForm.description" placeholder="请输入设定描述" rows="4"></el-input>
        </el-form-item>
        <el-form-item label="详细内容">
          <el-input type="textarea" v-model="settingForm.content" placeholder="请输入详细内容" rows="8"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveSetting">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'WorldBuilding',
  data() {
    return {
      activeTab: 'list',
      worldSettings: [
        {
          id: 1,
          name: '玄灵大陆',
          type: 'geography',
          description: '故事发生的主要大陆',
          content: '玄灵大陆是一个修炼者的世界，分为东、南、西、北四个区域。大陆中央有一座通天山脉，是修炼者的圣地。'
        },
        {
          id: 2,
          name: '天玄宗',
          type: 'politics',
          description: '主角所在的宗门',
          content: '天玄宗是玄灵大陆上的二流宗门，位于东部区域。宗门分为外门和内门，外门弟子数千人，内门弟子数百人。'
        },
        {
          id: 3,
          name: '修炼体系',
          type: 'power',
          description: '世界的力量体系',
          content: '修炼体系分为练气、筑基、金丹、元婴、化神、合体、渡劫、大乘八个境界。每个境界又分为初期、中期、后期三个阶段。'
        }
      ],
      dialogVisible: false,
      dialogTitle: '创建新设定',
      settingForm: {
        id: '',
        name: '',
        type: '',
        description: '',
        content: ''
      }
    }
  },
  methods: {
    openCreateDialog() {
      this.dialogTitle = '创建新设定'
      this.settingForm = {
        id: '',
        name: '',
        type: '',
        description: '',
        content: ''
      }
      this.dialogVisible = true
    },
    editSetting(setting) {
      this.dialogTitle = '编辑设定'
      this.settingForm = { ...setting }
      this.dialogVisible = true
    },
    saveSetting() {
      if (!this.settingForm.name) {
        this.$message.error('请输入设定名称')
        return
      }
      if (!this.settingForm.type) {
        this.$message.error('请选择设定类型')
        return
      }
      if (this.settingForm.id) {
        // 编辑现有设定
        const index = this.worldSettings.findIndex(item => item.id === this.settingForm.id)
        if (index !== -1) {
          this.worldSettings[index] = { ...this.settingForm }
        }
      } else {
        // 创建新设定
        const newSetting = {
          ...this.settingForm,
          id: this.worldSettings.length + 1
        }
        this.worldSettings.push(newSetting)
      }
      this.dialogVisible = false
      this.$message.success('保存成功')
    },
    deleteSetting(id) {
      this.$confirm('确定要删除这个设定吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.worldSettings.findIndex(item => item.id === id)
        if (index !== -1) {
          this.worldSettings.splice(index, 1)
        }
        this.$message.success('删除成功')
      }).catch(() => {
        // 取消删除
      })
    }
  }
}
</script>

<style scoped>
.world-building-container {
  padding: 20px;
}

.world-card {
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

.world-content {
  padding: 20px 0;
}

.world-map,
.timeline {
  height: 400px;
  background-color: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
}

.dialog-footer {
  width: 100%;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
