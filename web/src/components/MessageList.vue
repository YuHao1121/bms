<script>
import { deleteComment } from '@/api/comment.js';
import NestedComment from '@/components/NestedComment.vue';
export default {
    name: 'MessageList',
    components: { NestedComment },
    props: {
        messages: {
            type: Array,
            required: true,
        },
    },
    // methods: {
    //     // 删除留言
    //     async handleDelete(commentId) {
    //         try {
    //             await deleteComment(commentId);
    //             this.$emit('refresh'); // 通知父组件刷新留言列表
    //             this.$message.success('留言删除成功！');
    //         } catch (error) {
    //             console.error('删除留言失败', error);
    //         }
    //     },
    // },
    methods: {
        fetchMessages() {
            this.$emit('refresh');
        },
    },
};
</script>

<template>
    <!-- <el-card class="message-list"> -->
    <!-- 使用 v-for 遍历 messages 属性，动态生成每条留言的显示内容 -->
    <!-- <div v-for="message in messages" :key="message.commentId" class="message-item"> -->
    <!-- <el-row> -->
    <!-- <el-col :span="24"> -->
    <!-- <div class="message-header"> -->
    <!-- <span class="username">{{ message.username }}</span> -->
    <!-- <span class="time">{{ message.createTime }}</span> -->
    <!-- 删除按钮 -->
    <!-- <el-button type="danger" size="mini" @click="handleDelete(message.commentId)"> -->
    <!-- 删除 -->
    <!-- </el-button> -->
    <!-- </div> -->

    <!-- <div class="message-content"> -->
    <!-- {{ message.content }} -->
    <!-- </div> -->
    <!-- </el-col> -->
    <!-- </el-row> -->
    <!-- 每条留言之间使用 el-divider 分隔，增强可读性 -->
    <!-- <el-divider></el-divider> -->
    <!-- </div> -->
    <!-- </el-card> -->
    <div class="message-list">
        <nested-comment v-for="comment in messages" :key="comment.commentId" :comment="comment" @click="fetchMessages" />
    </div>
</template>

<!-- <style scoped>
.message-list {
    margin-top: 20px;
}

.message-item {
    padding: 10px 0;
}

.message-header {
    font-size: 14px;
    color: #606266;
    margin-bottom: 5px;
}

.username {
    font-weight: bold;
    margin-right: 10px;
}

.time {
    font-size: 12px;
    color: #909399;
}

.message-content {
    font-size: 14px;
    color: #333;
}
</style> -->
<style scoped>
.message-list {
    margin-top: 20px;
}
</style>