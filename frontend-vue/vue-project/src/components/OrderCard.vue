<template>
  <div class="order-card">
    <div class="order-left">
      <div class="order-id">订单号：{{ order.id }}</div>
      <div class="order-meta">商品 ID：{{ order.productId }}</div>
      <div class="order-meta">下单时间：{{ order.createTime || '—' }}</div>
    </div>
    <div class="order-right">
      <div class="order-price">¥ {{ Number(order.price || 0).toFixed(2) }}</div>
      <el-tag :type="statusType" size="small">{{ order.status }}</el-tag>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({ order: Object })

const statusType = computed(() => {
  const s = props.order.status
  if (s === '已支付') return 'success'
  if (s === '已取消') return 'info'
  if (s === '待支付') return 'warning'
  return ''
})
</script>

<style scoped>
.order-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: white;
  border-radius: 10px;
  padding: 16px 20px;
  margin-bottom: 12px;
  box-shadow: 0 1px 4px rgba(0,0,0,0.07);
}
.order-left {
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.order-id {
  font-size: 13px;
  font-weight: 600;
  color: #374151;
}
.order-meta {
  font-size: 12px;
  color: #9ca3af;
}
.order-right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 8px;
}
.order-price {
  font-size: 18px;
  font-weight: 700;
  color: #f56c6c;
}
</style>