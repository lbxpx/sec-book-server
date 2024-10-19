// userStore.ts  
import { defineStore } from 'pinia';  
import { login, userLogout } from '~/api/employee';  
import { getToken, setToken, removeToken, getStoreId, setStoreId, getUserInfo, removeUserInfo } from '~/utils/cookies';  
import Cookies from 'js-cookie';  
import { Message } from '@element-plus/icons-vue'
import { appStore } from './appStore';
import router from '~/router';
const aStore = appStore()

export interface IUserState {  
  token: string;  
  name: string;  
  avatar: string;  
  storeId: string;  
  introduction: string;  
  userInfo: any;  
  roles: string[];  
  username: string; 
  userId: any; // 添加这一行存储用户 ID 
  image: any;//用户头像
}  

export const useUserStore = defineStore('user', {  
  state: (): IUserState => ({  
    token: getToken() || '',  
    name: '',  
    avatar: '',  
    storeId: getStoreId() || '',  
    introduction: '',  
    userInfo: {},  
    roles: [],  
    username: Cookies.get('username') || '',  
    userId: '', // 初始化用户 ID
    image: '',
  }),  
  actions: {  
    // 登录  
    async Login(userInfo: { username: string; password: string }) { 
      let { username, password } = userInfo;  
      username = username.trim();  
      const { data } = await login({ username, password });  

      if (String(data.code) === '1') {  
        // 设置store中属性的值  
        this.username = username;  
        this.token = data.data.token;
        this.userId = data.data.id;
        this.image = data.data.image;   
        this.userInfo = { ...data.data };  

        // 保存到Cookie中  
        Cookies.set('username', username);  
        Cookies.set('user_info', JSON.stringify(data.data));  
        Cookies.set('token', data.data.token);
        localStorage.removeItem('YOUR_PINIA_STORE_NAME'); // 替换为实际的 store 名称  
        aStore.resetTabs(); // 重置标签  
        router.push({ name: 'home' }); // 导航到首页    
        return data;  
      } else {  
        // 假设 Message.error 是一个可以直接调用的函数  
        return Message.error(data.msg);  
      }  
    },  
    // 重置 token  
    ResetToken() {  
      removeToken();  
      this.token = '';  
      this.roles = [];  
    },  
    // 切换商店  
    async changeStore(data: any) {  
      this.storeId = data.data;  
      this.token = data.authorization;  
      setStoreId(data.data);  
      setToken(data.authorization);  
    },  
    // 获取用户信息  
    async GetUserInfo() {  
      if (this.token === '') {  
        throw new Error('GetUserInfo: token is undefined!');  
      }  

      const data = JSON.parse(<string>getUserInfo()); // 假设返回的结构是这个  
      if (!data) {  
        throw new Error('Verification failed, please Login again.');  
      }  

      const { roles, name, avatar, introduction, applicant, storeManagerName } = data;  

      // roles 必须是非空数组  
      if (!roles || roles.length <= 0) {  
        throw new Error('GetUserInfo: roles must be a non-null array!');  
      }  

      this.roles = roles;  
      this.userInfo = data;  
      this.name = name || applicant || storeManagerName;  
      this.avatar = avatar;  
      this.introduction = introduction;  
    },  
    // 登出  
    async LogOut() {  
      const { data } = await userLogout({});  
      removeToken();  
      this.token = '';  
      this.roles = [];  
      Cookies.remove('username');  
      Cookies.remove('user_info');  
      removeUserInfo();
      return data;  
    }  
  },  
});