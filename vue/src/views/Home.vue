<template>
  <div>
    <el-button type="primary" @click="handleAdd"><el-icon style="margin-right: 3px"><Plus /></el-icon>新規登録</el-button>
  </div>
  <el-table
      :data="state.tableDate"
      border
      style="width: 100%">
    <el-table-column
        fixed
        prop="name"
        label="氏名"
        width="150">
    </el-table-column>
    <el-table-column
        prop="sex"
        label="性別"
        width="100">
    </el-table-column>
    <el-table-column
        prop="department"
        label="部署"
        width="120">
    </el-table-column>
    <el-table-column
        prop="birthday"
        label="生年月日"
        width="180">
    </el-table-column>
    　<el-table-column
      prop="email"
      label="メールアドレス"
      width="200">
  </el-table-column>
    　<el-table-column
      prop="telephone"
      label="電話番号"
      width="180">
  </el-table-column>
    　<el-table-column
      prop="skill"
      label="特技"
      width="150">
  </el-table-column>
    <el-table-column
        prop="educational"
        label="最終学歴"
        width="100">
    </el-table-column>
    <el-table-column
        prop="address"
        label="住所"
        width="300">
    </el-table-column>
    <el-table-column
        fixed="right"
        label="操作"
        width="150">
      <template #default="scope">
        <el-button type="text" size="small" @click="handleEdit(scope.row)"><el-icon style="margin-right: 3px"><Edit /></el-icon>編集</el-button>
        <el-button link type="danger" size="small" @click="deleteRow(scope.row.name)"><el-icon style="margin-right: 3px"><Delete /></el-icon>削除</el-button>
      </template>
    </el-table-column>
  </el-table>




  <el-dialog v-model="dialogFormVisible" title="新規情報登録" width="50%">
    <el-form :model="state.form" :rules="state.rules" ref="ruleFormRef" label-width="120px" style="width: 85%">
      <el-form-item label="氏名" prop="name">
        <el-input v-model="state.form.name" autocomplete="off" placeholder="氏名を入力してください" />
      </el-form-item>
      <el-form-item label="性別">
        <el-select v-model="state.form.sex" placeholder="性別を選択してください">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
          <el-option label="その他" value="その他" />
        </el-select>
      </el-form-item>
      <el-form-item label="部署">
        <el-select v-model="state.form.department" placeholder="部署を選択してください">
          <el-option label="管理本部" value="管理本部" />
          <el-option label="経理部" value="経理部" />
          <el-option label="関東営業部" value="関東営業部" />
          <el-option label="関西営業部" value="関西営業部" />
          <el-option label="運営企画部" value="運営企画部" />
        </el-select>
      </el-form-item>
      <el-form-item label="生年月日">
        <el-input v-model="state.form.birthday" autocomplete="off" placeholder="生年月日を入力してください" />
      </el-form-item>
      <el-form-item label="メールアドレス">
        <el-input v-model="state.form.email" autocomplete="off" placeholder="メールアドレスを入力してください" />
      </el-form-item>
      <el-form-item label="電話番号">
        <el-input v-model="state.form.telephone" autocomplete="off" placeholder="電話番号を入力してください" />
      </el-form-item>
      <el-form-item label="特技">
        <el-input v-model="state.form.skill" autocomplete="off" placeholder="スキルを入力してください" />
      </el-form-item>
      <el-form-item label="最終学歴">
        <el-select v-model="state.form.educational" placeholder="最終学歴を選択してください">
          　<el-option label="高校" value="高校" />
          　<el-option label="専門学校/大学" value="専門学校/大学" />
          　<el-option label="大学院以上" value="大学院以上" />
          　<el-option label="その他" value="その他" />
        </el-select>
      </el-form-item>
      <el-form-item label="住所">
        <el-input v-model="state.form.address" autocomplete="off" placeholder="住所を入力してください" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取り消し</el-button>
        <el-button type="primary" @click="save">確定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { Edit, Delete, Plus} from '@element-plus/icons-vue'
import {getCurrentInstance, reactive, ref} from "vue";
import request from "../request";
import {ElMessage} from "element-plus";
const { proxy } = getCurrentInstance()

const state = reactive({
  tableDate: [],
  form: {},
  rules: {
    name: [
      {required: true, message: '氏名を入力してください', trigger: 'blur'}
    ]
  }
})

const load = () => {
  request.get("http://localhost:9090/home/list").then(res => {
    state.tableDate = res
  })
}
load()

const dialogFormVisible = ref(false)
const handleAdd = () => {
  dialogFormVisible.value = true
  state.form = {} //初始化数据
}

const handleEdit = (row) => {
  dialogFormVisible.value = true
  state.form = JSON.parse(JSON.stringify(row))
}

// const deleteRow = (row) =>{
//   state.form = JSON.parse(JSON.stringify(row))
//   console.log(state.form.name)
// }

const save = () => {
  proxy.$refs.ruleFormRef.validate((valid) => {
    if(valid) {
      console.log(state.form)
      request.post("http://localhost:9090/home",state.form).then(res => {
        ElMessage.success("登録しました")
        //关闭弹窗
        dialogFormVisible.value = false
        //刷新表格
        load()
      })
    }
  })
}

const deleteRow = (name) => {
  request.delete("http://localhost:9090/home" + name).then(res => {
    if (res === true) {
      ElMessage.success("削除しました")
    } else {
      ElMessage.success("失敗しました")
    }
    //刷新表格
    load()
  })
}
</script>

