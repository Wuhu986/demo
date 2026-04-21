import request from '@/utils/request'

export const purchase = (productId) => request.post('/order/purchase', { productId })
export const getMyOrders = () => request.get('/order/my')