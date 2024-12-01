<template>
    <div class="auto-403">
      <h1>403 - 无权限访问</h1>
      <p>抱歉，您没有权限访问此页面。</p>
      <router-link to="/" class="home-link">返回首页</router-link>
    </div>
  </template>
  
  <script>
  export default {
    name: "Auto403",
  };
  </script>
  
  <style scoped>
  .auto-403 {
    text-align: center;
    margin-top: 100px;
    font-family: Arial, sans-serif;
  }
  
  .auto-403 h1 {
    font-size: 48px;
    color: #ff4d4f;
    margin-bottom: 20px;
  }
  
  .auto-403 p {
    font-size: 18px;
    color: #606266;
    margin-bottom: 30px;
  }
  
  .auto-403 .home-link {
    display: inline-block;
    padding: 10px 20px;
    font-size: 16px;
    color: #fff;
    background-color: #409eff;
    text-decoration: none;
    border-radius: 4px;
    transition: background-color 0.3s;
  }
  
  .auto-403 .home-link:hover {
    background-color: #66b1ff;
  }
  </style>