//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import { ElMessage } from 'element-plus'


//定义一个变量,记录公共的前缀  ,  baseURL
// const baseURL = 'http://localhost:8080';

const baseURL = '/api';//这里相当于访问http://localhost:5173/api
//配置代理在vite.config.js


const instance = axios.create({baseURL})

// import {useRouter} from 'vue-router';
// const router = useRouter();

import router from '@/router'

import { useTokenStore } from '@/stores/token.js';


//添加请求拦截器
instance.interceptors.request.use(
    (config)=>{
        //请求前的回调
        //添加token
        const tokenStore = useTokenStore();

        //判断有没有token
        if(tokenStore.token){
            config.headers.Authorization = tokenStore.token
        }
        return config;
    },
    (err)=>{
        //请求错误的回调
        Promise.reject(err)
    }
)

//添加响应拦截器
instance.interceptors.response.use(
    result=>{

        //判断业务状态码，为0就代表本次操作成功
        if(result.data.code === 0){
            return result.data;
        }

        //操作失败
        // alert(result.data.msg?result.data:'服务异常')
        ElMessage.error(result.data.message?result.data:'服务异常')
        //异步操作的状态转换失败
        return Promise.reject(result.data);
    },
    err=>{
        //判断响应状态码，如果为401，则证明未登录，提示登录信息，并跳转到登录页面   
        if(err.response.status===401){
            ElMessage.error('请先登录')
            router.push('/login')
        }else{
            ElMessage.error('服务异常')
        }
        // alert('服务异常');
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;