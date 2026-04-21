<template>
  <div class="product-card" @click="$emit('view', product)">
    <div class="img-wrap">
      <img :src="product.imageUrl || defaultImg" class="product-img" />
      <span class="status-badge" :class="product.status === '已售' ? 'sold' : 'available'">
        {{ product.status === '已售' ? '已售出' : '在售' }}
      </span>
    </div>
    <div class="card-body">
      <div class="price">¥ {{ product.price != null ? Number(product.price).toFixed(2) : '0.00' }}</div>
      <h4 class="name">{{ product.name }}</h4>
      <p class="desc">{{ product.description || '暂无描述' }}</p>
      <div class="footer">
        <div class="seller">
          <span class="avatar">{{ String(product.userId || '?').slice(-1) }}</span>
          <span class="seller-name">卖家 #{{ product.userId }}</span>
        </div>
        <span class="time">{{ formattedTime }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({ product: Object })
const defaultImg = new URL('@/assets/empty.png', import.meta.url).href

const formattedTime = computed(() => {
  const dt = props.product.createTime
  if (!dt) return ''
  const d = new Date(dt)
  if (isNaN(d.getTime())) return ''
  const diff = (Date.now() - d.getTime()) / 1000
  if (diff < 3600) return `${Math.floor(diff / 60)}分钟前`
  if (diff < 86400) return `${Math.floor(diff / 3600)}小时前`
  if (diff < 86400 * 7) return `${Math.floor(diff / 86400)}天前`
  return `${d.getMonth() + 1}月${d.getDate()}日`
})
</script>

<style scoped>
.product-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.22s, box-shadow 0.22s;
  box-shadow: 0 2px 8px rgba(0,0,0,0.07);
}
.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 28px rgba(0,0,0,0.13);
}
.img-wrap {
  position: relative;
  overflow: hidden;
}
.product-img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  display: block;
  transition: transform 0.3s;
}
.product-card:hover .product-img {
  transform: scale(1.04);
}
.status-badge {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 11px;
  padding: 3px 9px;
  border-radius: 999px;
  font-weight: 600;
  backdrop-filter: blur(4px);
}
.status-badge.available {
  background: rgba(236, 253, 245, 0.9);
  color: #10b981;
}
.status-badge.sold {
  background: rgba(243, 244, 246, 0.9);
  color: #9ca3af;
}
.card-body {
  padding: 14px 16px 12px;
}
.price {
  font-size: 20px;
  font-weight: 700;
  color: #f56c6c;
  margin-bottom: 6px;
  letter-spacing: -0.5px;
}
.name {
  font-size: 14px;
  font-weight: 600;
  color: #1a1a1a;
  margin: 0 0 5px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc {
  font-size: 12px;
  color: #9ca3af;
  margin: 0 0 12px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
  line-height: 1.5;
  min-height: 36px;
}
.footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-top: 10px;
  border-top: 1px solid #f3f4f6;
}
.seller {
  display: flex;
  align-items: center;
  gap: 6px;
}
.avatar {
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  font-size: 11px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}
.seller-name {
  font-size: 12px;
  color: #6b7280;
}
.time {
  font-size: 11px;
  color: #c0c4cc;
}
</style>