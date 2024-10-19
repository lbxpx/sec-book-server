<template>  
  <div class="dashboard-container">  
    <div class="container">  
      <div class="tableBar">  
        <label style="margin-right: 10px">套餐名称：</label>  
        <el-input  
          v-model="input"  
          placeholder="请填写套餐名称"  
          style="width: 14%"  
          clearable  
          @clear="init"  
          @keyup.enter.native="initFun"  
        />
        <!-- 套餐分类选择框 -->  
        <label style="margin-right: 10px; margin-left: 20px">套餐分类：</label>  
        <el-select   
          v-model="categoryId"  
          style="width: 14%"  
          placeholder="请选择"  
          clearable  
          @clear="init"  
        >  
        <el-option   
          v-for="item in bookCategoryList"  
          :key="item.value"  
          :label="item.label"  
          :value="item.value"   
        />  
        </el-select>
          <!--套餐状态选择框  -->
        <label style="margin-right: 10px; margin-left: 20px">套餐状态：</label>  
        <el-select  
          v-model="packageStatus"  
          style="width: 14%"  
          placeholder="请选择"  
          clearable  
          @clear="init"  
        >  
          <el-option  
            v-for="item in packageStatusList"  
            :key="item.value"  
            :label="item.label"  
            :value="item.value"  
          />  
        </el-select>  
        <!-- 查询按钮 -->  
        <el-button  
          class="normal-btn continue"  
          @click="init(true)"  
        >  
          查询  
        </el-button>  
        <div class="tableLab">  
          <!-- 批量删除链接 -->  
          <span class="delBut non"  
                @click="deleteHandle('批量', selectedPackages)"  
          >  
            批量删除  
          </span>  
          <!-- 新建套餐按钮 -->  
          <el-button  
            type="primary"  
            style="margin-left: 15px"  
            @click="addPackageType('')"  
          >  
            + 新建套餐  
          </el-button> 
           <!--弹框  -->
          <el-dialog v-model="dialogFormVisible" title="套餐管理" width="500">  
            <el-form :model="form">  
              <el-form-item label="套餐名称" :label-width="formLabelWidth">  
                <el-input v-model="form.packageName" autocomplete="off" />  
              </el-form-item>
              
              <el-form-item label="套餐封面" prop="image" :label-width="formLabelWidth">  
                      <upload-image 
                          @imageChange="imageChange" style="margin-right: 10px;">
                      </upload-image> 
                      <span style=" color:grey">图片大小不超过2M<br>仅能上传 PNG JPEG JPG类型图片<br>建议上传200*200或300*300尺寸的图片 </span>
                    </el-form-item>
        
              <el-form-item label="分类选择" :label-width="formLabelWidth">  
                <el-select  
                  v-model="form.categoryId"    
                  style="width: 100%;"  
                  placeholder="请选择分类"
                  @change="$forceUpdate()"  
                >  
                  <el-option  
                    v-for="item in bookCategoryList"  
                    :key="item.value"  
                    :label="item.label"  
                    :value="item.value"  
                  />  
                </el-select>  
              </el-form-item>

              <el-form-item label="书籍选择" :label-width="formLabelWidth">  
                <el-select  
                    v-model="form.bookIds"  
                    multiple  
                    filterable 
                    clearable  
                    style="width: 100%;"  
                    placeholder="请选择书籍" 
                    @change="$forceUpdate()"   
                  >  
                  <el-option  
                    v-for="item in bookList"  
                    :key="item.value"  
                    :label="item.label"  
                    :value="item.value"  
                  />  
                </el-select>   
              </el-form-item>  
              <el-form-item label="套餐价格￥" :label-width="formLabelWidth">  
                <el-input v-model="form.price" autocomplete="off" />  
              </el-form-item>  
              <el-form-item label="套餐状态" :label-width="formLabelWidth">  
                <el-select v-model="form.status" style="width: 100%;">  
                  <el-option v-for="item in packageStatusList" :key="item.value" :label="item.label" :value="item.value" />  
                </el-select>  
              </el-form-item>  
            </el-form>  
            <template #footer #default="scope">  
              <div class="dialog-footer">  
                <el-button @click="cancel">取消</el-button>  
                <el-button type="primary" @click="submitPackage()">确定</el-button>  
              </div>  
            </template>  
          </el-dialog>  
        </div>  
      </div>  

      <el-table  
        v-if="tableData.length"  
        :data="tableData"  
        stripe  
        class="tableBox"  
        @selection-change="handleSelectionChange"  
      >  
        <!-- 选择列 -->  
        <el-table-column type="selection" width="25" />  
        <!-- 套餐名称列 -->  
        <el-table-column prop="packageName" label="套餐名称" />  
        <!-- 套餐分类列 -->  
        <el-table-column prop="categoryName" label="套餐分类"/>  
        <!-- 图片列 -->  
        <el-table-column prop="image" label="图片">  
          <template #default="{ row }">  
            <el-image   
              style="width: 80px; height: 40px; border: none; cursor: pointer"  
              :src="row.image"  
            >  
              <template #error>  
                <img   
                :src="require('../assets/kon.png')" 
                  style="width: auto; height: 40px; border: none"   
                >  
              </template>  
            </el-image>  
          </template>  
        </el-table-column>   
        <!-- 套餐价格列 -->  
        <el-table-column label="套餐价格">  
          <template #default="scope">  
            <span style="margin-right: 10px">￥{{ (scope.row.price).toFixed(2) }}</span>  
          </template>  
        </el-table-column>  
        <!-- 套餐状态列 -->  
        <el-table-column label="套餐状态">  
          <template #default="scope">  
            <div class="tableColumn-status" :class="{ 'stop-use': String(scope.row.status) === '0' }">  
              {{ String(scope.row.status) === '0' ? '停售' : '起售' }}  
            </div>  
          </template>  
        </el-table-column>
        <!-- 最后操作时间列 -->  
        <el-table-column prop="updatedAt" label="最后操作时间" />   
        <!-- 操作列 -->  
        <el-table-column label="操作" width="250">  
          <template #default="scope">  
            <el-button type="text" size="small" class="blueBug" @click="addPackageType(scope.row.id)">  
              修改  
            </el-button>  
            <el-button type="text" size="small" class="delBut" @click="deleteHandle('单删', scope.row.id)">  
              删除  
            </el-button>  
            <el-button  
              type="text"  
              size="small"  
              :class="{ blueBug: scope.row.status == '0', delBut: scope.row.status != '0' }"  
              @click="statusHandle(scope.row)"  
            >  
            {{ scope.row.status == '0' ? '起售' : '停售' }}  
            </el-button>  
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
  </div>  
