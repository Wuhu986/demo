<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>{{ $t('login.title') }}</h2>
      <el-form :model="form" :rules="rules" ref="formRef" label-position="top">
        <el-form-item :label="$t('login.phone')" prop="data.phone">
          <el-input v-model="form.data.phone" />
        </el-form-item>
        <el-form-item :label="$t('login.password')" prop="data.password">
          <el-input type="password" v-model="form.data.password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitLogin">{{ $t('login.submit') }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="text" @click="goRegister">{{ $t('login.toRegister') }}</el-button>
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
import { login, getUserById } from '@/api/user'
import LanguageSwitcher from '@/components/LanguageSwitcher.vue'

const router = useRouter()
const formRef = ref()

// 调整后的数据结构
const form = ref({
  data: {
    phone: '',
    password: ''
  },
  action: 'login'
})

// 调整后的校验规则
const rules = {
  'data.phone': [{ required: true, message: 'Phone is required', trigger: 'blur' }],
  'data.password': [{ required: true, message: 'Password is required', trigger: 'blur' }],
}

const submitLogin = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const res = await login(form.value)
        if (res.data.code === 200) {
          const token = res.data.data
          localStorage.setItem('token', token)
          try {
            const payload = JSON.parse(atob(token.split('.')[1]))
            const userId = payload.sub
            localStorage.setItem('userId', userId)
            const userRes = await getUserById(userId)
            const nickname = userRes.data?.data?.nickname || ''
            localStorage.setItem('nickname', nickname)
          } catch (e) {
            // JWT decode failed, not critical
          }
          ElMessage.success('登录成功')
          router.push('/products')
        } else if (res.data.code === 401) {
          ElMessage.error('手机号或密码错误')
        }
      } catch (err) {
        ElMessage.error('登录失败')
      }
    }
  })
}

const goRegister = () => router.push('/register')
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