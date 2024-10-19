// store/appStore.ts  
import { TabPaneName } from 'element-plus';
import { defineStore } from 'pinia';  

interface EditableTab {  
  title: string;  
  name: string;  
  close: boolean; // 用于标记是否可关闭的属性  
}  

interface AppState {  
  editableTabsValue: string; // 当前活动标签的名称  
  editableTabs: EditableTab[]; // 标签数组  
}  

// 定义 store  
export const appStore = defineStore( 'appStore',{
  state: (): AppState => ({  
          //定义一个editableTabs数组,里边放初始化元素
          editableTabs:[{
            title: '主页',
            name: 'home',
            close: false,
          }],
          //当前活动标签,默认显示name:'home'的元素
           editableTabsValue:"home" 
  }),  

  persist:  true, // 启用持久化  


  actions: {  
    addTab(newTab: EditableTab) {  
      // 检查标签是否已经存在  
      const exists = this.editableTabs.some(tab => tab.name === newTab.name);  
      if (!exists) {  
        this.editableTabs.push(newTab);  
      }  
      // 设置当前活动的标签  
      this.editableTabsValue = newTab.name;  
    },  
    
    removeTab(name: TabPaneName) {  
      // 如果被移除的是当前激活的标签，重新选中其他标签  
      if (this.editableTabsValue === name) {  
        const currentIndex = this.editableTabs.findIndex(tab => tab.name === name);  
    
        // 选择下一个标签（如果有的话），否则选择前一个标签  
        let nextIndex = currentIndex === this.editableTabs.length-1 ? currentIndex - 1 : currentIndex+1; // 如果是最后一个，则选择倒数第二个  
    
        this.editableTabsValue = this.editableTabs[nextIndex]?.name; // 更新当前激活标签    
      }
      // 过滤掉被移除的标签  
      this.editableTabs = this.editableTabs.filter(tab => tab.name !== name);  
    },

    setActiveTab(name: string) {  
      this.editableTabsValue = name; // 修改活动标签  
    }, 
    
    resetTabs() {  
      // 清空当前标签并添加首页 Tab  
      this.editableTabs = [{  
        title: '主页',  
        name: 'home',  
        close: false,  
      }];  
      this.editableTabsValue = 'home'; // 确保首页 Tab 是当前选中 Tab  
    } 
  },  

});
