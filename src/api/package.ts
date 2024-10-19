import request from '~/utils/request'
/**
 *
 * 套餐管理
 *
 **/

//套餐分页查询
export const getPackagePage = (params: any) => {
    return request({
        url: '/api/packages/page',
        method: 'GET',
        params: params
    })
}

//套餐启售停售
export const enableOrDisablePackage = (params: any) => {
    return request({
        url: `/api/packages/status/${params.status}`,
        method: 'POST',
        params: {id: params.id}
    })
}

//删除套餐
export const deletePackage = (ids: string) => {//1,2,3
    return request({
        url: '/api/packages',
        method: 'DELETE',
        params: {ids: ids}
    })
}


  
// 修改数据接口
export const editPackage = (params: any) => {
    return request({
        url: '/api/packages',
        method: 'put',
        data: { ...params }
    })
}

// 新增数据接口
export const addPackage = (params: any) => {
    return request({
        url: '/api/packages',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情接口
export const queryPackageById = (id: string | (string | null)[]) => {
    return request({
        url: `/api/packages/${id}`,
        method: 'get'
    })
}
