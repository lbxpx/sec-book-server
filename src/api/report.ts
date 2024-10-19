import request from '~/utils/request'
/**
 * 获取报告数据
 */
//获取套餐销量数据接口
export const getPackageSales = () => {
    return request({
        url: '/api/reports/package',
        method: 'GET',
    })
}

//获取销售流水数据接口
export const getSalesAmount = () => {
    return request({
        url: '/api/reports/amount',
        method: 'GET',
    })
}