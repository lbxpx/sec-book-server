import { defineStore } from 'pinia';  
import axios from 'axios';  

export const useCounterStore = defineStore('counter', {  
  state: () => ({  
    products: [], // 存储产品数据  
    loading: false, // 加载状态  
  }),  
  getters: {  
    getProducts: (state) => state.products,  
    isLoading: (state) => state.loading,  
  },  
  actions: {  
    async fetchProducts() {  
      this.loading = true; // 设置加载状态为 true  
      try {  
        const response = await axios.get('/api/products'); // 替换为实际的 API 地址  
        this.products = response.data; // 更新产品数据  
      } catch (error) {  
        console.error('获取产品数据失败:', error);  
      } finally {  
        this.loading = false; // 设置加载状态为 false  
      }  
    },  
    async addProduct(product: any) {  
      try {  
        await axios.post('/api/products', product); // 替换为实际的 API 地址  
        await this.fetchProducts(); // 添加后刷新数据  
      } catch (error) {  
        console.error('添加产品失败:', error);  
      }  
    },  
    async editProduct(id: any, product: any) {  
      try {  
        await axios.put(`/api/products/${id}`, product); // 替换为实际的 API 地址  
        await this.fetchProducts(); // 编辑后刷新数据  
      } catch (error) {  
        console.error('编辑产品失败:', error);  
      }  
    },  
    async deleteProduct(id: any) {  
      try {  
        await axios.delete(`/api/products/${id}`); // 替换为实际的 API 地址  
        await this.fetchProducts(); // 删除后刷新数据  
      } catch (error) {  
        console.error('删除产品失败:', error);  
      }  
    },  
  },  
});