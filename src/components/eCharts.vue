<template>  
    <div class="chart-wrapper">  
        <div ref="chartContainer" class="chart-container"></div>  
    </div>  
</template>   

<script setup lang="ts">  
import { ref, onMounted, watch } from 'vue';  
import * as echarts from 'echarts/core';  

// 引入需要的图表组件  
import { PieChart } from 'echarts/charts';  

// 引入必要的组件  
import {  
    TitleComponent,  
    TooltipComponent,  
    LegendComponent,  
    ToolboxComponent,  
    GridComponent,  
    DatasetComponent,  
    TransformComponent,  
} from 'echarts/components';  

// 标签自动布局，全局过渡动画  
import { LabelLayout, UniversalTransition } from 'echarts/features';  

// 引入 Canvas 渲染器  
import { CanvasRenderer } from 'echarts/renderers';  
import { getPackageSales } from '~/api/report';
import { ElMessage } from 'element-plus';

// 注册必要的组件  
echarts.use([  
    ToolboxComponent,  
    LegendComponent,  
    TitleComponent,  
    TooltipComponent,  
    GridComponent,  
    DatasetComponent,  
    TransformComponent,  
    LabelLayout,  
    UniversalTransition,  
    CanvasRenderer,  
    PieChart,  
]);  

const chartContainer = ref<HTMLDivElement | null>(null);  
const chartInstance = ref<echarts.ECharts | null>(null);  

// 图表数据  
const chartData = ref({  
    title: '套餐销量',  
    data: [],  // 初始为空，后续从后端填充  
});  

// 初始化图表  
const initChart = () => {  
    if (chartContainer.value) {  
        chartInstance.value = echarts.init(chartContainer.value);  
        updateChartOptions(chartData.value);  
    }  
};  

// 更新图表选项  
const updateChartOptions = (data: { title: string; data: any[] }) => { 
    if (chartInstance.value){
        const option = {  
        title: {  
            text: data.title,    
            left: 'center',  
        },  
        tooltip: {  
            trigger: 'item',  
        },  
        legend: {  
            orient: 'vertical',  
            left: 'left',  
        },  
        series: [  
            {  
                name: '销量',  
                type: 'pie',  
                radius: '50%',  
                data: data.data,  
                emphasis: {  
                    itemStyle: {  
                        shadowBlur: 10,  
                        shadowOffsetX: 0,  
                        shadowColor: 'rgba(0, 0, 0, 0.5)',  
                    },  
                },  
                itemStyle: {  
                    borderColor: '#fff',  
                    borderWidth: 2,  
                },  
            },  
        ],  
    };  
    chartInstance.value.setOption(option);
    }   
};  

// 组件挂载时初始化图表并获取数据  
onMounted(async () => {  
    initChart();  
    await fetchChartData();  // 获取数据  
});  

// 获取图表数据的异步函数  
const fetchChartData = async () => { 
    await getPackageSales()
    .then(res => {
        chartData.value.data = res.data.data;   // 假设后端返回的数据格式为{data: [ { value: x, name: string }, ... ] }  
        // 更新图表显示  
        updateChartOptions(chartData.value);  
    })
    .catch(error => {
      ElMessage.error("获取数据失败：" + error.message)
    }) 
};  

// 监听数据变化并更新图表 (如果需要)  
watch(chartData, (newData) => {  
    if (chartInstance.value) {  
        updateChartOptions(newData);  
    }  
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