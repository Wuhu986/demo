<template>
  <div class="filter-page">
    <el-form :model="filter" inline class="filter-form">
      <el-form-item label="用户名">
        <el-input v-model="filter.username" placeholder="模糊查询" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="filterData">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="filteredUsers" border style="width: 100%; margin-top: 20px">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="phone" label="电话" />
      <el-table-column prop="role" label="角色" />
    </el-table>
  </div>
</template>

<script setup>
import { reactive, ref, computed } from 'vue'

const filter = reactive({
  username: ''
})

// 模拟数据
const users = ref([
  { id: 1, username: 'alice', email: 'alice@example.com', phone: '123', role: 'admin' },
  { id: 2, username: 'bob', email: 'bob@example.com', phone: '456', role: 'user' },
  { id: 3, username: 'charlie', email: 'charlie@example.com', phone: '789', role: 'user' }
])

const filteredUsers = computed(() => {
  return users.value.filter(u =>
      u.username.includes(filter.username)
  )
})

const filterData = () => {
  // 由于用了 computed，输入自动过滤，不需要发请求
}
</script>

<style scoped>
.filter-form {
  margin: 20px 0;
}
</style>