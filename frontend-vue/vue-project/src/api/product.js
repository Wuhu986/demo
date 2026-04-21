import request from '@/utils/request'

export const getProductList = (params) => request.get('/product/list', { params })
export const searchProducts = (keyword, params) =>
    request.get('/product/search', { params: { keyword, ...params } })
export const getProductById = (id) => request.get(`/product/${id}`)
export const createProduct = (data) => request.post('/product/publish', data)
export const markProductSold = (id) => request.put(`/product/${id}/sold`)