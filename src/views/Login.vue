<template>
    <div class="login">
        <Particles
        id="tsparticles"
        class="login__particles"
        :options="options"
        />
        <h2>用户登录</h2>
        <div class="loginPart" v-if="pageType == 1">
            <h2>用户登录</h2>
            <el-form 
            ref="loginFormRef"
            :model="loginForm"
            :rules="lrules"
            status-icon
            label-width="100px"
            class="demo-ruleForm"
            style="transform:translate(-30px);"
            @submit.native.prevent
            >
                <el-form-item label="用户名：" prop="username">
                    <el-input placeholder="请输入用户名" maxlength="20" clearable v-model="loginForm.username"/>
                </el-form-item>
                <el-form-item label="密码：" prop="password">
                    <el-input type="password" placeholder="请输入密码" maxlength="20" show-password clearable v-model="loginForm.password" />
                </el-form-item>
                <el-form-item label="验证码：" prop="verifyCode">
                    <el-input style="width: 150px;"  placeholder="请输入验证码" maxlength="4" clearable  v-model="loginForm.verifyCode"/>
                    <img
                      @click="changeValiCode()"
                      class="img1"
                      referrerpolicy="no-referrer"
                      :src="img"
                    />
                </el-form-item>
                <el-button class="btn" type="primary" @click.native.prevent="Login" >
                  <span v-if="!loading">登录</span>
                  <span v-else>登录中...</span>
                </el-button>
                <div style="display: flex; justify-content: space-between; align-items: center;">
                    <div style="text-align: left; transform:translate(30px,20px);">
                        <el-link type="warning" @click="goUpdatePassword()">
                            忘记/更改密码
                        </el-link>
                    </div>
                    <div style="text-align: right; transform:translate(30px,20px);">
                        <el-link type="warning" @click="goRegister()">
                            没有账号？去注册
                        </el-link>
                    </div>
                </div>
            </el-form>
        </div>

        <div class="loginPart" v-if="pageType == 2">
            <h2>用户注册</h2>
            <el-form 
            ref="registerFormRef"
            :model="registerForm"
            :rules="rules"
            status-icon
            label-width="100px"
            class="demo-ruleForm"
            style="transform:translate(-30px);"
            >
                <el-form-item label="用户名：" prop="username">
                    <el-input  placeholder="请输入用户名" maxlength="20" clearable v-model="registerForm.username"/>
                </el-form-item>
                <el-form-item label="密码：" prop="password">
                    <el-input  type="password" placeholder="请输入密码" maxlength="20" show-password clearable v-model="registerForm.password"/>
                </el-form-item>
                <el-form-item label="确认密码：" prop="confirmPassword">
                    <el-input  type="password" placeholder="请确认密码" maxlength="20" show-password clearable v-model="registerForm.confirmPassword"/>
                </el-form-item>
                <el-form-item label="邮箱：" prop="email">
                    <el-input  placeholder="请输入邮箱" maxlength="20" clearable v-model="registerForm.email"/>
                </el-form-item>
                <el-form-item label="验证码：" prop="verifyCode">
                    <el-input style="width: 150px;"  placeholder="请输入验证码" maxlength="6" clearable  v-model="registerForm.verifyCode"/>
                    <el-button size="small" type="primary" @click="GetVerifyCode(registerForm.email)">
                      <span v-if="!sented">获取验证码</span>
                      <span v-else>已发送</span>
                    </el-button>
                </el-form-item>
                <el-button class="btn" type="primary" @click="Register" >注册</el-button>
                <div style="text-align: right;transform:translate(0,30px);">
                    <el-link type="warning" @click="backLogin()">
                        已有账号？去登录
                    </el-link>
                </div>
            </el-form>
        </div>

        <div class="loginPart" v-if="pageType == 3">
            <h2>密码重置</h2>
            <el-form 
            ref="registerFormRef"
            :model="registerForm"
            :rules="rules"
            status-icon
            label-width="100px"
            class="demo-ruleForm"
            style="transform:translate(-30px);"
            >
                <el-form-item label="用户名：" prop="username">
                    <el-input  placeholder="请输入用户名" maxlength="20" clearable v-model="registerForm.username"/>
                </el-form-item>
                <el-form-item label="密码：" prop="password">
                    <el-input  type="password" placeholder="请输入新密码" maxlength="20" show-password clearable v-model="registerForm.password"/>
                </el-form-item>
                <el-form-item label="确认密码：" prop="confirmPassword">
                    <el-input  type="password" placeholder="请确认新密码" maxlength="20" show-password clearable v-model="registerForm.confirmPassword"/>
                </el-form-item>
                <el-form-item label="邮箱：" prop="email">
                    <el-input  placeholder="请输入邮箱" maxlength="20" clearable v-model="registerForm.email"/>
                </el-form-item>
                <el-form-item label="验证码：" prop="verifyCode">
                    <el-input style="width: 150px;"  placeholder="请输入验证码" maxlength="6" clearable  v-model="registerForm.verifyCode"/>
                    <el-button size="small" type="primary" @click="GetVerifyCode(registerForm.email)">
                      <span v-if="!sented">获取验证码</span>
                      <span v-else>已发送</span>
                    </el-button>
                </el-form-item>
                <el-button class="btn" type="primary" @click="UpdatePassword" >重置密码</el-button>
                <div style="text-align: right;transform:translate(0,30px);">
                    <el-link type="warning" @click="backLogin()">
                        记得密码？去登录
                    </el-link>
                </div>
            </el-form>
        </div>

        <div class="footer">
            © 2024 XPXP. All rights reserved.
        </div>
    </div>
