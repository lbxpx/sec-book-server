<template>  
    <div class="container" v-if="userData">  
        <div class="left">  
            <div class="head"></div>  
            <div class="pic">  
                <img :src="userData.image" alt="User Avatar">  
                <span>{{ userData.name }}</span>  
            </div>  
            <div class="bottom"></div>  
        </div>  

        <div class="right">  
            <div class="first">  
                <span>背景资料</span>  
            </div>  

            <div class="sec">  
                <div class="edu">  
                    <div>大学：{{ userData.university }}</div>  
                    <div>角色：{{ userData.role ==='admin' ? '管理员' : '用户' }}</div>  
                </div>  
                <span>个人介绍:</span>  
            </div>  

            <p>&nbsp&nbsp{{ userData.introduction === '' ? '暂无数据，请前往“信息修改”页面进行补充': userData.introduction}}</p>  

            <div class="first">
                <span></span>
                <span>账户信息</span>
            </div>

            <ul class="third">  
                <li class="title">用户名：</li>  
                <li class="tet">{{ userData.name }}</li>
                <li class="title">密码：</li>  
                <li class="tet"> ******** </li>    
                <li class="title">邮箱：</li>  
                <li class="tet">{{ userData.email }}</li>  
                <li class="title">注册日期：</li>  
                <li class="tet">{{ userData.createdAt === '' ? '暂无数据，请前往“信息修改”页面进行补充': userData.createdAt}}</li>
                <li class="title">出生日期：</li>  
                <li class="tet">{{ userData.birth === '' ? '暂无数据，请前往“信息修改”页面进行补充': userData.birth}}</li>     
            </ul>  

              
        </div>  
    </div>  
    <div v-else>加载中...</div>  
</template>  

<script setup lang="ts">  
import { onMounted, reactive } from 'vue';  
import { getUserData } from '~/api/userData';
import { useUserStore } from '~/store/user';

const userStore = useUserStore();
const userId = userStore.userId; 
const userData = reactive ({
    image: '',
    name: '',
    university: '',
    role: '',
    introduction:'',
    email:'',
    createdAt: '',
    password: '',
    birth: '',
}) 

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
* {  
    box-sizing: border-box;  
}  

.container {  
    display: flex;  
    height: 640px;  
}  

.left {  
    border: solid;  
    display: flex;  
    flex-direction: column;  
    flex: 3;  
}  

.left .head {  
    flex: 1;  
    background-color: pink;  
    height: 100px;  
}  

.left .pic {  
    display: flex;  
    flex-direction: column;  
    justify-content: center;  
    align-items: center;  
    flex: 8;  
}  

.pic img {  
    height: 400px;  
    width: 240px;  
    border-radius: 50%;  
    border: 5px solid #ffffff;  
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);  
}  

.pic span {  
    margin-top: 15px;  
    font-size: 30px;  
    color: pink;  
}  

.left .bottom {  
    flex: 1;  
    background-color: pink;  
    height: 100px;  
}  

.right {  
    display: flex;  
    flex-direction: column;  
    flex: 7;  
}  

.right .first {  
    display: flex;  
    flex: 1;  
    font-size: 25px;  
    color: #002f43;  
    margin-left: 50px;  
    border-bottom: solid 2px;  
    align-items: flex-end;  
}  

.first span {  
    margin-bottom: 10px;  
}  

.right .sec {  
    margin-left: 50px;  
    font-size: 18px;  
    color: #002f43;  
    display: flex;  
    flex: 0.8;  
    flex-direction: column;  
}  

.sec .edu {  
    display: flex;  
    margin: 10px 0;  
}  

.sec .edu :nth-child(2) {  
    margin-left: 250px;  
}  

.sec .edu :nth-child(1) {  
    margin-right: 80px;  
}  

.right .third {  
    flex: 4;  
    display: flex;  
    flex-direction: column;  
}  

.third span {  
    flex: 1;  
}  

.title {  
    margin-left: 10px;  
    font-size: 18px;  
    color: #002f43;  
    list-style: none;  
}  

.tet {  
    margin-left: 40px;  
    font-size: 15px;  
    color: #002f43;  
    padding-left: 4px;  
    padding-top: 4px;  
    padding-bottom: 4px;  
}  

.fourth {  
    margin-top: 20px;  
    text-align: right;  
}  

p {  
    margin-left: 40px;  
    color: #002f43;  
}  
</style>