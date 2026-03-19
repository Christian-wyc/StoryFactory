<template>
  <div class="character-setting-container">
    <el-card class="character-card">
      <template #header>
        <div class="card-header">
          <span>人物设定</span>
          <el-button type="primary" @click="openCreateDialog">创建新角色</el-button>
        </div>
      </template>
      <div class="character-content">
        <el-tabs v-model="activeTab">
          <el-tab-pane label="角色列表" name="list">
            <el-table :data="characterList" style="width: 100%">
              <el-table-column prop="id" label="ID" width="80"></el-table-column>
              <el-table-column prop="name" label="角色名称" width="150"></el-table-column>
              <el-table-column prop="role" label="角色定位" width="120"></el-table-column>
              <el-table-column prop="age" label="年龄" width="80"></el-table-column>
              <el-table-column prop="description" label="角色描述"></el-table-column>
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <el-button type="primary" size="small" @click="editCharacter(scope.row)">编辑</el-button>
                  <el-button type="danger" size="small" @click="deleteCharacter(scope.row.id)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="关系图谱" name="relationship">
            <div class="relationship-graph">
              <p>人物关系图谱功能开发中...</p>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-card>

    <!-- 创建/编辑角色对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="600px"
    >
      <el-form :model="characterForm" label-width="100px">
        <el-form-item label="角色名称">
          <el-input v-model="characterForm.name" placeholder="请输入角色名称"></el-input>
        </el-form-item>
        <el-form-item label="角色定位">
          <el-select v-model="characterForm.role" placeholder="请选择角色定位">
            <el-option label="主角" value="protagonist"></el-option>
            <el-option label="反派" value="antagonist"></el-option>
            <el-option label="配角" value="supporting"></el-option>
            <el-option label="路人" value="extra"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input type="number" v-model="characterForm.age" placeholder="请输入年龄"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="characterForm.gender">
            <el-radio label="male">男</el-radio>
            <el-radio label="female">女</el-radio>
            <el-radio label="other">其他</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="外貌特征">
          <el-input type="textarea" v-model="characterForm.appearance" placeholder="请描述角色外貌" rows="3"></el-input>
        </el-form-item>
        <el-form-item label="性格特点">
          <el-input type="textarea" v-model="characterForm.personality" placeholder="请描述角色性格" rows="3"></el-input>
        </el-form-item>
        <el-form-item label="背景故事">
          <el-input type="textarea" v-model="characterForm.background" placeholder="请输入角色背景故事" rows="4"></el-input>
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input type="textarea" v-model="characterForm.description" placeholder="请输入角色描述" rows="3"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveCharacter">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'CharacterSetting',
  data() {
    return {
      activeTab: 'list',
      characterList: [
        {
          id: 1,
          name: '叶辰',
          role: 'protagonist',
          age: 18,
          gender: 'male',
          appearance: '剑眉星目，身材修长，穿着朴素的青衫',
          personality: '坚韧不拔，重情重义，有强烈的复仇心',
          background: '天玄宗外门弟子，被未婚妻退婚后获得系统',
          description: '本故事的男主角，从废物逆袭成为强者'
        },
        {
          id: 2,
          name: '林雨瑶',
          role: 'antagonist',
          age: 17,
          gender: 'female',
          appearance: '肤白貌美，气质高贵，穿着华丽的霓裳',
          personality: '高傲自大，嫌贫爱富',
          background: '林家大小姐，叶辰的未婚妻',
          description: '因叶辰修为低微而退婚，后反悔'
        },
        {
          id: 3,
          name: '萧炎',
          role: 'supporting',
          age: 19,
          gender: 'male',
          appearance: '浓眉大眼，体魄健壮，穿着兽皮护甲',
          personality: '豪爽仗义，重情重义',
          background: '叶辰的好友，同为天玄宗外门弟子',
          description: '在叶辰困难时给予帮助'
        }
      ],
      dialogVisible: false,
      dialogTitle: '创建新角色',
      characterForm: {
        id: '',
        name: '',
        role: '',
        age: '',
        gender: 'male',
        appearance: '',
        personality: '',
        background: '',
        description: ''
      }
    }
  },
  methods: {
    openCreateDialog() {
      this.dialogTitle = '创建新角色'
      this.characterForm = {
        id: '',
        name: '',
        role: '',
        age: '',
        gender: 'male',
        appearance: '',
        personality: '',
        background: '',
        description: ''
      }
      this.dialogVisible = true
    },
    editCharacter(character) {
      this.dialogTitle = '编辑角色'
      this.characterForm = { ...character }
      this.dialogVisible = true
    },
    saveCharacter() {
      if (!this.characterForm.name) {
        this.$message.error('请输入角色名称')
        return
      }
      if (!this.characterForm.role) {
        this.$message.error('请选择角色定位')
        return
      }
      if (this.characterForm.id) {
        // 编辑现有角色
        const index = this.characterList.findIndex(item => item.id === this.characterForm.id)
        if (index !== -1) {
          this.characterList[index] = { ...this.characterForm }
        }
      } else {
        // 创建新角色
        const newCharacter = {
          ...this.characterForm,
          id: this.characterList.length + 1
        }
        this.characterList.push(newCharacter)
      }
      this.dialogVisible = false
      this.$message.success('保存成功')
    },
    deleteCharacter(id) {
      this.$confirm('确定要删除这个角色吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.characterList.findIndex(item => item.id === id)
        if (index !== -1) {
          this.characterList.splice(index, 1)
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
.character-setting-container {
  padding: 20px;
}

.character-card {
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

.character-content {
  padding: 20px 0;
}

.relationship-graph {
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
