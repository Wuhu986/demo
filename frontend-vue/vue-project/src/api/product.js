import request from '@/utils/request'

export const getProductList = (params) => request.get('/product/search', { params })
export const getProductById = (id) => request.get(`/product/${id}`)
export const createProduct = (data) => request.post('/product/create', data)
export const deleteProduct = (id) => request.delete(`/product/delete/${id}`)
export const getUserProducts = (userId) => request.get(`/product/user/${userId}`)