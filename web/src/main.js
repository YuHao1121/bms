import './assets/main.scss'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'//导入element-plus
import 'element-plus/dist/index.css' //导入element-plus样式
import App from './App.vue'
import router from '@/router'
import {createPinia} from 'pinia'
import {createPersistedState} from 'pinia-persistedstate-plugin';
import locale from 'element-plus/dist/locale/zh-cn.js'


const app = createApp(App);
const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist)
app.use(pinia);
app.use(router)
app.use(ElementPlus,{locale})
app.mount('#app')






