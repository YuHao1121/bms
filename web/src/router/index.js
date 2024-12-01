import {createRouter,createWebHistory} from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import BookCategoryVue from '@/views/book/BookCategory.vue'
import BookManageVue from '@/views/book/BookManage.vue'
import BookTagVue from '@/views/book/BookTag.vue'
import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'
import NoticesVue from '@/views/notice/Notice.vue'
import CommentVue from '@/views/comment/Comment.vue';
import UsersListVue from '@/views/admin/UserList.vue';





//定义路由关系
const routes = [
    {path:'/login',component:()=>import('../views/Login.vue')},
    {path:'/',component:LayoutVue,redirect:'/notices',children:[
        {path:'/notices',component:NoticesVue},
        {path:'/books/category',component:BookCategoryVue},
        {path:'/books/tag',component:BookTagVue},
        {path:'/books/manage',component:BookManageVue},
        {path: '/comments',component:CommentVue},
        {path:'/users/avatar',component:UserAvatarVue},
        {path:'/users/info',component:UserInfoVue},
        {path:'/users/resetPassword',component:UserResetPasswordVue},
        {path: '/users/list',component:UsersListVue},
        {path: '/403',component: () => import('../views/auto/Auto403.vue') }
        
    ]},
    { path: '/404', component: () => import('../views/auto/Auto404.vue') }, // 404 页面
  { path: '/:catchAll(.*)', redirect: '/404' }
]

//创建路由器
const router = createRouter({
    history:createWebHistory(),
    routes:routes
})


//路由守卫
//to  是到达的路由信息
//from 是开源的路由信息
//next 是帮助我们跳转的函数
router.beforeEach((to, from, next) => {
    const userInfoString = localStorage.getItem('pinia-userInfo');
  
    if (userInfoString) {
      const userInfo = JSON.parse(userInfoString);
      const adminPaths = ['/users/list'];
  
      if (adminPaths.includes(to.path) && userInfo.info.role !== 'admin') {
        next('/403'); // 跳转到 403 页面
        return;
      }
    } else if (to.path !== '/login') {
      next('/login'); // 未登录用户跳转到登录页
      return;
    }
  
    next(); // 放行
  });



//导出路由
export default router