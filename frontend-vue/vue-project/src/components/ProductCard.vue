<template>
  <el-card class="product-card" shadow="hover">
    <img :src="product.imageUrl || defaultImg" class="product-image" />
    <div class="product-info">
      <h4>{{ product.title }}</h4>
      <p class="desc">{{ product.description }}</p>
      <p class="price">¥ {{ product.price }}</p>
      <el-button type="primary" size="small" @click="$emit('view', product)">查看</el-button>
    </div>
  </el-card>
</template>

<script setup>
defineProps({ product: Object })
const defaultImg = new URL('@/assets/empty.png', import.meta.url).href
</script>

<style scoped>
.product-card {
  width: 240px;
  border-radius: 10px;
}
.product-image {
  width: 100%;
  height: 160px;
  object-fit: cover;
  border-radius: 6px;
}
.product-info {
  padding: 10px 0;
}
.desc {
  font-size: 12px;
  color: #666;
  margin: 5px 0;
}
.price {
  color: #e74c3c;
  font-weight: bold;
}
</style>