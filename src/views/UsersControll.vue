<template>  
    <div class="user-management">  
      <div class="filter-container">
        <label style="margin-right: 10px">用户名：</label>   
        <el-input v-model="input" placeholder="搜索用户姓名" style="width: 300px; margin-bottom: 20px;" 
          clearable  
          @clear="init"  
          @keyup.enter.native="initFun"/>  
        <el-select v-model="selectedRoleA" placeholder="筛选角色" style="width: 200px; margin-bottom: 20px;">  
          <el-option label="所有" value="" />  
          <el-option label="管理员" value="管理员" />  
          <el-option label="普通用户" value="普通用户" />  
        </el-select>  
       
      <label style="margin-left: 20px; margin-top: 5px;">用户状态：</label>  
        <el-select  
          v-model="selectedRoleB"  
          style="width: 200px; margin-bottom: 20px;"  
          placeholder="请选择用户状态"     
        >  
          <!-- <el-option  
            v-for="item in userStatusList"  
            :key="item.value"  
            :label="item.label"  
            :value="item.value"  
          />  -->
          <el-option label="所有" value="" />  
          <el-option label="启用" value="1" />  
          <el-option label="停用" value="0" />  
        </el-select>
        <!-- 查询按钮 -->  
        <el-button 
          style="margin-right: 50px"   
          class="normal-btn continue"  
          @click="init(true)"  
        >  
          查询  
        </el-button> 
      </div> 
        <el-table :data="filteredUsers" border:true>  
          <el-table-column prop="userId" label="用户 ID" width="180" />  
          <el-table-column prop="userName" label="用户名" width="200" />  
          <el-table-column prop="email" label="邮箱" width="250" />  
          <el-table-column prop="role" label="角色" width="160" />  
          <el-table-column prop="createdAt" label="创建时间" width="200" />
          <!-- 账号状态列 -->  
          <el-table-column label="账号状态" width="150" class="tableColumn-status">  
          <template #default="scope">  
            <div :class="{ 'stop-use': String(scope.row.status) === '0' }">  
              {{ String(scope.row.status) === '0' ? '停用' : '启用' }}  
            </div>  
          </template>  
          </el-table-column>  
          <el-table-column label="操作" width="210">  
            <template #default="scope">  
              <!-- <div style="display: flex; gap: 10px;">  -->
                <!-- <el-button type="primary" size="small">查看</el-button>   -->
                <el-button type="danger" size="small" @click="handleDelete(scope.row.userId)">删除</el-button>  
                <el-button  
                  type="primary"  
                  size="small"  
                  :class="{ blueBug: scope.row.status == '0', delBut: scope.row.status != '0' }"  
                  @click="statusHandle(scope.row)"  
                >  
                  {{ scope.row.status == '0' ? '启用' : '停用' }}  
                </el-button>  
              <!-- </div>   -->
            </template>  
          </el-table-column> 
      </el-table>  
  
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
  import {ElMessage} from 'element-plus';
import { ElMessageBox } from 'element-plus';
import { ref, computed, onMounted } from 'vue';  
import { deleteUserById, enableOrDisableUsers, getUsersPage } from '~/api/userControll';
  
  // 定义用户接口  
  interface User {  
    userId: string;  
    userName: string;  
    email: string;  
    role: string;  
    createdAt: string;
    status: string;
  }  
  
  // 模拟用户数据  
  const users = ref<User[]>([]);  
  let userStatus = ref('');  
  const userStatusList = ref([  
      { label: '全部', value: '' },  
      { label: '启用', value: '1' },  
      { label: '停用', value: '0' }  
  ]); 
  let tableData = ref<User[]>([]);
  const input = ref('');  
  const selectedRoleA = ref('');
  const selectedRoleB = ref('');   
  const pageSize = ref(10); // 每页显示的用户数  
  let total = ref(0);  
  const page = ref(1);  
 // 初始化数据  
const init = async (isSearch?: Boolean) =>{
  isSearch = isSearch
  await getUsersPage({
    username: input.value, 
    status: userStatus.value || null,  
    page: page.value,  
    pageSize: pageSize.value  
  })
    .then(res => {
      if(res.data.code === 1) {
        tableData.value = res.data && res.data.data && res.data.data.records.map((record: { id: any; userName: any; email: any; role: any; createdAt: any; status: any; }) => {  
          return {  
            userId: record.id, // 映射 id 为 userId  
            userName: record.userName,  
            email: record.email,  
            role: record.role === "admin" ? "管理员" : "普通用户",  
            createdAt: record.createdAt,  
            status: record.status,  
          };  
        });  
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

const handleDelete = ( id: any) => {
  ElMessageBox.confirm('确认删除该用户, 是否继续?', '确定删除', {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(()=>{
      deleteUserById(id)
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

const statusHandle = (row: any) => {
    let params: any = {}
    params.id = row.userId
    params.status = row.status === '0' ? '1' : '0'; // 如果当前状态是停用，那么更改为启用    
    userStatus=params
    ElMessageBox.confirm('确认更改该账户状态?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(()=>{
      enableOrDisableUsers(userStatus)
        .then(res => {
          if(res && res.data && res .data.code === 1) {
            ElMessage.success('账户状态更改成功！')
            init()
          } else {
            ElMessage.error(res.data.msg)
          }
        })
        .catch(error => {
          ElMessage.error('请求出错了：' + error.message)
        })
    })  
} 

// 计算过滤后的用户  
const filteredUsers = computed(() => {  
    return tableData.value.filter(user => {  
      // const matchesQuery = user.userName.includes(searchQuery.value) ||   
      //                      user.email.includes(searchQuery.value);  
      const matchesRole = (selectedRoleA.value ? user.role === selectedRoleA.value : true) &&  
                     (selectedRoleB.value ? user.status === selectedRoleB.value : true);
      return matchesRole;  
    });  
  });  

  onMounted( async () => {
  await init()
})
  </script>  
  
<style scoped lang="scss">    
  .user-management {  
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

  /* 表格行的状态 */  
  .tableColumn-status {   
    text-align: center; /* 水平居中对齐 */  
    display: flex;      /* 使用 flexbox */  
    justify-content: center; /* 在交叉轴上居中对齐 */  
    align-items: center; /* 垂直居中对齐 */  
    height: 100%; /* 如果需要，这可以帮助进行垂直居中 */   
  }  

  .stop-use {  
    /* background-color: #f56c6c; 停用状态的背景色   */
    color: #f56c6c;  
  }  

  .blueBug {  
    color: white; /* 启用状态按钮的颜色 */  
  }  

  .delBut {  
    color: #f56c6c; /* 停用状态按钮的颜色 */  
  }  

  .pageList {  
    margin-top: 20px;  
  }  
</style>