</template>  
 
<script setup lang="ts">  
import { ElMessage, ElMessageBox } from 'element-plus';
import { ref, reactive, onMounted, Ref } from 'vue';  
import {   
    getPackagePage,   
    enableOrDisablePackage,   
    deletePackage,   
    editPackage,   
    addPackage,   
    queryPackageById   
} from '~/api/package';
import { CategoryList, queryBookList } from '~/api/book';
import UploadImage from "~/components/UploadImage.vue";

interface BookList {
  value: string | number; // 根据需要定义类型  
  label: string;  
} 
let bookList : Ref<BookList[]> = ref([]); // 可选书籍列表 

const input = ref('');  
const packageStatus = ref('');  
const packageStatusList = [  
    { label: '全部', value: '' },  
    { label: '起售', value: '1' },  
    { label: '停售', value: '0' }  
];  

let checkList: Ref<string[]> = ref([]); 
let tableData = ref([]);  
const selectedPackages = ref([]);  
let total = ref(0);  
const page = ref(1);  
const pageSize = ref(10);  
const dialogFormVisible = ref(false);
const formLabelWidth = '100px'; // 根据需要设置
const form = reactive({  
    packageName: '',  
    bookIds: [],  
    price: '',  
    status: '1',
    categoryId: '',
    image:''  
});

let packageId:Ref<string | (string | null)[]> = ref([])

