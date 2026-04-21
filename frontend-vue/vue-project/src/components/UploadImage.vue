<template>
  <div>
    <el-upload
      class="upload-box"
      action="#"
      list-type="picture-card"
      :auto-upload="false"
      :limit="1"
      :on-change="handleChange"
      :on-remove="handleRemove"
    >
      <el-icon><Plus /></el-icon>
    </el-upload>
    <div v-if="modelValue" class="hint">
      <el-icon style="color:#10b981"><Check /></el-icon> 图片已选择
    </div>
  </div>
</template>

<script setup>
import { Plus, Check } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const props = defineProps({ modelValue: String })
const emit = defineEmits(['update:modelValue'])

const compressImage = (file) =>
  new Promise((resolve) => {
    const img = new Image()
    const blobUrl = URL.createObjectURL(file)
    img.onload = () => {
      URL.revokeObjectURL(blobUrl)
      const MAX = 800
      let { width, height } = img
      if (width > height && width > MAX) {
        height = Math.round((height * MAX) / width)
        width = MAX
      } else if (height > MAX) {
        width = Math.round((width * MAX) / height)
        height = MAX
      }
      const canvas = document.createElement('canvas')
      canvas.width = width
      canvas.height = height
      canvas.getContext('2d').drawImage(img, 0, 0, width, height)
      resolve(canvas.toDataURL('image/jpeg', 0.75))
    }
    img.src = blobUrl
  })

const handleChange = async (file) => {
  if (!file.raw.type.startsWith('image/')) {
    ElMessage.error('只能上传图片')
    return
  }
  if (file.raw.size / 1024 / 1024 > 5) {
    ElMessage.error('图片不能超过 5MB')
    return
  }
  const base64 = await compressImage(file.raw)
  emit('update:modelValue', base64)
}

const handleRemove = () => emit('update:modelValue', '')
</script>

<style scoped>
.upload-box {
  width: 120px;
}
.hint {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: #10b981;
  margin-top: 6px;
}
</style>