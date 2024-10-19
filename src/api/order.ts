import request from '~/utils/request'
/**
 * 
 * 订单管理
 * 
 */

//订单分页查询
export const getOrdersPage = (params: any) => {
    return request({
        url: '/api/orders/page',
        method: 'GET',
        params: params
    })
}


// 新增订单接口
export const addPackage = (params: any) => {
    return request({
        url: '/api/orders',
        method: 'post',
        data: { ...params }
    })
}

//查看订单
export const getOrderById = (id: string | (string | null)[]) => {
    return request({
      url: `/api/orders/${id}`,
      method: 'get'
    })
  }

//订单删除
export const deleteOrdersById = (ids: string) => {
    return request({
        url: `/api/orders`,
        method: 'DELETE',
        params: {ids: ids}
    })
}

//查询订单总额
export const getOrdersCount = () => {
    return request({
        url: '/api/orders/count',
        method: 'GET',
    })
}

