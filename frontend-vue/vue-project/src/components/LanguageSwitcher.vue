<template>
  <el-dropdown @command="changeLang">
    <span class="el-dropdown-link">
      🌐 {{ currentLang === 'zh' ? '中文' : '日本語' }} <el-icon><arrow-down /></el-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item command="zh">中文</el-dropdown-item>
        <el-dropdown-item command="ja">日本語</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup>
import { ArrowDown } from '@element-plus/icons-vue'
import { useI18n } from 'vue-i18n'

const { locale } = useI18n()
const currentLang = locale.value

const changeLang = (lang) => {
  locale.value = lang
  localStorage.setItem('lang', lang)
  location.reload()
}
</script>