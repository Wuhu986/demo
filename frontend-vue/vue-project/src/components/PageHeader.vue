<template>
  <div class="header">
    <div class="logo">Used Market</div>
    <div class="right">
      <language-switcher />
      <el-button text @click="$router.push('/orders')">我的订单</el-button>
      <el-button type="primary" size="small" @click="$router.push('/publish')">发布商品</el-button>
    </div>
  </div>
</template>

<script setup>
import LanguageSwitcher from './LanguageSwitcher.vue'
</script>

<style scoped>
.header {
  height: 60px;
  background: white;
  border-bottom: 1px solid #eee;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
}
.logo {
  font-weight: bold;
  font-size: 20px;
}
.right {
  display: flex;
  align-items: center;
  gap: 10px;
}
</style>