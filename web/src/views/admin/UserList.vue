<script>
import { ref, onMounted } from 'vue';
import { getUserListService } from '@/api/user.js';
import { Edit, Delete } from '@element-plus/icons-vue';

export default {
  name: 'UserList',
  setup() {
    const usersInfoList = ref([]); // 用户列表数据
    const pageNum = ref(1); // 当前页码
    const pageSize = ref(10); // 每页大小
    const total = ref(0); // 总记录数

    // 获取用户列表
    const getUserList = async () => {
      try {
        const params = { 
            pageNum: pageNum.value, 
            pageSize: pageSize.value 
        };
        const result = await getUserListService(params);
        if (result.data) {
          total.value = result.data.total;
          usersInfoList.value = result.data.items;
        }
      } catch (error) {
        console.error('获取用户列表失败', error);
      }
    };

    // 分页事件
    const onSizeChange = (size) => {
      pageSize.value = size;
      getUserList();
    };

    const onCurrentChange = (num) => {
      pageNum.value = num;
      getUserList();
    };

    // 页面加载时获取数据
    onMounted(() => {
      getUserList();
    });

    return {
      usersInfoList,
      pageNum,
      pageSize,
      total,
      onSizeChange,
      onCurrentChange,
      Edit,
      Delete,
    };
  },
};
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>用户列表</span>
      </div>
    </template>

    <!-- 用户列表表格 -->
    <el-table :data="usersInfoList" style="width: 100%">
      <el-table-column prop="id" label="用户ID" width="120"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="nickname" label="昵称"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="role" label="角色"></el-table-column>
      <el-table-column prop="userPic" label="头像" width="100">
        <template #default="scope">
          <img
            :src="scope.row.userPic || 'https://via.placeholder.com/100'"
            alt="头像"
            style="width: 50px; height: 50px; border-radius: 50%"
          />
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column prop="updateTime" label="更新时间"></el-table-column>
      
    </el-table>

    <!-- 分页 -->
    <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      :total="total"
      :page-sizes="[5, 10, 15, 20]"
      layout="sizes, prev, pager, next, jumper, total"
      background
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px"
    />
  </el-card>
</template>

<style scoped>
.page-container {
  margin: 20px;
}

.header {
  font-size: 18px;
  font-weight: bold;
}

.avatar {
  border-radius: 50%;
  width: 40px;
  height: 40px;
}
</style>
