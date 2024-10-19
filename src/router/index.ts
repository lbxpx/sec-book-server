//引入路由对象
import { createRouter, createWebHashHistory } from 'vue-router'

// 每个路由都需要映射到一个组件。
const routes = [
    {
        path: '/',
        redirect: '/login',
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('~/views/Login.vue')
    },
    {
        path: '/Register',
        name: 'Register',
        component: () => import('~/views/Register.vue')
    },
    {
        path: '/Index',
        name:'/Index',
        component:() => import('~/views/Index.vue'),
        children: [
            {
                path: 'home',
                name: 'home',
                meta: { title: '首页', icon: 'HomeFilled', rag: 1 }, 
                component: () => import('~/views/Home.vue'),
            },
            {
                path: 'usersControll',
                name: 'users',
                meta: { title: '用户管理', icon: 'Avatar', rag: 1 },
                component: () => import('~/views/UsersControll.vue'),
            },
            
            {
                path: 'pim',
                name: 'pim',
                meta: { title: '个人信息', icon: 'User', rag : 1 },
                // commponent: () => import('~views/PIM/Index.vue'),
                children: [
                    {
                        path: 'user',
                        name: 'user',
                        meta: { title: '信息查看', icon: 'User', rag: 2 },
                        component: () => import('~/views/PIM/UserMessage.vue'),
                    },
                    {
                        path: 'change',
                        name: 'change',
                        meta: { title: '信息修改', icon: 'User', rag: 2 },
                        component: () => import('~/views/PIM/ChangeMessage.vue'),
                        
                    }
                ]
            },
            {
                path: 'order-list',
                name: 'order-list',
                meta: { title: '订单列表', icon: 'List', rag: 1 }, 
                component: () => import("~/views/order/List.vue")
            },
           
            {
                path: 'product',
                name: 'product',
                component: () => import("~/views/product/Index.vue"),
                meta: { title: '商品管理', icon: 'Shop', rag: 1 },
                children: [
                    {
                        path: 'product-list',
                        name: 'product-list',
                        meta: { title: '商品列表', icon: 'List', rag: 2 },
                        component: () => import("~/views/product/List.vue"),
                    },
                    {
                        path: 'product-index',
                        name: 'product-index', 
                        component: () => import("~/views/product/Index.vue"),
                    },
                    {
                        path: 'product-package',
                        name: 'product-package',
                        meta: { title: '套餐管理', icon: 'CirclePlusFilled', rag: 2 },  
                        component: () => import("~/views/product/Package.vue"),
                    }
                ]
            },
        ]
    },
    
    {
        path: '/404',
        name: '404',
        component: () => import('~/views/404.vue')
    },
    // {
    //     path: '/editor',
    //     name: 'editor',
    //     component: () => import("~/views/Editor.vue"),
    //     meta: { title: "富文本",icon: "Setting" }
    // },
    {
        path: '/:pathMatch(.*)*',//解决进入路由守卫后未找到对应的路由记录问题
        redirect: '/404'
    }
]
 
const router = createRouter({
    history: createWebHashHistory(), // 修正函数名
    routes
})
 
//导出router
export default router;


