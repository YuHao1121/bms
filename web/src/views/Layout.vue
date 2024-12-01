<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom
} from '@element-plus/icons-vue'

import avatar from '@/assets/default.png'
// import { useTokenStore } from '@/stores/token.js';
// const tokenStore = useTokenStore();


//调用函数，获取用户详细信息
import {userInfoService} from '@/api/user.js'
import useUserInfoStore from '@/stores/userinfo.js'
import { useTokenStore } from '@/stores/token'
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();
const getUserInfo = async()=>{
    //调用接口函数
    let result = await userInfoService();
    //数据存储到pinia中
    userInfoStore.setInfo(result.data);
}
getUserInfo();

//条目被点击后，调用的函数
import {useRouter} from 'vue-router'
import { ElMessage,ElMessageBox } from 'element-plus';
const router = useRouter();

const handleCommand = (command)=>{
    //判断指令
    if(command === 'logout'){
        //推出登录
        ElMessageBox.confirm(
            '您确认要退出吗？',
            '温馨提示',
            {
                confirmButtonText: '确认',
                cancelButtonText:'取消',
                type: 'warning',
            }
        )
            .then(async()=>{
                //退出登录
                //1.清空pinia存储的token以及个人信息
                tokenStore.removeToken()
                userInfoStore.removeInfo()

                //2.跳转到登录页面
                router.push('/login')
                 
                ElMessage({
                    type: 'success',
                    message:'退出登录成功'
                })

            })
            .catch(()=>{
                ElMessage({
                    type: 'info',
                    message:'用户取消了退出登录'
                })
            })
    }else{
        //路由
        router.push('/users/'+command)
    }
}

</script>

<template>
    <!-- element-plus中的容器 -->
    <el-container class="layout-container">

        <!-- 左侧菜单 -->
        <el-aside width="200px">
            <div class="el-aside__logo"></div>

            <!-- element-plus的菜单标签 -->
            <el-menu active-text-color="#ffd04b" background-color="#A19A86"  text-color="#fff"
                router>
                <!-- index="/article/category" 需与index.js里的路由关系保持一致 -->
                <el-menu-item index="/notices" >
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>系统公告</span>
                </el-menu-item>

                <el-menu-item index="/books/category">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>图书分类</span>
                </el-menu-item>

                <el-menu-item index="/books/tag">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>图书标签</span>
                </el-menu-item>

                <el-menu-item index="/books/manage">
                    <el-icon>
                        <Promotion />
                    </el-icon>
                    <span>图书管理</span>
                </el-menu-item>

                <el-menu-item index="/comments">
                    <el-icon>
                        <Promotion />
                    </el-icon>
                    <span>读者留言</span>
                </el-menu-item>

                <el-sub-menu  >
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>个人中心</span>
                    </template> 
                    <!-- 个人中心的三个子菜单 -->
                    <el-menu-item  index="/users/info">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>基本资料</span>
                    </el-menu-item>
                    <el-menu-item index="/users/avatar">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>更换头像</span>
                    </el-menu-item>
                    <el-menu-item index="/users/resetPassword">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>重置密码</span>
                    </el-menu-item>
                </el-sub-menu>

                <el-menu-item index="/users/list" v-if="userInfoStore.info.role === 'admin'">
                    <el-icon>
                        <Promotion />
                    </el-icon>
                    <span>用户列表</span>
                </el-menu-item>

            </el-menu>

        </el-aside>

        <!-- 右侧主区域 -->
        <el-container>

            <!-- 头部区域 -->
            <el-header>
                <div>书友：<strong>{{userInfoStore.info.nickname}}</strong></div>
                <!-- command:条目被点击后悔触发，在事件函数上可以声明一个函数，接受条目对应的指令 -->
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <!-- 头像 -->
                        <el-avatar :src="userInfoStore.info.userPic?userInfoStore.info.userPic:avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            
                            <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
                            <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
                            <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>

            <!-- 中间区域 -->
            <el-main>
                <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
                    内容展示区
                </div> -->
                <router-view>
                </router-view>
            </el-main>

            <!-- 底部区域 -->
            <el-footer>BMS ©2024 Created by 唐玉亮</el-footer>
        </el-container>

    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside { 
        // #1E4D3E #0D3B66 #6E4D93 #C0A080 #E5E4E2 #A7BFD6 #6B8E23 #A19A86
        background-color: #A7BFD6;

        &__logo {
            height: 120px;
            background: url('@/assets/bmsbg1.jpg') no-repeat center / 120px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}
</style>