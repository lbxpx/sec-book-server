<template>  
  <div class="dashboard-container">  
    <div class="container">  
      <div class="tableBar">  
        <!-- 书名输入框 -->  
        <label style="margin-right: 10px">书名：</label>  
        <el-input   
          v-model="input"  
          placeholder="请填写书名"  
          style="width: 14%"  
          clearable  
          @clear="init"  
          @keyup.enter.native="initFun"   
        />  

        <!-- 书籍分类选择框 -->  
        <label style="margin-right: 10px; margin-left: 20px">书籍分类：</label>  
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

        <!-- 售卖状态选择框 -->  
        <label style="margin-right: 10px; margin-left: 20px">售卖状态：</label>  
        <el-select   
          v-model="bookStatus"  
          style="width: 14%"  
          placeholder="请选择"  
          clearable  
          @clear="init"  
        >  
          <el-option   
            v-for="item in saleStatus"  
            :key="item.value"  
            :label="item.label"  
            :value="item.value"   
          />  
        </el-select>  

        <!-- 查询按钮 -->  
        <el-button 
          style="margin-right: 50px"   
          class="normal-btn continue"  
          @click="init(true)"  
        >  
          查询  
        </el-button>  

        <div class="tableLab" style="margin: 0,25px;">  
          <!-- 批量删除链接 -->  
          <span class="delBut non"  
                @click="deleteHandle('批量', null)"  
          >   
            批量删除  
          </span>  
          <!-- 新建书籍按钮 -->  
          <el-button   
            type="primary"  
            style="margin-left: 50px"  
            @click="addBookType(null)"  
          >  
            + 新建书籍  
          </el-button>
                <!-- 以下为弹出框-->
                <el-dialog v-model="dialogFormVisible" title="书籍管理" width="550">
                  <el-form :model="form">
                    <el-form-item label="书名" :label-width="formLabelWidth">
                      <el-input v-model="form.title" autocomplete="off" />
                    </el-form-item>
                    
                    <el-form-item label="书籍封面" prop="coverImage" :label-width="formLabelWidth">  
                      <upload-image 
                          @imageChange="imageChange" style="margin-right: 10px;">
                      </upload-image> 
                      <span style=" color:grey">图片大小不超过2M<br>仅能上传 PNG JPEG JPG类型图片<br>建议上传200*200或300*300尺寸的图片 </span>
                    </el-form-item>

                    <el-form-item label="封面图片链接" :label-width="formLabelWidth">
                      <el-input v-model="form.coverImage" autocomplete="off" />
                    </el-form-item>
                    
                    <el-form-item label="作者" :label-width="formLabelWidth">
                      <el-input v-model="form.author" autocomplete="off" />
                    </el-form-item>

                    <el-form-item label="ISBN" :label-width="formLabelWidth">
                      <el-input v-model="form.isbn" autocomplete="off" />
                    </el-form-item>

                    <el-form-item label="出版日期" :label-width="formLabelWidth">
                      <el-date-picker
                        style="width: 330px;"
                        v-model="form.publicationDate"
                        type="date"
                        placeholder="点击选择日期"
                        value-format="YYYY-MM-DD"
                        :size="size"
                      />
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

                    <el-form-item label="价格￥" :label-width="formLabelWidth">
                      <el-input v-model="form.price" autocomplete="off" />
                    </el-form-item>

                    <el-form-item label="库存" :label-width="formLabelWidth">
                      <el-input v-model="form.inventory" autocomplete="off" />
                    </el-form-item>
                  </el-form>
                  <template #footer>
                    <div class="dialog-footer">
                      <el-button @click="cancel">取消</el-button>
                      <el-button type="primary" @click="submitBook">确定</el-button>
                    </div>
                  </template>
                </el-dialog>

          <!-- 弹出框到此为止 -->
        </div>  
      </div>  

      <!-- 书籍表格 -->  
      <el-table   
        v-if="tableData.length"  
        :data="tableData"  
        stripe  
        class="tableBox"  
        @selection-change="handleSelectionChange"  
      >  
        <!-- 选择列 -->  
        <el-table-column type="selection" width="25" />  
        <!-- 书名列 -->  
        <el-table-column prop="title" label="书名" />  
        <!-- 图片列 -->  
        <el-table-column prop="coverImage" label="图片">  
          <template #default="{ row }">  
            <el-image   
              style="width: 80px; height: 40px; border: none; cursor: pointer"  
              :src="row.coverImage"  
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
        <!-- 书籍分类列 -->  
        <el-table-column prop="categoryName" label="书籍分类" />  
        <!-- 售价列 -->  
        <el-table-column label="售价">  
          <template #default="scope">  
            <span style="margin-right: 10px">￥{{ (scope.row.price).toFixed(2) }}</span>  
          </template>  
        </el-table-column>  
        <!-- 售卖状态列 -->  
        <el-table-column label="售卖状态">  
          <template #default="scope">  
            <div class="tableColumn-status" :class="{ 'stop-use': String(scope.row.status) === '0' }">  
              {{ String(scope.row.status) === '0' ? '停售' : '启售' }}  
            </div>  
          </template>  
        </el-table-column>  
        <!-- 最后操作时间列 -->  
        <el-table-column prop="updatedAt" label="最后操作时间"/>  
        <!-- 操作列 -->  
        <el-table-column label="操作" width="250">  
          <template #default="scope">  
            <el-button type="text" size="small" class="blueBug" @click="addBookType(scope.row.id)">  
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
              {{ scope.row.status == '0' ? '启售' : '停售' }}  
            </el-button>  
          </template>  
        </el-table-column>  
      </el-table>  

      <!-- 分页组件 -->  
      <el-pagination   
        v-if="total > 0"  
        class="pageList"  
        @size-change="handleSizeChange"  
        @current-change="handleCurrentChange"  
        :current-page="page"  
        :page-size="pageSize"  
        :total="total"  
        layout="total, sizes, prev, pager, next, jumper"   
      >  
      </el-pagination>  
    </div>  
  </div>  
