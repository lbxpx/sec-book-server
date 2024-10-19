import request from '~/utils/request'
/**
 * 用户数据管理
 */


//获取当前用户信息
export const getUserData = (id:any) =>{
    return request({
        url: `/api/userData/${id}`,
        method: 'get'
    })
}

//修改用户信息
export const editUserData = (params: any) => {
    return request({
        url: '/api/userData',
        method: 'put',
        data: { ...params }
    })
}