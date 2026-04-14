import { createRouter, createWebHistory } from 'vue-router'

// 页面组件按需加载
const Login = () => import('@/views/Login.vue')
const Register = () => import('@/views/Register.vue')
const ProductList = () => import('@/views/ProductList.vue')
const ProductDetail = () => import('@/views/ProductDetail.vue')
const ProductForm = () => import('@/views/ProductForm.vue')
const OrderList = () => import('@/views/OrderList.vue')
const Pay = () => import('@/views/Pay.vue')

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/products', component: ProductList },
  { path: '/product/:id', component: ProductDetail, props: true },
  { path: '/publish', component: ProductForm },
  { path: '/orders', component: OrderList },
  { path: '/pay/:id', component: Pay, props: true },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// 可选：路由守卫（需登录）
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && to.path !== '/register' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router