</template>

<script setup lang="ts">
import { error } from 'console'
import { ElMessage, FormInstance } from 'element-plus'
import { onMounted, reactive, ref } from 'vue'
import {checkVerifyCode, getImageCode, getVerifyCode, register, testImageCode, updatePassword, }from '~/api/employee'
import router from '~/router'
import {useUserStore} from '~/store/user'
import { isEmail } from '~/utils/validate'
const userStore = useUserStore();
let loading = ref(false);
let sented = ref(false);  
// const username = ref('');  
// const password = ref('');  
const img = ref('');
const valiCode= ref('');

const loginForm = reactive({
  username: '',
  password: '',
  verifyCode:'',
})

const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword:'',
  verifyCode:'',
  email:'',
})
const registerFormRef = ref<FormInstance>()
const backCode = ref('');

const checkEmail= (rule:any, value:any, callback:any) => {  
        if (!value) {  
            callback(new Error(`请填写 ${rule.field} 字段`)); // 使用 rule.field 生成多语言或动态错误消息  
        } else if (!isEmail(value)) {  
            callback(new Error(`请输入有效的 ${rule.field} 地址`)); // 动态生成错误消息  
        } else {  
            callback(); // 验证通过  
        }  
    }
const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 15, message: '用户名长度应在2~5个字符之间', trigger: 'blur' },
  ],
  password: [
  { required: true, message: '请输入密码', trigger: 'blur' },
  { min: 6, message: '密码长度不应小于6个字符', trigger: 'blur' },
  ],
  confirmPassword: [  
        { required: true, message: '请确认密码', trigger: 'blur' },  
        { validator: (rule: any, value: string, callback: any) => {  
            if (value !== registerForm.password) {  
                callback(new Error('两次输入的密码不一致'));  
            } else {  
                callback();  
            }  
        }, trigger: 'blur' }  
    ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    {validator: checkEmail,trigger: 'blur'}
  ],
})

const loginFormRef = ref<FormInstance | null>(null);

const pageType = ref(1)

const lrules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 15, message: '用户名长度应在2~5个字符之间', trigger: 'blur' },
  ],
  password: [
  { required: true, message: '请输入密码', trigger: 'blur' },
  { min: 6, message: '密码长度不应小于6个字符', trigger: 'blur' },
  ],
  verifyCode: [
  { required: true, message: '请输入验证码', trigger: 'blur' },]
})

