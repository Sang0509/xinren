<template>
  <div style="margin-bottom: 50px">
    <el-input style="width: 260px; margin-right: 10px" v-model="name" placeholder="氏名を入力してください" clearable></el-input>
    <el-input style="width: 260px; margin-right: 10px" v-model="email" placeholder="メールアドレスを入力してください" clearable></el-input>
    <el-input style="width: 260px; margin-right: 10px" v-model="telephone" placeholder="電話番号を入力してください" clearable></el-input>
    <el-button type="primary" @click="load"><el-icon style="margin-right: 3px"><Search /></el-icon>検索</el-button>
    <el-button type="primary" @click="handleAdd"><el-icon style="margin-right: 3px"><Plus /></el-icon>新規登録</el-button>
    <div class="right-items" style="float: right;">
      <el-dropdown>
        <el-button type="success">
          <el-icon style="margin-right: 3px"><User /></el-icon>管理員<el-icon class="el-icon--right"><ArrowDown /></el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item><el-icon class="el-icon--right"><Setting /></el-icon>各種設定</el-dropdown-item>
            <el-dropdown-item><a href="http://localhost:5173/login">
              <el-icon class="el-icon--right"><SwitchButton /></el-icon>ログアウト</a></el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      </div>
  </div>
  <el-table
      :data="state.tableDate"
      border
      :default-sort="{ prop: 'name, sex, department, birthday, email, telephone, educational, address', order: 'descending' }"
      style="width: 100%">
    <el-table-column
        sortable
        fixed
        prop="name"
        label="氏名"
        width="150">
    </el-table-column>
    <el-table-column
        sortable
        prop="sex"
        label="性別"
        width="100">
    </el-table-column>
    <el-table-column
        sortable
        prop="department"
        label="部署"
        width="120">
    </el-table-column>
    <el-table-column
        sortable
        prop="birthday"
        label="生年月日"
        width="180">
    </el-table-column>
    　<el-table-column
      sortable
      prop="email"
      label="メールアドレス"
      width="250">
  </el-table-column>
    　<el-table-column
      sortable
      prop="telephone"
      label="電話番号"
      width="180">
  </el-table-column>
<!--    　<el-table-column-->
<!--      prop="skill"-->
<!--      label="特技"-->
<!--      width="150">-->
<!--  </el-table-column>-->
    <el-table-column
        sortable
        prop="educational"
        label="最終学歴"
        width="150">
    </el-table-column>
    <el-table-column
        sortable
        prop="address"
        label="住所"
        width="300">
    </el-table-column>
    <el-table-column
        fixed="right"
        label="操作"
        width="220">
      <template #default="scope">
        <el-button type="warning" text="warning" size="small" @click="syosai(scope.row)"><el-icon style="margin-right: 3px"><ZoomIn /></el-icon>詳細</el-button>
        <el-button type="text" size="small" @click="handleEdit(scope.row)"><el-icon style="margin-right: 3px"><Edit /></el-icon>編集</el-button>
        <el-popconfirm title="削除してよろしいですか？" @confirm="deleteRow(scope.row.name)">
          <template #reference>
            <el-button link type="danger" size="small"><el-icon style="margin-right: 3px"><Delete /></el-icon>削除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

  <div style="margin: 10px 0">
    <el-pagination
        background
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 200, 300, 400]"
        :small="small"
        :disabled="false"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>

  <el-dialog v-model="state.use" >
    <el-descriptions title="社員情報詳細" :column="1" border>
      <el-descriptions-item
          label="氏名"
          label-align="right"
          align="center"
          label-class-name="my-label"
          class-name="my-content"
          width="150px"
      >{{state.use.name}}</el-descriptions-item
      >
      <el-descriptions-item label="性別" label-align="right" align="center"
      >{{state.use.sex}}</el-descriptions-item
      >
      <el-descriptions-item label="部署" label-align="right" align="center"
      >{{state.use.department}}</el-descriptions-item
      >
      <el-descriptions-item label="生年月日" label-align="right" align="center">
        {{state.use.birthday}}
      </el-descriptions-item>
      <el-descriptions-item label="メールアドレス" label-align="right" align="center"
      >{{state.use.email}}</el-descriptions-item
      >
      <el-descriptions-item label="電話番号" label-align="right" align="center"
      >{{state.use.telephone}}</el-descriptions-item
      >
      <el-descriptions-item label="特技" label-align="right" align="center"
      >{{state.use.skill}}</el-descriptions-item
      >
      <el-descriptions-item label="最終学歴" label-align="right" align="center"
      >{{state.use.educational}}</el-descriptions-item
      >
      <el-descriptions-item label="住所" label-align="right" align="center"
      >{{state.use.address}}</el-descriptions-item
      >
    </el-descriptions>
  </el-dialog>



  <el-dialog v-model="dialogFormVisible" title="社員情報登録" width="50%">
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
        <el-date-picker
            v-model="state.form.birthday"
            type="date"
            value-format="YYYY-MM-DD"
            format="YYYY-MM-DD"
            placeholder="Pick a day"
            :disabled-date="disabledDate"
            :shortcuts="shortcuts"
            :size="size"
        />
      </el-form-item>
      <el-form-item label="メールアドレス">
        <el-input v-model="state.form.email" autocomplete="off" placeholder="メールアドレスを入力してください" />
      </el-form-item>
      <el-form-item label="電話番号">
        <el-input v-model="state.form.telephone" autocompl  ete="off" placeholder="電話番号を入力してください" />
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
    <el-upload
        ref="upload"
        class="upload-demo"
        action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
        :limit="1"
        :on-exceed="handleExceed"
        :auto-upload="false"
    >
      <template #trigger>
        <el-button size="small" type="default">ファイルを選択する</el-button>
      </template>
      <el-button size="small" class="ml-3" type="primary" text="primary" @click="submitUpload">
        アップロード
      </el-button>
    </el-upload>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取り消し</el-button>
        <el-button type="primary" @click="save">確定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { Edit, Delete, Plus, Search, ArrowDown, ZoomIn, User, Setting, SwitchButton} from '@element-plus/icons-vue'
