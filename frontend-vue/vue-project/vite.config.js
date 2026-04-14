import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

export default defineConfig({
  // base: '/vue/',
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  server: {
    port: 5173,
    open: true,
    proxy: {
      // 后端接口代理
      '/user': 'http://localhost:8080',
      '/product': 'http://localhost:8080',
      '/order': 'http://localhost:8080',
      '/payment': 'http://localhost:8080'
    },
  }
})