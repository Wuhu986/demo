<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>{{ $t('register.title') }}</h2>
      <el-form :model="form" :rules="rules" ref="formRef" label-position="top">
        <el-form-item :label="$t('register.phone')" prop="data.phone">
          <el-input v-model="form.data.phone" type="text"/>
        </el-form-item>
        <el-form-item :label="$t('register.password')" prop="data.password">
          <el-input v-model="form.data.password" type="password" maxlength="18" show-word-limit show-password/>
        </el-form-item>
        <el-form-item :label="$t('register.nickname')" prop="data.nickname">
          <el-input v-model="form.data.nickname" type="text" maxlength="6" show-word-limit/>
        </el-form-item>
        <el-form-item :label="$t('register.birth')" prop="data.birth">
          <el-date-picker v-model="form.data.birth" type="date" placeholder="请选择日期" value-format="YYYY-MM-DD">
            <el-input />
          </el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('register.email')" prop="data.email">
          <el-input v-model="form.data.email" type="text"/>
        </el-form-item>
        <el-form-item :label="$t('register.checkEmail')" prop="data.checkEmail">
          <el-input v-model="form.data.checkEmail" type="text"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit">{{ $t('register.submit') }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="text" @click="goLogin">{{ $t('register.toLogin') }}</el-button>
        </el-form-item>
        <language-switcher />
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import LanguageSwitcher from '@/components/LanguageSwitcher.vue'
import {login} from '@/api/user'

const router = useRouter()
const formRef = ref()

// 数据结构修改
const form = ref({
  data: {
    phone: '',
    password: '',
    nickname: '',
    birth: null,
    email: '',
    checkEmail: ''
  },
  action: 'register'
})

// 校验规则修改（配合新数据结构）
const rules = {
  'data.phone': [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  'data.password': [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '至少6位字符', trigger: 'blur' },
  ],
  'data.nickname':[{ required: true,message: '请输入昵称', trigger: 'blur' }],
  'data.email':[
    { required: true,message: '请输入邮箱', trigger: 'blur' },
    { type: 'email',message: '邮箱格式不正确', trigger: 'blur' },
  ],
  'data.checkEmail':[
    { required: true,message: '请输入邮箱', trigger: 'blur' },
    { type: 'email',message: '邮箱格式不正确', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== form.value.data.email) {
          callback(new Error('两次邮箱不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
}

const submit = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const res = await login(form.value)
        console.log(res)
        if(res.data.code === 200){
          ElMessage.success('注册成功')
          router.push('/login')
        }
      } catch {
        ElMessage.error('注册失败')
      }
    }
  })
}

const goLogin = () => router.push('/login')
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 90vh;
}
.login-card {
  width: 400px;
  padding: 30px;
  box-shadow: 0 4px 14px rgba(0, 0, 0, 0.1);
}
</style>