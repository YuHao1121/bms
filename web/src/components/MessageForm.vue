<script>
import { addComment } from '@/api/comment.js';
export default {
    name: 'MessageForm',
    data() {
        return {
            form: {
                content: '',
            },
        };
    },
    methods: {
        /*点击发布按钮时，调用 submitMessage 方法：
            1.验证留言内容是否为空。
            2.提交留言（目前为模拟提交，后续通过 axios 调用后端接口）。
            3.触发父组件的 refresh 事件，刷新留言列表。*/
        async submitMessage() {
            if (!this.form.content.trim()) {
                this.$message.error('留言内容不能为空！');
                return;
            }

            try {
                await addComment({ content: this.form.content.trim() });
                this.$emit('refresh'); // 通知父组件刷新留言列表
                this.resetForm();
                this.$message.success('留言发布成功！');
            } catch (error) {
                console.error('发布留言失败', error);
            }
        },
        //点击重置按钮时，调用 resetForm 方法清空输入框内容。
        resetForm() {
            this.form.content = '';
        },
    },
};
</script>

<template>
    <el-card class="message-form">
        <el-form ref="formRef" :model="form" label-width="80px">
            <el-form-item label="留言内容" prop="content">
                <!-- 使用 v-model 将留言内容与 form.content 双向绑定 -->
                <el-input type="textarea" v-model="form.content" placeholder="请输入留言内容" maxlength="200"
                    show-word-limit />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitMessage">发布留言</el-button>
                <el-button @click="resetForm">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<style scoped>
.message-form {
    margin-top: 20px;
}

.el-input {
    width: 100%;
}
</style>