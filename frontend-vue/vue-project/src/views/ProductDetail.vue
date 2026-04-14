<template>
  <div class="page-container">
    <page-header />
    <div v-if="product" class="product-detail">
      <img :src="product.imageUrl || defaultImg" class="product-img" />
      <div class="product-info">
        <h2>{{ product.title }}</h2>
        <p class="desc">{{ product.description }}</p>
        <p class="price">¥ {{ product.price }}</p>
        <p class="publisher">{{ $t('product.publisher') }}：{{ product.publisherName || '用户' }}</p>
        <el-button
            type="success"
            @click="buy"
            v-if="product.price > 0"
        >
          {{ $t('product.buy') }}
        </el-button>
        <el-button
            type="info"
            @click="receive"
            v-else
        >
          {{ $t('product.receive') }}
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getProductById } from '@/api/product'
import PageHeader from '@/components/PageHeader.vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const product = ref(null)
const defaultImg = new URL('@/assets/empty.png', import.meta.url).href

const fetchProduct = async () => {
  const id = route.params.id
  try {
    const res = await getProductById(id)
    product.value = res.data
  } catch {
    ElMessage.error('商品不存在')
    router.push('/products')
  }
}

const buy = () => {
  router.push(`/pay/${product.value.id}`)
}

const receive = () => {
  ElMessage.success('您已领取该物品（模拟）')
}

onMounted(() => {
  fetchProduct()
})
</script>

<style scoped>
.product-detail {
  display: flex;
  gap: 30px;
  background: white;
  border-radius: 12px;
  padding: 30px;
  margin-top: 40px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
}
.product-img {
  width: 300px;
  height: 300px;
  object-fit: cover;
  border-radius: 10px;
}
.product-info {
  flex: 1;
}
.desc {
  margin: 10px 0;
  color: #555;
}
.price {
  font-size: 24px;
  color: #e74c3c;
  margin: 15px 0;
}
.publisher {
  font-size: 14px;
  color: #999;
  margin-bottom: 20px;
}
</style>