</template>  

<script setup lang="ts">  
import { ElMessage, ElMessageBox } from 'element-plus';
import { ref, onMounted, Ref, reactive } from 'vue';
import { CategoryList, deleteBook, getProductPage, addBook, bookStatusByStatus, queryBookById, editBook } from '~/api/book'
import UploadImage from "~/components/UploadImage.vue";

interface BookCategory {  
  value: string | number; // 根据需要定义类型  
  label: string;  
}
interface Book {
  title:string;
  coverImage:string;
  categoryName:string;
  price:string;
  status:string;
  updatedAt: string;
  categoryId: string;
} 
// 定义响应式变量  
const input = ref('');  
const categoryId = ref('');  
let tableData = ref<Book[]>([]);  
let total = ref(0);  
const page = ref(1);  
const pageSize = ref(10); 
let bookState = ref(''); 
let bookCategoryList: Ref<BookCategory[]> = ref([]);  
const bookStatus = ref('');
let checkList: Ref<string[]> = ref([]);  
const saleStatus = ref([
  {
    value: "0",
    label: '停售'
  },
  {
    value: "1",
    label: '启售'
  }
]);
const form = reactive({
  title: '',
  author: '',
  isbn: '',
  publicationDate: '',
  price: '',
  inventory: '',
  coverImage: '',
  categoryId: '',
});
const dialogFormVisible = ref(false);
const formLabelWidth = '100px'; // 根据需要设置
const size = ref<'default' | 'large' | 'small'>('default')
let bookId:Ref<string | (string | null)[]> = ref([])

onMounted( async () => {
  await init()
  await getBookCategoryList()
})

const  init = async (isSearch?: Boolean) => {
  isSearch = isSearch
  await getProductPage({
    page: page.value,
    pageSize: pageSize.value,
    title: input.value,
    categoryId: categoryId.value,
    status: bookStatus.value || null
  })
    .then(res => {
      if( res.data.code === 1) {
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

//删除
const deleteHandle = (type: string, id: any) => {
  if(type === '批量' && id === null) {
    if(checkList.value.length === 0) {
      return ElMessage.error('请选择删除对象')
    }
  }
  ElMessageBox.confirm('确认删除该商品, 是否继续?', '确定删除', {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(()=>{
      deleteBook(type === '批量' ? checkList.value.join(',') : id)
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
// 根据传入的书籍 ID 获取书籍信息  
async function addBookType(id: string | null) {  
  if (id) {  
    // 编辑已有书籍  
    try {  
      const response = await queryBookById(id); // 假设有一个 API 获取书籍信息  
      const selectedBook = response.data.data; // 获取书籍数据 
      Object.assign(form, { ...selectedBook });
      form.categoryId=selectedBook.categoryId // 合并数据到表单  
    } catch (error) {  
      console.error('获取书籍详情失败:', error);  
    }  
    bookId.value = id; // 设置当前书籍 ID  
  } else {  
    // 创建新书籍  
    resetForm(); // 确保表单重置  
    bookId.value = ''; // 清空 ID  
  }  
  dialogFormVisible.value = true; // 显示对话框  
}  

// 提交书籍操作  
const submitBook = async () => {  
  // 确保表单数据有效性  
  if (!form.title || !form.author || !form.isbn || !form.publicationDate || !form.price || !form.inventory){
      alert('请填写完整信息')
      //清空表单
      resetForm()
      return;
    }
  try {  
    if (bookId.value) {  
      await editBook(form); // 编辑已有书籍  
    } else {  
      await addBook(form); // 添加新书籍  
    }  
    ElMessage.success('书籍操作成功！'); // 提交后显示成功消息  
    dialogFormVisible.value = false; // 关闭对话框  
    init(); // 重新加载数据  
  } catch (error) {  
    console.error('提交书籍失败:', error);  
    ElMessage.error('提交失败：' + (error as Error).message); // 提交失败的提示  
  }  
}; 

const cancel = () => {
  resetForm();
  dialogFormVisible.value = false; // 关闭弹框
};

const resetForm = () => {
  form.title = '';
  form.author = '';
  form.isbn = '';
  form.publicationDate = '';
  form.price = '';
  form.inventory = '';
  form.coverImage = '';
  form.categoryId= ''
};


// 全部操作
const handleSelectionChange = (val :any) =>{
  checkList.value = val.map((item: any) => item.id); // 直接映射  
}

//状态更改
const statusHandle = (row: any) =>{
  let params: any = {}
  if(typeof row === 'string'){
    if(checkList.value.length === 0) {
      ElMessage.error('批量操作，请先勾选操作书籍！')
      return false
    }
    params.id = checkList.value.join(',')
    params.status = row
  }else{
    params.id = row.id
    params.status = row.status === '0' ? '1' : '0'; // 如果当前状态是停售，那么更改为启售  
  }
  bookState = params
  ElMessageBox.confirm('确认更改该商品状态?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      // 起售停售---批量起售停售接口
      bookStatusByStatus(bookState)
        .then(res => {
          if(res && res.data && res .data.code === 1) {
            ElMessage.success('商品状态更改成功！')
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

const handleSizeChange = (val: any) =>{
  pageSize.value = val
  init()
}

const handleCurrentChange = (val: any) =>{
  page.value = val
  init()
}


const imageChange = (imageUrl: any) =>{
    form.coverImage = imageUrl; // 更新 userData.image  
}
</script>  

<style lang="scss" scoped>  
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
      // 定义状态的样式  
      &.stop-use {  
        color: red;  
      }  
    }  
  }    
</style>