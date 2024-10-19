<template>
    <div class="login">
        <Particles
        id="tsparticles"
        class="login__particles"
        :options="options"
        />
        <div class="loginPart">
            <h2>用户注册</h2>
            <el-form 
            ref="registerFromRef"
            :model="registerForm"
            :rules="rules"
            status-icon
            label-width="100px"
            class="demo-ruleForm"
            style="transform:translate(-30px);"
            >
                <el-form-item label="用户名：" prop="account">
                    <el-input  placeholder="请输入用户名" maxlength="20" clearable v-model="registerForm.username"/>
                </el-form-item>
                <el-form-item label="密码：" prop="password">
                    <el-input  type="password" placeholder="请输入密码" maxlength="20" show-password clearable v-model="registerForm.password"/>
                </el-form-item>
                <el-form-item label="确认密码：" prop="confirmPassword">
                    <el-input  type="password" placeholder="请输入确认密码" maxlength="20" show-password clearable v-model="registerForm.confirmPassword"/>
                </el-form-item>
                <el-form-item label="验证码：" prop="verifyCode">
                    <el-input style="width: 150px;"  placeholder="请输入验证码" maxlength="4" clearable  v-model="registerForm.verifyCode"/>
                    <img class="verifyCodeImg" >
                </el-form-item>
                <el-button class="btn" type="primary" @click="Register" >注册</el-button>
                <div style="text-align: right;transform:translate(0,30px);">
                    <el-link type="warning">
                        <router-link to="/Login">已有账号？去登录</router-link>
                    </el-link>
                </div>
            </el-form>
        </div>
        <div class="footer">
            © 2024 LJJie. All rights reserved.
        </div>
    </div>
</template>

<script setup lang="ts">
import { FormInstance } from 'element-plus'
import { reactive, ref } from 'vue'
import {login, userLogout}from '~/api/employee'
import router from '~/router'

const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword:'',
  verifyCode:'',
})

const registerFormRef = ref<FormInstance>()

const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 5, message: '用户名长度应在2~5个字符之间', trigger: 'blur' },
  ],
  password: [
  { required: true, message: '请输入密码', trigger: 'blur' },
  { min: 6, message: '密码长度不应小于6个字符', trigger: 'blur' },
  ],
  confimrPassword: [
  { required: true, message: '请输入密码', trigger: 'blur' },
  { min: 6, message: '密码长度不应小于6个字符', trigger: 'blur' },
  ]
})

const Register = () => {
  registerFormRef.value!.validate(async (valid: any)=>{
    if(valid) {
      await login(registerForm).then(res => {  
    if (String(res.data.code) === '1') {
      //注册成功，跳转到登录页
      router.push('/Login')
    } else {
      console.error('注册失败，返回数据不完整：', res);  
    }
  }).catch((error) => {  
    console.error('注册请求失败：', error);  
  });  
    }else {
      return
    }
  })
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
