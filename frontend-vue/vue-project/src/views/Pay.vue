<template>
  <div class="page-container">
    <page-header />
    <div class="pay-wrap">
      <el-card v-if="product" class="pay-box">
        <h2 class="title">{{ $t('pay.title') }}</h2>

        <div class="product-preview">
          <img :src="product.imageUrl || defaultImg" class="preview-img" />
          <div class="preview-info">
            <h3>{{ product.name }}</h3>
            <p class="preview-desc">{{ product.description }}</p>
          </div>
        </div>

        <el-divider />

        <div class="info-rows">
          <div class="info-row">
            <span class="label">支付金额</span>
            <span class="amount">¥ {{ price }}</span>
          </div>
          <div class="info-row">
            <span class="label">{{ $t('pay.currentBalance') }}</span>
            <span :class="['balance-val', insufficient ? 'red' : 'green']">
              ¥ {{ currentBalance }}
            </span>
          </div>
          <div class="info-row">
            <span class="label">{{ $t('pay.afterBalance') }}</span>
            <span :class="['balance-val', insufficient ? 'red' : '']">
              {{ insufficient ? '—' : `¥ ${afterBalance}` }}
            </span>
          </div>
        </div>

        <el-alert
          v-if="insufficient"
          :title="$t('pay.insufficient')"
          type="error"
          show-icon
          :closable="false"
          style="margin-top:16px"
        />

        <div class="btn-group">
          <el-button
            type="primary"
            size="large"
            :loading="paying"
            :disabled="insufficient"
            style="flex:1"
            @click="pay"
          >
            {{ $t('pay.confirm') }}
          </el-button>
          <el-button size="large" style="flex:1" @click="$router.back()">
            {{ $t('pay.cancel') }}
          </el-button>
        </div>
      </el-card>

      <div v-else class="loading-wrap">
        <el-skeleton animated style="width:500px" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import PageHeader from '@/components/PageHeader.vue'
import { getProductById } from '@/api/product'
import { getBalance } from '@/api/user'
import { purchase } from '@/api/order'

const route = useRoute()
const router = useRouter()
const product = ref(null)
const balance = ref(0)
const paying = ref(false)
const defaultImg = new URL('@/assets/empty.png', import.meta.url).href

const productId = route.params.id

const price = computed(() => Number(product.value?.price || 0).toFixed(2))
const currentBalance = computed(() => Number(balance.value || 0).toFixed(2))
const afterBalance = computed(() => (balance.value - (product.value?.price || 0)).toFixed(2))
const insufficient = computed(() => balance.value < (product.value?.price || 0))

onMounted(async () => {
  const [productRes, balanceRes] = await Promise.all([
    getProductById(productId),
    getBalance(),
  ])
  if (productRes.data.code === 200) {
    product.value = productRes.data.data
  } else {
    ElMessage.error('商品不存在')
    router.push('/products')
    return
  }
  if (balanceRes.data.code === 200) {
    balance.value = balanceRes.data.data ?? 0
  }
})

const pay = async () => {
  if (insufficient.value) return
  paying.value = true
  try {
    const res = await purchase(productId)
    if (res.data.code === 200) {
      ElMessage.success('支付成功！')
      router.push('/orders')
    } else {
      ElMessage.error(res.data.msg || '支付失败')
    }
  } catch {
    ElMessage.error('支付失败，请重试')
  } finally {
    paying.value = false
  }
}
</script>

<style scoped>
.pay-wrap {
  display: flex;
  justify-content: center;
  padding: 60px 16px;
}
.pay-box {
  width: 500px;
  padding: 10px 20px 20px;
}
.title {
  margin: 0 0 24px;
  font-size: 20px;
  font-weight: 600;
}
.product-preview {
  display: flex;
  gap: 16px;
  align-items: flex-start;
}
.preview-img {
  width: 90px;
  height: 90px;
  object-fit: cover;
  border-radius: 8px;
  flex-shrink: 0;
}
.preview-info h3 { margin: 0 0 6px; font-size: 16px; }
.preview-desc {
  font-size: 13px;
  color: #9ca3af;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
}
.info-rows { display: flex; flex-direction: column; gap: 12px; }
.info-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.label { font-size: 14px; color: #6b7280; }
.amount {
  font-size: 26px;
  font-weight: 700;
  color: #f56c6c;
}
.balance-val {
  font-size: 16px;
  font-weight: 600;
  color: #374151;
}
.balance-val.green { color: #10b981; }
.balance-val.red { color: #f56c6c; }
.btn-group {
  display: flex;
  gap: 12px;
  margin-top: 24px;
}
.loading-wrap { margin-top: 60px; }
</style>