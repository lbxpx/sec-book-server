import request from '~/utils/request'
/**
 *
 * 员工管理
 *
 **/
// 登录
export const login = (data: any) =>
  request({
    'url': '/api/employee/login',
    'method': 'post',
    data: data
  })

  // 退出
 export const userLogout = (params: any) =>
 request({
   'url': `/api/employee/logout`,
   'method': 'post',
   params
 })

 //注册
 export const register = (data: any) =>
  request({
    'url': '/api/employee/register',
    'method': 'post',
    data: data
  })

  //修改密码
  export const updatePassword = (data: any) =>
    request({
      'url': '/api/employee/update',
      'method': 'post',
      data: data
    })

  //获取验证码  
 export const getVerifyCode = (data: any) =>
  request({
    'url': '/api/employee/code',
    'method': 'post',
    data: data
  })
 
  //校验验证码
  export const checkVerifyCode = (data: any) =>
    request({
      'url': '/api/employee/checkcode',
      'method': 'post',
      data: data
  })

  //获取图像验证码
  export const getImageCode = () =>
    request({
      'url': '/api/employee/getValidateCode',
      'method': 'get',
  })

  //校验图形验证码
  export const testImageCode = (data: any) =>
    request({
      'url': '/api/employee/testValidateInfo',
      'method': 'post',
      data: data
  })

