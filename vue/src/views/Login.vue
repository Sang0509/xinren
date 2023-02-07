<template>
  <div>
    <div style="width:400px; margin: 150px auto;">
      <h1 style="text-align: center;margin-bottom: 30px">社員情報管理システム</h1>
      <el-form :model="user" :rules="rules" ref="ruleFormRef" size="large">
        <el-form-item prop="email">
          <el-input v-model="user.email" :prefix-icon="Message" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="user.password" :prefix-icon="Lock" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%" @click="login">ログイン</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance} from 'vue'
import {Message,Lock} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";
import request from "../request";
import router from "../router";
const { proxy } = getCurrentInstance()

const rules = reactive({
  email: [
    { required: true, message: 'メールアドレスを入力してください', trigger: 'blur' },
  ],
  password: [
    { required: true, message: 'パスワードを入力してください', trigger: 'blur' },
    { min: 3, max: 20, message: 'パスワードの長さは3~20桁です'}
  ],
})

const user = reactive( {})
console.log(user)
const login = () => {
  proxy.$refs.ruleFormRef.validate((valid) => {
    if(valid) {
      console.log(valid)
      console.log(user)
      console.log(user.email)
      console.log(user.password)
      //promise
      //往后台发送请求 http://localhost:9090   /user/login
      //后台数据格式，{"code": "200", "msg": "", "data": null}
      request.post('/user/login',user).then(res => {
        console.log(res.code)
        // if(!/^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/.test(email)) {
        //   this.$message.warning("メールアドレスは正しくありません")
        //   return
        // }
        if (res.code === '200') { // 請求成功
          ElMessage({
            type: 'success',
            message: 'ログイン成功しました'
          })
          router.push('/')
        }else {　//請求失敗
          ElMessage({
            type: 'error',
            message: res.msg
          })
        }
      })
    } else {
      ElMessage({
        type: 'error',
          message: 'メールアドレスまたはパスワードが間違っています'
      })
    }
  })
}
</script>
