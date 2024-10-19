<template>
  <h3>欢迎来到校园二手书籍交易平台</h3>
  <div class="charts-container">
    <eCharts></eCharts>
    <BarChart/>  
    <TreeChart/>  
  </div>

</template>
<script setup lang="ts">
import { onMounted } from 'vue'
import { appStore } from "~/store/appStore";
import { storeToRefs } from "pinia";
import eCharts from '~/components/eCharts.vue'
import BarChart from '~/components/BarChart.vue';
import TreeChart from '~/components/TreeChart.vue';
let { editableTabsValue, editableTabs } = storeToRefs(appStore());

const homePage = 'home';
onMounted(() => {
  // console.log(activeTabName.value)

  if (editableTabsValue.value == homePage) {
      // console.log("执行了")
      editableTabsValue.value = homePage;
      //过滤,只留下首页
      editableTabs.value = editableTabs.value.filter((tab: { name: string; }) => tab.name == homePage)
  }
})

</script>
<style lang="scss" scoped>  
.charts-container {  
  display: flex; /* 横向排列 */  
  justify-content: space-between; /* 根据空间均匀分配 */  
  gap: 20px; /* 间隔 */  
}  

.chart-wrapper {  
  flex: 1; /* 固定宽度 */  
  max-width: 400px; /* 可选：限制最大宽度 */  
}  
</style>  