import {getCurrentInstance, reactive, ref} from "vue";
import request from "../request";
import {ElMessage} from "element-plus";
const { proxy } = getCurrentInstance()

const syosai = (row) => {
  dialogFormVisible.value = false
  state.use = JSON.parse(JSON.stringify(row))
}

const shortcuts = [
  {
    text: 'Today',
    value: new Date(),
  },
  {
    text: 'Yesterday',
    value: () => {
      const date = new Date()
      date.setTime(date.getTime() - 3600 * 1000 * 24)
      return date
    },
  },
  {
    text: 'A week ago',
    value: () => {
      const date = new Date()
      date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
      return date
    },
  },
]
// const disabledDate = (time: Date) => {
//   return time.getTime() > Date.now()
// }

const checkEmail = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('メールアドレスを入力してください'))
  }
}

const state = reactive({
  tableDate: [],
  form: {},
  rules: {
    name: [
      {required: true, message: '氏名を入力してください', trigger: 'blur'}
    ],
    email:[
      { validator: checkEmail, trigger: 'blur'}
    ]
  }
})

const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const name = ref('')
const email = ref('')
const telephone = ref('')
const load = () => {
  request.get("http://localhost:9090/home/list/page/", {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      name: name.value,
      email: email.value,
      telephone: telephone.value
    }
  }).then(res => {
    if (res.code === '200') {
      state.tableDate = res.data.data
      total.value = res.data.total
    }
  })
}
load()

const handleSizeChange = (val) => {
  pageSize.value = val
  load()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  load()
}

// const dialogDetailVisible = ref(false)
const dialogFormVisible = ref(false)
const handleAdd = () => {
  dialogFormVisible.value = true
  state.form = {} //初始化数据
}

const handleEdit = (row) => {
  dialogFormVisible.value = true
  state.form = JSON.parse(JSON.stringify(row))
}

const save = () => {
  proxy.$refs.ruleFormRef.validate((valid) => {
    if(valid) {
      request.post("http://localhost:9090/home",state.form).then(res => {
        if (res.code === '200') {
          ElMessage.success("登録しました")
          //关闭弹窗
          dialogFormVisible.value = false
          //刷新表格
          load()
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}

const deleteRow = (name) => {
  request.delete("http://localhost:9090/home/" + name).then(res => {
    if (res.code === '200' && res.data === true) {
      ElMessage.success("削除しました")
    } else {
      ElMessage.success("失敗しました")
    }
    //刷新表格
    load()
  })
}



</script>

