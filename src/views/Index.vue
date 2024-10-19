<template>
  <div class="common-layout">
    <el-container>
        <el-header>
            <el-row :gutter="20">
                <el-col :span="2">
                    <img src="../assets/kon.png" class="logo" alt="交易平台" />
                </el-col>
                <el-col :span="18" class="col1">
                    校园二手书籍交易平台
                </el-col>
                <el-col :span="4">
                  <el-dropdown>
                    <span class="el-dropdown-link" >
                        <img class="user" :src="getImgSrc()" alt="头像" />
                        <!-- 动态绑定用户名 -->
                        <span class="user">用户</span>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                        <el-dropdown-item @click="change">修改密码</el-dropdown-item>
                        <el-dropdown-item>更多</el-dropdown-item>
                        <el-dropdown-item divided @click="Logout">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </el-col>
            </el-row>
        </el-header>

      <el-container>
            <el-aside :width="menuWidth" style="height: 100vh+1px;">
                <Menu></Menu>
            </el-aside>

            <el-main>
                <NaviHead></NaviHead>
                <router-view v-slot="{Component}">
                    <keep-alive>
                        <component :is="Component" />
                    </keep-alive>
                </router-view>
            </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import Menu from "~/components/Menu.vue";
import NaviHead from "~/components/NaviHead.vue";
import{ ref, onMounted, watch } from 'vue';
import PubSub from 'pubsub-js';
import router from "~/router";
import { ElMessage, ElMessageBox } from "element-plus";
import { useUserStore } from "~/store/user";
const userStore = useUserStore();
const img = ref(userStore.image);  

const getImgSrc = () => {  
    // console.log("当前图片URL:", img.value); // 添加调试信息  
    if (img.value === null || img.value === '')  
        return new URL("~/assets/0002.jpg", import.meta.url).href;  
    else return img.value;  
};

// 监测头像的变化  
watch(() => userStore.image, (newValue) => {  
    img.value = newValue;  
});

const menuWidth = ref("200px");
onMounted(()=>{
    //消费者 订阅消息
    PubSub.subscribe('menuopenorclose', (topic: String, data: any) => {
        menuWidth.value = data ==true ? "64px":"200px"
    })
})


const Logout = ()=> {
    ElMessageBox.confirm('确认退出吗, 是否继续?', '确认退出', {
      confirmButtonText: '退出',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async ()=>{
        await userStore.LogOut()
        .then(res=>{
            if(res.code === 1) {
                router.push('/login')
            } else {
                ElMessage.error("退出失败")
            }
        })
        .catch(error=>{
            ElMessage.error("退出失败："+error.message)
        })
    })
}

const change = () =>{
    router.push({name: 'change'})
}

</script>

<style lang="less" scoped>
  .el-row {
  align-items: center;
  vertical-align: middle;
  padding-top: 10px;
}
  .el-header {
  background-color: #a1e8ca;
}
.logo {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
}
.col1 {
    color: white;
}
.userinfo {
  text-align: right;
}
 
.el-dropdown-link {
  color: white;
  .user {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        margin-right: 10px;
        // line-height: 40px;
        vertical-align:middle;
    }
}

.el-main {
    background-color: #d5ebe1;
}
</style>









<!-- <style lang="less" scoped>
.common-layout {
  height: 100%;
  &>.el-container {
      height: 100%;
      &>.el-aside {
          height: 100%;
          background: #545c64;
      }
      &>.el-container {
          &>.el-header {
              padding: 0%;
          }
      }
  }
}
</style> -->
