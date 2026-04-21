import axios from 'axios'
import router from '@/router'
import { ElMessage } from 'element-plus'

const service = axios.create({
    baseURL: '',
    timeout: 5000,
})

service.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) config.headers.Authorization = `Bearer ${token}`
    return config
})

service.interceptors.response.use(
    (res) => {
        if (res.data.code === 401) {
            ElMessage.error(res.data.msg || '请先登录')
            localStorage.clear()
            router.push('/login')
        }
        return res
    },
    err => {
        const { response } = err
        if (response) {
            ElMessage.error(response.data?.msg || '请求错误')
        } else {
            ElMessage.error('无法连接服务器')
        }
        return Promise.reject(err)
    }
)

export default service