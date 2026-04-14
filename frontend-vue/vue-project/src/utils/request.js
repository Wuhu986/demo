import axios from 'axios'
import router from '@/router'
import { ElMessage } from 'element-plus'

const service = axios.create({
    baseURL: '', // 后端地址
    timeout: 5000,
})

service.interceptors.response.use(
    (res) => {
        console.log(res)
        // 处理业务状态码
        if (res.data.code === 401) {
            ElMessage.error(res.data.msg || '请先登录')
            router.push('/login')
        }
        return res
    },
    err => {
        // 如果是 HTTP 错误码（例如 500、404）
        console.log("err")
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