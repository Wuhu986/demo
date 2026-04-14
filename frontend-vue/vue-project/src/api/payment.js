import request from '@/utils/request'

export const createPayment = (data) => request.post('/payment/create', data)
export const getPaymentStatus = (id) => request.get(`/payment/status/${id}`)