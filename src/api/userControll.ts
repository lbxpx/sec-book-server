import request from '~/utils/request'
/**
 * 
 * 用户管理
 * 
 */

//用户分页查询
export const getUsersPage = (params: any) => {
    return request({
        url: '/api/users/page',
        method: 'GET',
        params: params
    })
}

//用户删除
export const deleteUserById = (ids: string) => {
    return request({
        url: `/api/users`,
        method: 'DELETE',
        params: {ids: ids}
    })
}

//账号启用停用
export const enableOrDisableUsers = (params: any) => {
    return request({
        url: `/api/users/status/${params.status}`,
        method: 'POST',
        params: {id: params.id}
    })
}

//获取用户数据接口
export const getUsersCount = () => {
    return request({
        url: '/api/users/count',
        method: 'GET',
    })
}