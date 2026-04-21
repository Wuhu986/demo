<template>
  <div class="page-container">
    <page-header />
    <div v-if="product" class="product-detail">
      <div class="img-wrap">
        <img :src="product.imageUrl || defaultImg" class="product-img" />
        <div v-if="isSold" class="sold-overlay">已售出</div>
      </div>
      <div class="product-info">
        <div class="name-row">
          <h2>{{ product.name }}</h2>
          <el-tag v-if="isSold" type="info">已售出</el-tag>
          <el-tag v-else type="success">在售</el-tag>
        </div>
        <p class="desc">{{ product.description }}</p>
        <p class="price">¥ {{ Number(product.price).toFixed(2) }}</p>
        <p class="publisher">{{ $t('product.publisher') }}：卖家 #{{ product.userId }}</p>

        <template v-if="!isSold">
          <el-button v-if="product.price > 0" type="success" size="large" @click="buy">
            {{ $t('product.buy') }}
          </el-button>
          <el-button v-else type="primary" size="large" @click="receive">
            {{ $t('product.receive') }}
          </el-button>
        </template>
        <el-button v-else disabled size="large">该商品已售出</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getProductById } from '@/api/product'
import PageHeader from '@/components/PageHeader.vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const product = ref(null)
const defaultImg = new URL('@/assets/empty.png', import.meta.url).href

const isSold = computed(() => product.value?.status === '已售')

const fetchProduct = async () => {
  try {
    const res = await getProductById(route.params.id)
    if (res.data.code !== 200) throw new Error(res.data.msg)
    product.value = res.data.data
  } catch {
    ElMessage.error('商品不存在')
    router.push('/products')
  }
}

const buy = () => router.push(`/pay/${product.value.id}`)

const receive = () => ElMessage.success('您已领取该物品（模拟）')

onMounted(() => fetchProduct())
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
.img-wrap {
  position: relative;
  flex-shrink: 0;
}
.product-img {
  width: 300px;
  height: 300px;
  object-fit: cover;
  border-radius: 10px;
}
.sold-overlay {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.45);
  color: white;
  font-size: 28px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  letter-spacing: 4px;
}
.product-info {
  flex: 1;
}
.name-row {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
}
.name-row h2 {
  margin: 0;
}
.desc {
  margin: 10px 0;
  color: #555;
  line-height: 1.6;
}
.price {
  font-size: 28px;
  font-weight: 700;
  color: #f56c6c;
  margin: 15px 0;
}
.publisher {
  font-size: 14px;
  color: #999;
  margin-bottom: 24px;
}
</style>