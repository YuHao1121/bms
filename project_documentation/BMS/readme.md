# 图书管理系统 (Library Management System)

## 一、系统概述

这是一个基于 Java Spring Boot + Vue.js 的图书管理系统项目。该系统举带了图书的增删改查、分类和标签系统、评论留言、公告管理等功能。此 README 文档将说明系统的安装、配置和使用方法。

## 二、主要特性

1. **用户管理**：支持读者和图书管理员的用户注册、登录和注销功能。
2. **图书信息管理**：支持图书的 CRUD (增加、删除、修改和查询)操作。
3. **图书分类和标签系统**：用于对图书进行分类和标记，方便用户快速搜索。
4. **图书搜索**：支持通过书名、作者、ISBN 等条件进行图书搜索。
5. **评论留言功能**：读者可以在图书详情页面评论和回复。
6. **公告管理**：管理员可以发布和管理系统公告。
7. **运行时规范控制**：对用户权限进行控制，限制读者和管理员的能力范围。

## 三、技术栈架

- **前端**: Vue.js + Element Plus
- **后端**: Spring Boot + MyBatis + JWT
- **数据库**: MySQL
- **存储**: Redis (用于数据缓存和标识签证)
- **构建工具**: Maven
- **安全**: Spring Security

## 四、环境配置

### 4.1 前端环境

- **Node.js**: 版本18.18.0
- **Vue CLI**: 安装来构建 Vue 项目

### 4.2 后端环境

- **JDK**: 21
- **Maven**: 版本 3.9.9
- **MySQL**: 版本 8.0.28
- **Redis**: 用于数据缓存

## 五、安装和部署

### 5.1 Docker 部署

1. **构建 Docker 镜像**

   在项目根目录中执行以下命令构建后端和前端的 Docker 镜像：

   ```
   docker build -t library-system-backend ./backend
   docker build -t library-system-frontend ./frontend
   ```

2. **创建网络**

   创建 Docker 网络以便容器之间可以通信：

   ```
   docker network create library-system-network
   ```

3. **运行 MySQL 和 Redis 容器**

   启动 MySQL 和 Redis 容器：

   ```
   docker run -d --name mysql-container --network library-system-network -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=library_system mysql:8.0
   docker run -d --name redis-container --network library-system-network redis
   ```

4. **运行后端容器**

   启动后端服务容器：

   ```
   docker run -d --name backend-container --network library-system-network -p 8080:8080 library-system-backend
   ```

5. **运行前端容器**

   启动前端服务容器：

   ```
   docker run -d --name frontend-container --network library-system-network -p 80:80 library-system-frontend
   ```

## 六、使用说明

### 6.1 用户注册和登录

- **注册**: 新用户可以通过首页的注册按钮进行注册。
- **登录**: 填写用户名和密码后进行登录。

### 6.2 图书管理

- **添加图书**: 由管理员登录，进入图书管理页面。
- **搜索图书**: 通过书名、作者或 ISBN 搜索库中的图书。

### 6.3 公告及留言

- **公告**: 查看最新的系统公告。
- **留言**: 读者可以在图书详情页面下留言并进行回复。

## 七、系统测试

系统包括单元测试、集成测试和系统测试。每个功能都需要进行全面的测试以确保系统的稳定性和可靠性。

## 八、问题反馈

如果在使用过程中遇到任何问题，可以联系系统管理员：

- 邮箱: admin@example.com
- 电话: 123-456-7890