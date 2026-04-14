<template>
  <div class="page-container">
    <page-header />
    <el-card class="pay-box">
      <h2>支付确认</h2>
      <p>商品ID：{{ route.params.id }}</p>
      <p>金额：¥ {{ price }}</p>
      <el-button type="primary" @click="pay">确认支付</el-button>
    </el-card>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import PageHeader from '@/components/PageHeader.vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()

const price = 88 // 模拟，建议从后端获取真实金额

const pay = () => {
  ElMessage.success('支付成功（模拟）')
  router.push('/orders')
}
</script>

<style scoped>
.pay-box {
  max-width: 500px;
  margin: 60px auto;
  padding: 30px;
  text-align: center;
}
</style>