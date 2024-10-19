<template>  
  <div class="order-management">  
    <div class="filter-container">  
      <el-input v-model="input" placeholder="搜索订单ID" style="width: 300px; margin-bottom: 20px;" 
      clearable  
      @clear="init"  
      @keyup.enter.native="initFun"
      />
      <el-input v-model="uinput" placeholder="搜索用户" style="width: 300px; margin-bottom: 20px;" 
      clearable  
      @clear="init"  
      @keyup.enter.native="initFun"
      />  
      <!-- 订单状态选择框 -->  
      <label style="margin-right: 10px; margin-left: 20px">订单状态：</label>  
        <el-select   
          v-model="selectedStatus"  
          style="width: 14%"  
          placeholder="请选择"  
          clearable  
          @clear="init"  
        >  
          <el-option   
            v-for="item in orderStatus"  
            :key="item.value"  
            :label="item.label"  
            :value="item.value"   
          />  
        </el-select>  

        <!-- 查询按钮 -->  
        <el-button 
          style="margin-right: 5px"   
          class="normal-btn continue"  
          @click="init(true)"  
        >  
          查询  
        </el-button> 
      
      <div class="tableLab">  
          <!-- 批量删除链接 -->  
          <span class="del"  
                @click="handleDelete('批量', null)"  
          >   
            批量删除  
          </span> 
      </div> 
    </div>  

    <el-table :data="tableData" border:true @selection-change="handleSelectionChange">
      <!-- 选择列 -->  
      <el-table-column type="selection" width="25" />  
      <el-table-column prop="orderId" label="订单 ID" width="350" />  
      <el-table-column prop="buyer" label="买家" width="160" />  
      <el-table-column prop="seller" label="卖家" width="160" />  
      <el-table-column label="订单状态" width="160">  
          <template #default="scope">  
            <div class="tableColumn-status" :class="{ 'stop-use': String(scope.row.status) !== '0' }">  
              {{ String(scope.row.status) === '0' ? '已完成' :  String(scope.row.status) === '1' ? '待发货' : '待收货'}}  
            </div>  
          </template>  
        </el-table-column> 
      <el-table-column label="售价" width="150">  
          <template #default="scope">  
            <span style="margin-right: 10px">￥{{ (scope.row.price).toFixed(2) }}</span>  
          </template>  
      </el-table-column> 

      <el-table-column prop="createdAt" label="创建时间" width="200" />  
      <el-table-column label="操作" width="180">  
        <template v-slot="scope">  
          <el-button type="primary" size="small" @click="handleView(scope.row.orderId)">查看</el-button>  
          <el-button type="danger" size="small" @click="handleDelete('单删',scope.row.orderId)">删除</el-button>  
        </template>  
      </el-table-column>  
    </el-table>  

    <!-- 查询弹窗 -->
    <el-dialog v-model="dialogVisible" title="订单详情" width="400px">  
      <div>  
        <h3>订单信息</h3> 
        <p><strong>订单号:</strong> {{ orderDetail.orderId }}</p> 
        <p><strong>订单详情:</strong>
          <div v-for="item in orderDetail.orderDetails" :key="item.id" class="order-item">
            <span>{{ item.name }} x {{ item.number }}</span>
            <span class="amount">-------- ￥{{ item.amount }}</span>
          </div>
        </p> 
        <p><strong>总价:</strong> ￥{{ orderDetail.price }}</p>     
        <p><strong>买家:</strong> {{ orderDetail.buyer }}</p>
        <p><strong>卖家:</strong> {{ orderDetail.seller }}</p>   
        <p><strong>创建时间:</strong> {{ orderDetail.createdAt }}</p>  
      </div>  
      <template #footer>  
        <el-button @click="dialogVisible = false">关闭</el-button>  
      </template>  
    </el-dialog>    

    <el-pagination  
        v-if="total > 0"  
        class="pageList"  
        @size-change="handleSizeChange"  
        @current-change="handleCurrentChange"  
        :current-page="page"  
        :page-size="pageSize"  
        :total="total"  
        layout="total, sizes, prev, pager, next, jumper"  
      /> 
  </div>  
</template>  

<script setup lang="ts">  
import { ElMessage } from 'element-plus';
import { ElMessageBox } from 'element-plus';
import { ref, computed, reactive, onMounted, Ref } from 'vue';  
import { deleteOrdersById, getOrderById, getOrdersPage } from '~/api/order';

