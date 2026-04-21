<template>
  <div class="page-container">
    <page-header />
    <el-card class="form-card">
      <h2>{{ $t('product.publish') }}</h2>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item :label="$t('product.title')" prop="name">
          <el-input v-model="form.name" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item :label="$t('product.desc')" prop="description">
          <el-input
            type="textarea"
            v-model="form.description"
            placeholder="描述商品的状态、成色等信息"
            :rows="4"
          />
        </el-form-item>
        <el-form-item :label="$t('product.price')" prop="price">
          <el-input-number v-model="form.price" :min="0" :precision="2" :step="1" />
          <span class="price-hint">设为 0 表示免费赠送</span>
        </el-form-item>
        <el-form-item label="上传图片">
          <upload-image v-model="form.imageUrl" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="submitting" @click="submit">
            {{ $t('product.publish') }}
          </el-button>
          <el-button @click="$router.back()">取消</el-button>
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
const submitting = ref(false)

const form = ref({
  name: '',
  description: '',
  price: 0,
  imageUrl: '',
  status: '在售',
})

const rules = {
  name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
  description: [{ required: true, message: '请输入商品描述', trigger: 'blur' }],
  price: [{ required: true, message: '请输入价格', trigger: 'change' }],
}

const submit = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitting.value = true
  try {
    const res = await createProduct(form.value)
    if (res.data.code === 200) {
      ElMessage.success('发布成功')
      router.push('/products')
    } else {
      ElMessage.error(res.data.msg || '发布失败')
    }
  } catch {
    ElMessage.error('发布失败，请重试')
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.form-card {
  max-width: 600px;
  margin: 40px auto;
  padding: 20px;
}
.price-hint {
  margin-left: 12px;
  font-size: 12px;
  color: #9ca3af;
}
</style>