const Register = async () => {
  try {
    // 验证表单  
    const isFormValid = await registerFormRef.value!.validate();  
    if (!isFormValid) {  
      return; // 如果表单无效，直接返回  
    } 
    
    // 检查验证码  
    const verifyResponse = await checkVerifyCode({  
      email: registerForm.email,  
      code: registerForm.verifyCode,  
    });

    if (verifyResponse.data.code !== 1) {  
      ElMessage(verifyResponse.data.msg);  
      return; // 如果验证码验证失败，返回  
    }  

    //注册
    const registerResponse  = await register({
      username: registerForm.username, 
      password: registerForm.password, 
      email: registerForm.email,
    });

    if( registerResponse.data.code === 1) {
      ElMessage("注册成功！");
      pageType.value=1
    } else {  
      ElMessage(`注册请求失败：${registerResponse.data.msg}`);  
    }  
  } catch (error) {
    ElMessage(`请求失败：${(error as Error).message}`);
  }
}

const Login = async () => {  
  try {  
    // 验证表单  
    const valid = await loginFormRef.value!.validate();  
    if (!valid) {  
      return; // 如果表单无效，直接返回  
    }  

    // 检查验证码  
    const res = await testImageCode({ identity: valiCode.value, verifycode: loginForm.verifyCode });  
    if (res.data.code !== 1) {  
      ElMessage(res.data.msg);  
      return; // 如果验证码验证失败，返回  
    }  

    loading.value = true;  

    // 登录操作  
    const loginResponse = await userStore.Login({ username: loginForm.username, password: loginForm.password });  
    
    if (loginResponse.code === 1) {  
      // 登录成功，跳转到系统首页  
      // router.push('/Index');  
    } else {   
      loading.value = false;   
      ElMessage.error('登录失败，账号密码错误：', loginResponse.msg);  
    }  
  } catch (error) {  
    loading.value = false; // 在发生错误时确保loading状态被重置  
    ElMessage.error('请求错误：' + (error as Error).message);  
  }  
};

const UpdatePassword = async () => { 
  try {
    // 验证表单  
    const isFormValid = await registerFormRef.value!.validate();  
    if (!isFormValid) {  
      return; // 如果表单无效，直接返回  
    }  

    // 检查验证码  
    const verifyResponse = await checkVerifyCode({  
      email: registerForm.email,  
      code: registerForm.verifyCode,  
    });

    if (verifyResponse.data.code !== 1) {  
      ElMessage(verifyResponse.data.msg);  
      return; // 如果验证码验证失败，返回  
    }   

    // 更新密码  
    const updateResponse = await updatePassword({  
      username: registerForm.username,  
      password: registerForm.password,  
      email: registerForm.email,  
    });  

    if (updateResponse.data.code === 1) {  
      ElMessage("修改密码成功！");  
      pageType.value = 1; // 更新页面状态  
    } else {  
      ElMessage(`修改密码请求失败：${updateResponse.data.msg}`);  
    }  
    
  } catch (error) {
    ElMessage(`请求失败：${(error as Error).message}`);
  }  
    
};

const GetVerifyCode = async(email: string) => {
  if(isEmail(email)) {
    console.log(email)
    await getVerifyCode({email:email})
      .then((res:any) => {
        if(res.data.code === 1) {
          ElMessage("验证码已成功发送，请注意查收！")
          backCode.value = res.data;
        }else {
          ElMessage.error("验证码发送失败！")
        }
      }).catch(error=>{
        ElMessage("获取验证码失败：" + error.message);
      })   
  }else {
    ElMessage.error("发送请求失败！")
  }
}

const changeValiCode = async () =>{
  await getImageCode()
      .then(res =>{
        if(res.data.code===1){
          const data = res.data.data
          img.value = data.imgCode
          valiCode.value = data.identity
        }else{
          ElMessage("刷新失败")
        }
      }).catch(error=>{
        ElMessage.error("请求失败" + error.message)
      })
}

