<template>
  <el-card class="order-card" shadow="always">
    <div class="order-info">
      <p>订单号：{{ order.id }}</p>
      <p>商品：{{ order.productName }}</p>
      <p>金额：¥ {{ order.amount }}</p>
      <p>状态：{{ order.status }}</p>
    </div>
  </el-card>
</template>

<script setup>
defineProps({ order: Object })
</script>

<style scoped>
.order-card {
  margin-bottom: 12px;
}
.order-info {
  line-height: 1.6;
}
</style>