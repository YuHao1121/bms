<script>
import MessageList from '@/components/MessageList.vue';
import MessageForm from '@/components/MessageForm.vue';
import { getCommentList,getNestedComments } from '@/api/comment.js';

export default {
    name: 'MessageBoard',
    components: {
        MessageList,
        MessageForm,
    },
    data() {
        return {
            messages: [], // 留言列表数据
            // pagination: {
            //     pageNum: 1,
            //     pageSize: 10,
            //     total: 0,
            // },
        };
    },
    methods: {
        //调用 fetchMessages() 方法获取留言数据
        // 获取留言列表
        async fetchMessages() {
            // try {
            //     const response = await getCommentList({
            //         pageNum: this.pagination.pageNum,
            //         pageSize: this.pagination.pageSize,
            //     });
            //     this.messages = response.data.items;
            //     this.pagination.total = response.data.total;
            // } catch (error) {
            //     console.error('获取留言列表失败', error);
            // }
            try {
                const response = await getNestedComments();
                this.messages = response.data;
            } catch (error) {
                console.error('加载留言列表失败', error);
            }
        },
        // // 分页切换
        // handlePageChange(page) {
        //     this.pagination.pageNum = page;
        //     this.fetchMessages();
        // },
    },
    mounted() {
        this.fetchMessages();
    },
};
</script>

<template>
    <div class="message-board">
        <!-- 页面标题 -->
        <el-page-header content="读者留言板"></el-page-header>

        <!-- 留言表单 -->
        <message-form @refresh="fetchMessages" />

        <!-- 留言列表 -->
        <message-list :messages="messages" @refresh="fetchMessages" />
    </div>
</template>

<style scoped>
.message-board {
    padding: 20px;
}
</style>