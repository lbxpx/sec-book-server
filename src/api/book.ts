import request from '~/utils/request'
/**
 *
 * 商品管理
 *
 **/
// 查询列表接口
export const getProductPage = (params: any) => {
  return request({
    url: '/api/product/page',
    method: 'get',
    params
  })
}

// 删除接口
export const deleteBook = (ids: string) => {
  return request({
    url: '/api/product',
    method: 'delete',
    params: { ids }
  })
}

// 修改接口
export const editBook = (params: any) => {
  return request({
    url: '/api/product',
    method: 'put',
    data: { ...params }
  })
}

// 新增接口
export const addBook = (params: any) => {
  return request({
    url: '/api/product',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情
export const queryBookById = (id: string | (string | null)[]) => {
  return request({
    url: `/api/product/${id}`,
    method: 'get'
  })
}



// 查询产品列表的接口
export const queryBookList = () => {
  return request({
    url: '/api/product/list',
    method: 'get',
  })
}

// 文件down预览
export const commonDownload = (params: any) => {
  return request({
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    url: '/common/download',
    method: 'get',
    params
  })
}

// 起售停售
export const bookStatusByStatus = (params: any) => {
  return request({
    url: `/api/product/status/${params.status}`,
    method: 'post',
    params: { id: params.id }
  })
}

//书籍分类数据查询
export const CategoryList = () => {
  return request({
    url: `/api/category/list`,
    method: 'get',
  })
}