onMounted(async ()=>{
  await changeValiCode();
})
const backLogin = () => {  
  // 清空注册表单内容  
  registerForm.username = '';  
  registerForm.password = '';  
  registerForm.confirmPassword = '';  
  registerForm.verifyCode = '';
  registerForm.email= ''  
  
  // 设置当前页面类型为登录  
  pageType.value = 1;  
}  

const goUpdatePassword = () => {
  // 清空登录表单内容  
  loginForm.username = '';  
  loginForm.password = '';  
  loginForm.verifyCode = '';  
  
  // 设置当前页面类型为更新密码  
  pageType.value = 3; 
}

const goRegister = () => {  
  // 清空登录表单内容  
  loginForm.username = '';  
  loginForm.password = '';  
  loginForm.verifyCode = '';  
  
  // 设置当前页面类型为注册  
  pageType.value = 2;  
}



const options = {
  fpsLimit: 60,
  interactivity: {
    detectsOn: 'canvas',
    events: {
      onClick: { // 开启鼠标点击的效果
        enable: true,
        mode: 'push'
      },
      onHover: { // 开启鼠标悬浮的效果(线条跟着鼠标移动)
        enable: true,
        mode: 'grab'
      },
      resize: true
    },
    modes: { // 配置动画效果
      bubble: {
        distance: 400,
        duration: 2,
        opacity: 0.8,
        size: 40
      },
      push: {
        quantity: 4
      },
      grab: {
        distance: 200,
        duration: 0.4
      },
      attract: { // 鼠标悬浮时，集中于一点，鼠标移开时释放产生涟漪效果
        distance: 200,
        duration: 0.4,
        factor: 5
      }
    }
  },
  particles: {
    color: {
      value: '#BA55D3' // 粒子点的颜色
    },
    links: {
      color: '#FFBBFF', // 线条颜色
      distance: 150,//线条距离
      enable: true,
      opacity: 0.4, // 不透明度
      width: 1.2 // 线条宽度
    },
    collisions: {
      enable: true
    },
    move: {
      attract: { enable: false, rotateX: 600, rotateY: 1200 },
      bounce: false,
      direction: 'none',
      enable: true,
      out_mode: 'out',
      random: false,
      speed: 0.5, // 移动速度
      straight: false
    },
    number: {
      density: {
        enable: true,
        value_area: 800
      },
      value: 80//粒子数
    },
    opacity: {//粒子透明度
      value: 0.7
    },
    shape: {//粒子样式
      type: 'star'
    },
    size: {//粒子大小
      random: true,
      value: 3
    }
  },
  detectRetina: true
}

</script>
<style scoped lang="scss">
.footer {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    padding: 10px;
    background-color: #FFF0F5;
    text-align: center;
    font-size: 12px;
    color: #999;
  }
.login {
    height: 100%;
    width: 100%;
    overflow: hidden;
  }
  .login__particles {
    height: 100%;
    width: 100%;
    background-size: cover;
    background-repeat: no-repeat;
    background-image: url('~/assets/0001.jpg');
    opacity:0.9;
    position:fixed;
     pointer-events: none;
  }
  .loginPart{
    position:absolute;
    /*定位方式绝对定位absolute*/
    top:50%;
    left:80%;
    /*顶和高同时设置50%实现的是同时水平垂直居中效果*/
    transform:translate(-50%,-50%);
    /*实现块元素百分比下居中*/
    width:450px;
    padding:50px;
    background: rgba(255,204,255,.3);
    /*背景颜色为黑色，透明度为0.8*/
    box-sizing:border-box;
    /*box-sizing设置盒子模型的解析模式为怪异盒模型，
    将border和padding划归到width范围内*/
    box-shadow: 0px 15px 25px rgba(0,0,0,.5);
    /*边框阴影  水平阴影0 垂直阴影15px 模糊25px 颜色黑色透明度0.5*/
    border-radius:15px;
    /*边框圆角，四个角均为15px*/
  }
  h2{
    margin:0 0 30px;
    padding:0;
    color: #fff;
    text-align:center;
    /*文字居中*/
  }
  .btn{
    transform:translate(170px);
    width:80px;
    height:40px;
    font-size:15px;
  }
</style>
