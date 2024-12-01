<script>
import { replyComment,deleteComment } from '@/api/comment.js';

export default {
    name: 'NestedComment',
    props: {
        comment: {
            type: Object,
            required: true,
        },
    },
    data() {
        return {
            showReplyForm: false,
            replyContent: '',
        };
    },
    methods: {
        // 切换回复表单显示
        toggleReplyForm() {
            this.showReplyForm = !this.showReplyForm;
            this.$emit('refresh');
        },
        // 提交回复
        async submitReply() {
            if (!this.replyContent.trim()) {
                this.$message.error('回复内容不能为空！');
                return;
            }

            try {
                await replyComment({
                    parentId: this.comment.commentId,
                    content: this.replyContent.trim(),
                });
                this.$emit('refresh'); // 通知父组件刷新列表
                this.replyContent = '';
                this.showReplyForm = false;
                this.$message.success('回复成功！');
            } catch (error) {
                console.error('回复失败', error);
            }
        },
        // 删除留言
        async handleDelete() {
            try {
                await deleteComment(this.comment.commentId); // 调用后端接口删除留言
                this.$emit('refresh'); // 通知父组件刷新留言列表
                this.$message.success('留言删除成功！');
            } catch (error) {
                // this.$message.error('删除失败，请重试！');
            }
        },
        // 刷新列表
        fetchMessages() {
            this.$emit('refresh');
        },
    },
};
</script>

<template>
    <el-card class="nested-comment">
        <div class="comment-item">
            <div class="comment-header">
                <span class="username">{{ comment.username }}</span>
                <span class="time">{{ comment.createTime }}</span>
                <el-button type="text" size="mini" @click="toggleReplyForm">回复</el-button>
                <!-- 删除按钮 -->
                <el-button type="danger" size="mini" @click="handleDelete">删除</el-button>
            </div>
            <div class="comment-content">{{ comment.content }}</div>

            <!-- 回复表单 -->
            <el-form v-if="showReplyForm" inline class="reply-form">
                <el-input type="textarea" v-model="replyContent" placeholder="请输入回复内容" size="small"
                    style="width: 300px" />
                <el-button type="primary" size="small" @click="submitReply">提交</el-button>
                <el-button type="text" size="small" @click="toggleReplyForm">取消</el-button>
            </el-form>

            <!-- 子回复 -->
            <div v-if="comment.children && comment.children.length" class="nested-children">
                <nested-comment v-for="child in comment.children" :key="child.commentId" :comment="child"
                    @click="fetchMessages" />
            </div>
        </div>
    </el-card>
</template>

<style scoped>
.nested-comment {
    margin-top: 10px;
}

.comment-item {
    padding: 10px;
}

.comment-header {
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

.comment-content {
    font-size: 14px;
    color: #333;
    margin-bottom: 10px;
}

.reply-form {
    margin-top: 10px;
}

.nested-children {
    margin-left: 20px;
}
</style>