const categoryId = ref(''); 
interface BookCategory {  
  value: string | number; // 根据需要定义类型  
  label: string;  
}
let bookCategoryList: Ref<BookCategory[]> = ref([]);  
// 初始化数据  
const init = async (isSearch?: Boolean) =>{
  isSearch = isSearch
  await getPackagePage({
    name: input.value,  
    status: packageStatus.value || null,  
    page: page.value,  
    pageSize: pageSize.value,
    categoryId: categoryId.value,  
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

// 全部操作
const handleSelectionChange = (val :any) =>{
  checkList.value = val.map((item: any) => item.id); // 直接映射  
}


const handleSizeChange = (val: any) =>{
  pageSize.value = val
  init()
}

const handleCurrentChange = (val: any) =>{
  page.value = val
  init()
}

const resetForm = () => {
  form.packageName= '',  
  form.bookIds= [],  
  form.price='',  
  form.status= '1',
  form.categoryId= '',
  form.image= ''
};

// 根据传入的套餐 ID 获取套餐信息  
async function addPackageType(Id: string | (string | null)[]) {  
    if (Id) {  
        // 修改已有套餐  
        try {  
            const response = await queryPackageById(Id);  
            const selectedPackage = response.data.data; // 假设返回的数据为套餐对象  
            Object.assign(form, {  
                ...selectedPackage, 
            });
            form.categoryId = selectedPackage.categoryId
            form.bookIds = selectedPackage.packageBooks.map((book:any) => book.bookId);
        } catch (error) {  
            console.error('获取套餐详情失败:', error);  
        }
        packageId.value=Id  
    } else {  
        // 创建新套餐  
        resetForm(); // 确保新套餐时重置表单  
        packageId.value= ''//清空ID
    }  
    dialogFormVisible.value = true; // 统一放到最后  
}  

// 取消操作  
function cancel() {  
    dialogFormVisible.value = false; // 关闭对话框
    resetForm()  
}  

// 提交套餐  
const submitPackage = async () =>{  
    // 确保表单数据有效性  
    if (!form.packageName || !form.price ||!form.categoryId ||!form.status || form.bookIds.length === 0) {  
      alert('请填写完整信息') 
      //清空表单
      resetForm() 
      return; // 如果数据无效，则直接返回  
    }  
    try {  
        if (packageId.value) {  
            await editPackage(form); // 编辑已有套餐  
        } else {  
            await addPackage(form); // 添加新套餐  
        }  
        ElMessage.success('套餐操作成功！'); // 提交后显示成功消息  
        dialogFormVisible.value = false; // 关闭对话框  
        init(); // 重新加载数据  
    } catch (error) {  
        console.error('提交套餐失败:', error);  
        ElMessage.error('提交失败：' + (error as Error).message); // 提交失败的提示  
    }  
}
//删除
const deleteHandle = (type: string, id: any) => {
  if(type === '批量' && id === null) {
    if(checkList.value.length === 0) {
      return ElMessage.error('请选择删除对象')
    }
  }
  ElMessageBox.confirm('确认删除该套餐, 是否继续?', '确定删除', {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(()=>{
      deletePackage(type === '批量' ? checkList.value.join(',') : id)
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

const statusHandle=(row: { status: string; id: any; }) =>{  
    ElMessageBox.confirm('确认更改该商品状态?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(()=>{
      // 在用户确认后更改状态  
      row.status = row.status === '0' ? '1' : '0';  
      enableOrDisablePackage({ id: row.id, status: row.status })
        .then(res => {
          if(res && res.data && res .data.code === 1) {
            ElMessage.success('套餐状态更改成功！')
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

//获取书籍分类下拉数据
const getBookCategoryList = () => {
  CategoryList()
    .then(res => {
      if (res && res.data && res.data.code === 1) {
        bookCategoryList.value = (
          res.data &&
          res.data.data
        ).map((item: any) => {
          return { value :item.id, label: item.name}
        })
      }
    })
    .catch(error =>{
      ElMessage.error('获取书籍分类失败：',error.message)
    })
}

const getBookList = () => {
  queryBookList()
    .then(res => {
      if (res && res.data && res.data.code === 1) {
        bookList.value = (
          res.data &&
          res.data.data
        ).map((item: any) => {
          return { value :item.id, label: item.title}
        })
      }
    })
    .catch(error => {
      ElMessage.error('获取书籍列表失败：',error.message)
    })
}

// 页面加载时初始化数据  
onMounted( async () => {
  await init()
  await getBookCategoryList()
  await getBookList()
})

const imageChange = (imageUrl: any) =>{
    form.image = imageUrl; // 更新 userData.image  
}
</script>

<style scoped lang="scss">  
.dashboard-container {  
  margin: 30px, 0;  

  .container {  
    background: #fff;  
    padding: 30px 28px;  
    border-radius: 4px;  

    .normal-btn {  
      background: #333333;  
      color: white;  
      margin-left: 20px;  
      }
    }  

    .tableBar {  
      margin-bottom: 20px;  

      .tableLab {  
        display: inline-block;  

        :is(span) {  
          cursor: pointer;  
          display: inline-block;  
          font-size: 14px;  
          padding: 0 20px;  
          color: #999;  
        }  
      }  
    }  

    .tableBox {  
      width: 100%;  
      border: 1px solid #ccc;  
      border-bottom: 0;  
    }  

    .pageList {  
      text-align: center;  
      margin-top: 30px;  
    }  

    .tableColumn-status {  
      /*  定义状态的样式   */
      &.stop-use {  
        color: red;  
      }  
    }  
  }    
</style>