<template>
  <el-upload
      class="upload-box"
      action="#"
      list-type="picture-card"
      :auto-upload="false"
      :on-change="handleChange"
  >
    <el-icon><Plus /></el-icon>
  </el-upload>
</template>

<script setup>
import { Plus } from '@element-plus/icons-vue'

const handleChange = (file) => {
  // 这里你可以使用 base64 或者 FormData 上传
  console.log(file)
}
</script>

<style scoped>
.upload-box {
  width: 200px;
}
</style>