const dialogVisible = ref(false);

let checkList: Ref<string[]> = ref([]);

// 定义订单接口  
interface Order {  
  orderId: string;  
  buyer: string;  
  seller: string;  
  status: string;  
  createdAt: string;
  price: string;  
}  

// 模拟订单数据  
let tableData = ref<Order[]>([]);  

const searchQuery = ref('');  
const selectedStatus = ref('');  

const input = ref('');
const uinput = ref('');    
const pageSize = ref(10); 
let total = ref(0);  
const page = ref(1);  
const orderDetail = ref();

const orderStatus = ref([
  {
    value: "0",
    label: '已完成'
  },
  {
    value: "1",
    label: '待发货'
  },
  {
    value: "2",
    label: '待收货'
  }
]);

onMounted( async () => {
  await init()
})


// 初始化数据  
const init = async (isSearch?: Boolean) =>{
  isSearch = isSearch
  await getOrdersPage({
    orderId: input.value || null,
    name: uinput.value || null,  
    page: page.value,  
    pageSize: pageSize.value,
    status: selectedStatus.value || null  
  })
    .then(res => {
      if(res.data.code === 1) {
        tableData.value = res.data && res.data.data && res.data.data.records
        total.value = res.data.data.total
      }
    })
    .catch(error => {
      ElMessage.error('请求出错了：' + error.message)
    })
}

const initFun = () => {
      page.value = 1 
      init()  
}

const handleSizeChange = (val: any) =>{
  pageSize.value = val
  init()
}

const handleCurrentChange = (val: any) =>{
  page.value = val
  init()
}

// // 计算过滤后的订单  
// const filteredOrders = computed(() => {  
//   return tableData.value.filter(order => {  
//     const matchesQuery = order.buyer.includes(searchQuery.value) ||  
//                          order.seller.includes(searchQuery.value);  
//     const matchesStatus = selectedStatus.value ? order.status === selectedStatus.value : true;  
//     return matchesQuery && matchesStatus;  
//   });  
// });  
 

// 处理查看订单  
const handleView = (id: any) => {  
  alert(`查看订单: ${id}`);
  getOrderById(id)
  .then(res=>{
      if(res.data.code === 1) {
        orderDetail.value = res.data && res.data.data
        dialogVisible.value =true
      }else {
        ElMessage.error(res.data.msg)
      }
  })
  .catch(error=>{
    ElMessage("请求失败：" + error.message)
  })
};  

// 全部操作
const handleSelectionChange = (val :any) =>{
  checkList.value = val.map((item: any) => item.orderId); // 直接映射  
}

//删除操作
const handleDelete = ( type: string, id: any) => {
  if(type === '批量' && id === null) {
    if(checkList.value.length === 0) {
      return ElMessage.error('请选择删除对象')
    }
  }
  ElMessageBox.confirm('确认删除该订单, 是否继续?', '确定删除', {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(()=>{
      deleteOrdersById(type === '批量' ? checkList.value.join(',') : id)
        .then(res => {
          if(res && res.data && res.data.code === 1) {
            ElMessage.success('删除成功！')
            init()
          }else {
            ElMessage.error(res.data.msg)
          }
        })
        .catch(error => {
          ElMessage.error('请求出错了：' + error.message)
        })
  })
}

</script>  

<style scoped lang="scss">  
.order-management {  
  padding: 20px;  
  background-color: #f9f9f9;  
  border-radius: 8px;  
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);  
}  

.filter-container {  
  display: flex;  
  gap: 20px; /* 增加筛选条件之间的间距 */  
  margin-bottom: 20px;
  .normal-btn {  
    background: #333333;  
    color: white;  
    margin-left: 20px;  
  }
}  
.tableLab {  
  display: inline-block;
  margin-top: 5px;  
  .del {
    cursor: pointer;  
    display: inline-block;  
    font-size: 14px;  
    padding: 0 20px;  
    color: #999; 
  } 
  .del:hover {  
  color: blue; /* 鼠标悬停时文本颜色 */  
}  
}    
.order-item {  
  display: flex;  
  justify-content: space-between;   
  margin-bottom: 10px;   
}  

.amount {
  margin-right: 40px;  
  text-align: right;   
} 
.tableColumn-status {  
      /* 定义状态的样式   */
      &.stop-use {  
        color: red;  
      }  
    } 
</style>