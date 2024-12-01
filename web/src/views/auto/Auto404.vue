<template>
    <div class="auto-404">
      <h1>404 页面不存在</h1>
      <p>您访问的页面不存在，请检查路径。</p>
      <router-link to="/">返回首页</router-link>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Auto404',
  };
  </script>
  
  <style scoped>
  .auto-404 {
    text-align: center;
    margin-top: 100px;
  }
  </style>