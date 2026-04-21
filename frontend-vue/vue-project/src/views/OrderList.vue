<template>
  <div class="page-container">
    <page-header />
    <div class="order-wrap">
      <h2 class="section-title">我的订单</h2>

      <div v-if="loading">
        <el-skeleton v-for="n in 3" :key="n" animated style="margin-bottom:12px">
          <template #template>
            <el-skeleton-item variant="rect" style="height:90px;border-radius:10px" />
          </template>
        </el-skeleton>
      </div>

      <template v-else-if="orders.length > 0">
        <order-card v-for="item in orders" :key="item.id" :order="item" />
      </template>

      <el-empty v-else description="暂无订单记录">
        <el-button type="primary" @click="$router.push('/products')">去逛逛</el-button>
      </el-empty>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import PageHeader from '@/components/PageHeader.vue'
import OrderCard from '@/components/OrderCard.vue'
import { getMyOrders } from '@/api/order'

const orders = ref([])
const loading = ref(false)

const fetchOrders = async () => {
  loading.value = true
  try {
    const res = await getMyOrders()
    if (res.data.code === 200) {
      orders.value = res.data.data || []
    } else {
      ElMessage.error(res.data.msg || '获取订单失败')
    }
  } finally {
    loading.value = false
  }
}

onMounted(() => fetchOrders())
</script>

<style scoped>
.order-wrap {
  max-width: 800px;
  margin: 0 auto;
}
.section-title {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 20px;
}
</style>