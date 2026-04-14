import request from '@/utils/request'

export const createOrder = (data) => request.post('/order/create', data)
export const getOrderList = () => request.get('/order/list')
export const getOrderById = (id) => request.get(`/order/${id}`)
export const getUserOrders = (userId) => request.get(`/order/user/${userId}`)