import { createApp } from 'vue'
import App from './App.vue'
// import service from './utils/request'

// Element Plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// 国际化
import i18n from './i18n'

// 路由
import router from './router'

// 全局样式
import '@/assets/global.css'

const app = createApp(App)

app.use(ElementPlus)
app.use(i18n)
app.use(router)
// app.use(service)

app.mount('#app')