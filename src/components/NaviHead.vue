<template>  
  <el-tabs  
    v-model="editableTabsValue"  
    type="card"  
    class="demo-tabs"  
    @tab-remove="tabRemove"  
    @tab-change="tabChange"  
  >  
    <el-tab-pane  
      v-for="item in editableTabs"  
      :closable="item.close"  
      :key="item.name"  
      :label="item.title"  
      :name="item.name"  
    />  
  </el-tabs>  
</template>  

<script setup lang="ts">  
import { onMounted, watch } from 'vue';  
import { useRoute, useRouter } from "vue-router";  
import { appStore } from "~/store/appStore";  
import { storeToRefs } from "pinia";  
import { TabPaneName } from 'element-plus';  
const store = appStore();
const { editableTabsValue, editableTabs } = storeToRefs(store);  
const route = useRoute();  
const router = useRouter();  
// 组件挂载时自动添加 Home Tab  
onMounted(() => {  
  // 强制加载首页，如果首页没有在 editableTabs 中  
    router.push({name: editableTabsValue.value}); // 跳转到 Home 页面  
}); 

const tabRemove = (targetName: TabPaneName) => {  
  // 调用 Pinia 动作，移除 Tab  
  store.removeTab(targetName);  
  router.push({name: editableTabsValue.value});
};


// 添加 Tab 的方法  
const tabAdd = (route: any) => {  
  const index = editableTabs.value.findIndex(item => item.name === route.name);  
  if (index === -1) {  
    editableTabs.value.push({  
      title: route.meta.title,  
      name: route.name,  
      close: true,  
    });  
  }  
  // 新添加的Tab标签页高亮  
  editableTabsValue.value = route.name;  
};  

// 路由变化时自动添加 Tab  
watch(() => route.path, () => {  
  tabAdd(route);  
}, { deep: true });  

// Tab 变化时更新路由  
const tabChange = (name: any) => {  
  router.push({ name: name });  
};  
</script>