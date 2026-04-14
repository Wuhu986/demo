<template>
  <div class="page-container">
    <page-header />
    <el-row :gutter="20">
      <el-col
          v-for="item in products"
          :key="item.id"
          :span="6"
      >
        <product-card :product="item" @view="viewProduct" />
      </el-col>
    </el-row>

    <div class="pagination-wrapper">
      <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="pageSize"
          @current-change="handlePage"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getProductList } from '@/api/product'
import PageHeader from '@/components/PageHeader.vue'
import ProductCard from '@/components/ProductCard.vue'

const products = ref([])
const total = ref(0)
const pageSize = 8
const currentPage = ref(1)

const fetchProducts = async (page = 1) => {
  const res = await getProductList({ page, pageSize })
  products.value = res.data.records
  total.value = res.data.total
}

const handlePage = (page) => {
  currentPage.value = page
  fetchProducts(page)
}

const viewProduct = (product) => {
  window.location.href = `/product/${product.id}`
}

onMounted(() => {
  fetchProducts()
})
</script>

<style scoped>
.page-container {
  max-width: 1200px;
  margin: 0 auto;
  padding-top: 20px;
}
.pagination-wrapper {
  margin-top: 30px;
  display: flex;
  justify-content: center;
}
</style>