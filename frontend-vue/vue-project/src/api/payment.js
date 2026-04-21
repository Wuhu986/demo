import request from '@/utils/request'

export const createPayment = (data) => request.post('/payment/record', data)
export const getPaymentStatus = (id) => request.get(`/payment/status/${id}`)