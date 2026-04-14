import request from '@/utils/request'

export const login = (data) => request.post('/user/handle', data)
// export const register = (data) => request.post('/user/register', data)
export const getUserById = (id) => request.get(`/user/${id}`)
export const getAllUsers = () => request.get('/user/all')
export const updateUser = (data) => request.put('/user/update', data)