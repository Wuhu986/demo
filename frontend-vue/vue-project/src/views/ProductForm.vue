<template>
  <div class="page-container">
    <page-header />
    <el-card class="form-card">
      <h2>{{ $t('product.publish') }}</h2>
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item :label="$t('product.title')" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item :label="$t('product.desc')" prop="description">
          <el-input type="textarea" v-model="form.description" />
        </el-form-item>
        <el-form-item :label="$t('product.price')" prop="price">
          <el-input-number v-model="form.price" :min="0" />
        </el-form-item>
        <el-form-item label="上传图片">
          <upload-image />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit">{{ $t('product.publish') }}</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import PageHeader from '@/components/PageHeader.vue'
import UploadImage from '@/components/UploadImage.vue'
import { createProduct } from '@/api/product'

const router = useRouter()
const formRef = ref()
const form = ref({
  title: '',
  description: '',
  price: 0,
  imageUrl: '',
})

const submit = async () => {
  await formRef.value.validate()
  try {
    await createProduct(form.value)
    ElMessage.success('发布成功')
    router.push('/products')
  } catch {
    ElMessage.error('发布失败')
  }
}
</script>

<style scoped>
.form-card {
  max-width: 600px;
  margin: 40px auto;
  padding: 20px;
}
</style>