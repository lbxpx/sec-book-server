<template>  
    <div class="chart-wrapper">  
      <div ref="chartContainer" class="chart-container"></div>  
    </div>  
  </template>  
  
  <script setup lang="ts">  
  import { ref, onMounted } from 'vue';  
  import * as echarts from 'echarts/core';  
  import { LineChart } from 'echarts/charts';  
  import { TitleComponent, TooltipComponent, GridComponent } from 'echarts/components';  
  import { CanvasRenderer } from 'echarts/renderers';  
import { getUsersCount } from '~/api/userControll';
import { error } from 'console';
import { ElMessage } from 'element-plus';
  

  echarts.use([TitleComponent, TooltipComponent, GridComponent, LineChart, CanvasRenderer]);  
  
  const chartContainer = ref<HTMLDivElement | null>(null);  
  const chartInstance = ref<echarts.ECharts | null>(null);  
  
  // 模拟用户量数据  
  const chartData = {  
    dates: ['2023-01', '2023-02', '2023-03', '2023-04', '2023-05', '2023-06', '2023-07'],  
    userCounts: [100, 150, 200, 300, 400, 500, 700]  
  };  
  
  // 更新图表的函数  
  const updateChart = () => {  
    if (chartInstance.value) {  
      const option = {  
        title: {  
          text: '用户量走势',  
        },  
        tooltip: {  
          trigger: 'axis',  
        },  
        xAxis: {  
          type: 'category',  
          data: chartData.dates,  
        },  
        yAxis: {  
          type: 'value',  
        },  
        series: [  
          {  
            name: '用户量',  
            type: 'line',  
            data: chartData.userCounts,  
            smooth: true,  
            itemStyle: { color: '#42A5F5' },  
          },  
        ],  
      };  
      chartInstance.value.setOption(option);  
    }  
  };
  
  const fetchData = async () => {
    await getUsersCount()
    .then(res => {
      chartData.dates = res.data.data.dates;  
      chartData.userCounts = res.data.data.counts;
      updateChart();  
    })
    .catch(error => {
      ElMessage.error("获取数据失败：" + error.message)
    })
    
};  
  
  // 组件挂载时初始化图表  
  onMounted(() => {  
    chartInstance.value = echarts.init(chartContainer.value!);
    fetchData(); // 获取数据     
  });  
  </script>  
  
  <style lang="scss" scoped>  
  .chart-wrapper {  
  display: flex;  
  justify-content: center;  
  align-items: center;  
  padding: 20px;  
  border: 1px solid #ddd;  
  border-radius: 10px;  
  background-color: #f9f9f9;  
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);  
  margin-bottom: 20px; /* 用于上下图表之间的间隔 */  
  }  

  .chart-container {  
  width: 100%;  
  height: 400px; /* 高度可以根据需要调整 */  
  }  
  </style>  