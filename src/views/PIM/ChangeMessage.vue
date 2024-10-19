<template>
    <el-container>  
    <el-header>  
        修改个人信息
      </el-header>
      <el-main>  
        <el-form :model="userData" :rules="rules" ref="formRef" label-width="120px">  
          
          <el-form-item label="头像" prop="image">  
            <upload-image :prop-image-url="userData.image"
                          @imageChange="imageChange" style="margin-right: 10px;">
            </upload-image> 
            <span style=" color:grey">图片大小不超过2M<br>仅能上传 PNG JPEG JPG类型图片<br>建议上传200*200或300*300尺寸的图片 </span>
          </el-form-item>  
          
          <el-form-item label="姓名" prop="name">  
            <el-input v-model="userData.name" placeholder="输入姓名" style="width: 300px;"></el-input>  
          </el-form-item>  
          
          <el-form-item label="大学" prop="university">  
            <el-input v-model="userData.university" placeholder="输入大学名称" style="width: 300px;"></el-input>  
          </el-form-item>  
          
          <el-form-item label="介绍" prop="introduction">  
            <el-input v-model="userData.introduction" type="textarea" placeholder="输入个人介绍" style="width: 300px;"></el-input>  
          </el-form-item>  
          
          <el-form-item label="邮箱" prop="email">  
            <el-input v-model="userData.email" placeholder="输入邮箱" style="width: 300px;"></el-input>  
          </el-form-item>  
          
          <el-form-item label="出生日期" prop="birth">  
            <el-date-picker v-model="userData.birth" type="date" placeholder="选择出生日期" />  
          </el-form-item>  
          
          <el-form-item label="注册日期" prop="createdAt">  
            <el-input v-model="userData.createdAt" disabled style="width: 300px;"></el-input>  
          </el-form-item>  
          
          <el-form-item label="修改密码" prop="password">  
            <el-input v-model="userData.password" placeholder="输入新密码" type="password" style="width: 300px;"></el-input>  
          </el-form-item>  
          
          <el-form-item label="确认密码" prop="confirmPassword">  
            <el-input v-model="userData.confirmPassword" placeholder="确认新密码" type="password" style="width: 300px;"></el-input>  
          </el-form-item>  
          
          <el-form-item>  
            <el-button type="primary" @click="submitForm">提交</el-button>  
            <el-button @click="resetForm">重置</el-button>  
          </el-form-item>  
        </el-form>  
      </el-main>  
    </el-container>  
</template>  

<script setup lang="ts">  
import { ref, reactive, onMounted } from 'vue';  
import { ElForm, ElMessage } from 'element-plus';  
import UploadImage from "~/components/UploadImage.vue";
import { useUserStore } from '~/store/user';
import { editUserData, getUserData } from '~/api/userData';

const userStore = useUserStore();
const userId = userStore.userId; 
// 定义用户信息数据结构  
const userData = reactive({
    userId:userId,  
    image: '',  
    name: '',  
    university: '',  
    introduction: '',  
    email: '',  
    createdAt: '',  
    password: '',  
    confirmPassword: '', // 新增确认密码字段  
    birth: '',  
});  

// 表单验证规则  
const rules = {  
    name: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],  
    email: [{ required: true, message: '邮箱不能为空', trigger: 'blur' }],  
    university: [{ required: true, message: '大学不能为空', trigger: 'blur' }],  
    password: [  
        { required: true, message: '密码不能为空', trigger: 'blur' },  
        { min: 6, message: '密码长度至少为6个字符', trigger: 'blur' }  
    ],  
    confirmPassword: [  
        { required: true, message: '请确认密码', trigger: 'blur' },  
        { validator: (rule: any, value: string, callback: any) => {  
            if (value !== userData.password) {  
                callback(new Error('两次输入的密码不一致'));  
            } else {  
                callback();  
            }  
        }, trigger: 'blur' }  
    ],  
};  

// 引用表单  
const formRef = ref<InstanceType<typeof ElForm> | null>(null);  

// 提交表单  
const submitForm = async () => {  
    if (formRef.value) {  
        await formRef.value.validate(async (valid) => {  
            if (valid) {  
                try {  
                    // 发送用户数据（不包括 confirmPassword）  
                    const { confirmPassword, ...dataToSend } = userData;  
                    const response = await editUserData(dataToSend);
                    if(response.data.code===1) {
                        ElMessage('用户信息更新成功:')
                    }
                } catch (error) {  
                    ElMessage.error(`更新用户信息失败: ${(error as Error).message}`);  
                }  
            } else {  
                ElMessage('表单验证失败');  
            }  
        });  
    }  
};  

// 重置表单  
const resetForm = () => {  
    if (formRef.value) {  
        formRef.value.resetFields();  
    }  
};

const imageChange = (imageUrl: any) =>{
    userData.image = imageUrl; // 更新 userData.image  
}
onMounted(async () => {  
    try {  
        const res = await getUserData(userId)
        Object.assign(userData,{ ...res.data.data })
    } catch (error) {  
        console.error('获取用户数据失败:', error);  
    }  
});  
</script>  

<style scoped lang="scss">  
.el-header {  
    text-align: center;  
    font-size: 28px;  
    color: #35495e;   
    font-weight: bold;
    /* line-height: 30px;   */
}  

.el-main {  
    background-color: #f2f7fc; /* 页面背景，使其更加柔和 */  
    padding: 20px;  
    border-radius: 10px; /* 圆角 */  
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 阴影 */  
}  

.el-form-item {  
    margin-bottom: 20px; /* 项目之间的间距 */  
}  

.el-form-item label {  
    font-weight: 600; /* 标签字体加粗 */  
    color: #333;  
}  

.el-button {  
    width: 120px; /* 按钮固定宽度 */  
    margin-right: 10px; /* 按钮之间的间距 */  
}  

.el-input, .el-input[type="textarea"], .el-date-picker {  
    border-radius: 5px; /* 输入框圆角 */  
    font-size: 16px; /* 输入框字体大小 */  
}  

.el-input[disabled] {  
    background-color: #e4e4e4    
    /* 输入框禁用状态的背景 */  
}  

.el-input:hover, .el-button:hover {  
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); /* 悬浮效果 */  
}  

 
</style>