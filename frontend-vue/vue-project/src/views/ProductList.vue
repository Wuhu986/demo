<template>
  <div class="store-page">
    <page-header />

    <!-- Banner -->
    <div class="banner">
      <div class="banner-inner">
        <h1 class="banner-title">二手好物，流转新生</h1>
        <p class="banner-sub">发现身边闲置宝贝，让好物焕发新价值</p>
        <div class="search-wrap">
          <el-input
            v-model="keyword"
            placeholder="搜索商品名称或描述..."
            size="large"
            clearable
            @keyup.enter="handleSearch"
            @clear="resetSearch"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
            <template #append>
              <el-button @click="handleSearch">搜索</el-button>
            </template>
          </el-input>
        </div>
      </div>
    </div>

    <!-- Main content -->
    <div class="content">
      <!-- Filter bar -->
      <div class="filter-bar">
        <span class="result-info">
          <template v-if="activeKeyword">
            「{{ activeKeyword }}」相关商品共
            <b>{{ total }}</b> 件
          </template>
          <template v-else>
            全部商品共 <b>{{ total }}</b> 件
          </template>
        </span>
        <div class="sort-btns">
          <el-button
            v-for="s in sortOptions"
            :key="s.value"
            :type="sortBy === s.value ? 'primary' : 'default'"
            size="small"
            plain
            @click="sortBy = s.value"
          >{{ s.label }}</el-button>
        </div>
      </div>

      <!-- Loading skeleton -->
      <div v-if="loading" class="product-grid">
        <div v-for="n in pageSize" :key="n" class="skeleton-card">
          <el-skeleton animated>
            <template #template>
              <el-skeleton-item
                variant="image"
                style="width:100%;height:200px;border-radius:12px 12px 0 0"
              />
              <div style="padding:14px 16px">
                <el-skeleton-item variant="text" style="width:40%;margin-bottom:8px" />
                <el-skeleton-item variant="h3" style="width:75%" />
                <el-skeleton-item variant="text" style="width:90%;margin-top:6px" />
                <el-skeleton-item variant="text" style="width:60%;margin-top:4px" />
              </div>
            </template>
          </el-skeleton>
        </div>
      </div>

      <!-- Product grid -->
      <div v-else-if="sortedProducts.length > 0" class="product-grid">
        <product-card
          v-for="item in sortedProducts"
          :key="item.id"
          :product="item"
          @view="viewProduct"
        />
      </div>

      <!-- Empty state -->
      <div v-else class="empty-wrap">
        <el-empty
          :description="activeKeyword ? `没有找到「${activeKeyword}」相关商品` : '暂无商品，快来发布第一件吧！'"
        >
          <el-button v-if="activeKeyword" @click="resetSearch">查看全部商品</el-button>
          <el-button v-else type="primary" @click="$router.push('/publish')">去发布</el-button>
        </el-empty>
      </div>

      <!-- Pagination -->
      <div v-if="total > pageSize" class="pagination-wrap">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="pageSize"
          :current-page="currentPage"
          @current-change="handlePage"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getProductList, searchProducts } from '@/api/product'
import PageHeader from '@/components/PageHeader.vue'
import ProductCard from '@/components/ProductCard.vue'
import { Search } from '@element-plus/icons-vue'

const router = useRouter()

const products = ref([])
const total = ref(0)
const pageSize = 12
const currentPage = ref(1)
const keyword = ref('')
const activeKeyword = ref('')
const sortBy = ref('newest')
const loading = ref(false)

const sortOptions = [
  { label: '最新发布', value: 'newest' },
  { label: '价格最低', value: 'price_asc' },
  { label: '价格最高', value: 'price_desc' },
]

const sortedProducts = computed(() => {
  const list = [...products.value]
  if (sortBy.value === 'price_asc') return list.sort((a, b) => a.price - b.price)
  if (sortBy.value === 'price_desc') return list.sort((a, b) => b.price - a.price)
  return list
})

const fetchProducts = async (page = 1) => {
  loading.value = true
  try {
    let res
    if (activeKeyword.value.trim()) {
      res = await searchProducts(activeKeyword.value.trim(), { pageNum: page, pageSize })
    } else {
      res = await getProductList({ pageNum: page, pageSize })
    }
    products.value = res.data?.data?.records ?? []
    total.value = res.data?.data?.total ?? 0
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  activeKeyword.value = keyword.value.trim()
  currentPage.value = 1
  fetchProducts(1)
}

const resetSearch = () => {
  keyword.value = ''
  activeKeyword.value = ''
  currentPage.value = 1
  fetchProducts(1)
}

const handlePage = (page) => {
  currentPage.value = page
  fetchProducts(page)
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

const viewProduct = (product) => {
  router.push(`/product/${product.id}`)
}

onMounted(() => fetchProducts())
</script>

<style scoped>
.store-page {
  min-height: 100vh;
  background: #f5f6f8;
}

/* Banner */
.banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 56px 24px 52px;
  text-align: center;
  color: white;
}
.banner-inner {
  max-width: 640px;
  margin: 0 auto;
}
.banner-title {
  font-size: 30px;
  font-weight: 700;
  margin: 0 0 10px;
  letter-spacing: 1px;
}
.banner-sub {
  font-size: 15px;
  opacity: 0.82;
  margin: 0 0 28px;
}
.search-wrap {
  max-width: 540px;
  margin: 0 auto;
}
:deep(.search-wrap .el-input__wrapper) {
  border-radius: 8px 0 0 8px;
}
:deep(.search-wrap .el-input-group__append) {
  border-radius: 0 8px 8px 0;
  background: white;
  color: #667eea;
  font-weight: 600;
  cursor: pointer;
}
:deep(.search-wrap .el-input-group__append:hover) {
  background: #f0f0ff;
}

/* Content area */
.content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px 16px 60px;
}

/* Filter bar */
.filter-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: white;
  border-radius: 10px;
  padding: 12px 20px;
  margin-bottom: 20px;
  box-shadow: 0 1px 4px rgba(0,0,0,0.06);
}
.result-info {
  font-size: 14px;
  color: #6b7280;
}
.result-info b {
  color: #667eea;
  font-weight: 700;
}
.sort-btns {
  display: flex;
  gap: 8px;
}

/* Product grid */
.product-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

/* Skeleton card */
.skeleton-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
}

/* Empty state */
.empty-wrap {
  background: white;
  border-radius: 12px;
  padding: 60px 0;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
}

/* Pagination */
.pagination-wrap {
  display: flex;
  justify-content: center;
  margin-top: 32px;
}

@media (max-width: 960px) {
  .product-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}
@media (max-width: 640px) {
  .product-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  .banner-title {
    font-size: 22px;
  }
}
</style>