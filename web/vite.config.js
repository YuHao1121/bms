import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server:{
    proxy:{

      '/api':{//获取路径中包含了/api的请求
        target:'http://localhost:8080',
        changeOrigin:true,//要不要更换源，若换，则换为target
        //路径重写
        rewrite:(path)=>path.replace(/^\/api/,'')


        //例如http://localhost:5173/api/users/register
        //1.changeOrigin:true  换为http://localhost:8080/api/users/register
        //2.rewrite:(path)=>path.replace(/^\/api/,'') 路径重写为http://localhost:8080/users/register
      }
    }
  }
})

//
