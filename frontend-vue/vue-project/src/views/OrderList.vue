<template>
  <div class="page-container">
    <page-header />
    <el-card class="order-wrap">
      <h2>{{ $t('order.list') }}</h2>
      <order-card
          v-for="item in orders"
          :key="item.id"
          :order="item"
      />
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import PageHeader from '@/components/PageHeader.vue'
import OrderCard from '@/components/OrderCard.vue'
import { getUserOrders } from '@/api/order'

const orders = ref([])

const fetchOrders = async () => {
  const userId = localStorage.getItem('userId') || 1 // 测试用，实际请替换为真实登录信息
  const res = await getUserOrders(userId)
  orders.value = res.data
}

onMounted(() => {
  fetchOrders()
})
</script>

<style scoped>
.order-wrap {
  max-width: 800px;
  margin: 40px auto;
}
</style>