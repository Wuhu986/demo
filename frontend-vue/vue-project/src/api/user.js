import request from '@/utils/request'

export const login = (data) => request.post('/user/handle', data)
export const getUserById = (id) => request.get(`/user/info/${id}`)
export const getBalance = () => request.get('/user/balance')
export const recharge = (amount) => request.post('/user/recharge', { amount })