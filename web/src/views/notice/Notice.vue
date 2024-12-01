<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { ref } from 'vue'
const notice = ref([
    {
      
    }
])

import {noticeListService,noticeAddService,noticeUpdateService,noticeDeleteService} from '@/api/notice.js'

//声明一个异步的图书标签的函数
const noticeList= async ()=>{
    let result = await noticeListService();
    notice.value = result.data;
}
noticeList();

//控制添加分类弹窗,默认为false，当点击添加分类，就显示弹窗
const dialogVisible = ref(false)

//添加分类数据模型
//模型里的属性名字跟接口里的参数名保持一致
const noticeModel = ref({
    noticeId: '',
    name: '',
    content: ''
})
//添加分类表单校验
const rules = {
    name: [
        { required: true, message: '请输入系统公告标题', trigger: 'blur' },
    ],
    content: [
        { required: true, message: '请输入系统公告内容', trigger: 'blur' },
    ]
}

import { ElMessage,ElMessageBox } from 'element-plus';
//调用接口，添加表单
const addNotice = async ()=>{
    //调用接口
    let result = await noticeAddService(noticeModel.value);
    ElMessage.success(result.msg?result.msg:'添加成功');

    //获取所有图书标签的函数
    noticeList();
    dialogVisible.value=false;
}

//定义变量，控制标题的展示
const title = ref('')


//展示编辑弹窗
const showDialog= (row) => {
    dialogVisible.value=true;title.value='编辑公告';
    //数据拷贝
    noticeModel.value.name = row.name;
    noticeModel.value.content = row.content;
    //扩展id属性，将来需要传递给后台，完成分类的修改
    noticeModel.value.noticeId = row.noticeId
}

//编辑分类
const updateNotice= async () =>{
    let result = await noticeUpdateService(noticeModel.value);
    ElMessage.success(result.msg?result.msg:'修改成功')

    //获取所有图书标签的函数
    noticeList();

    dialogVisible.value=false;
}

//清空模型的数据
const clearDialogData = ()=>{
    noticeModel.value.noticeId = ''
    noticeModel.value.name = '';
    noticeModel.value.content = '';
}

//删除分类
const deleteNotice =  (row) =>{
    //提示用户，是否删除
    ElMessageBox.confirm(
    '你确定要删除该系统公告吗?',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then( async () => {
        //调用接口
        let result = await noticeDeleteService(row.noticeId);

      ElMessage({
        type: 'success',
        message: '删除成功',
      })
      //刷新列表
      noticeList();
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '管理员取消了删除',
      })
    }) 
}

//用于权限控制
import useUserInfoStore from '@/stores/userinfo.js';
const userInfoStore = useUserInfoStore();
const userInfo = ref({...userInfoStore.info})

</script>
<template>
    <el-card class="page-container">

        <template #header>
            <div class="header">
                <span>系统公告</span>
                <div class="extra">
                    <el-button v-if="userInfo.role === 'admin'" type="primary" @click="dialogVisible=true;title='添加公告';clearDialogData()" >添加公告</el-button>
                </div>
            </div>
        </template>

        <el-table :data="notice" style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="公告标题" prop="name"></el-table-column>
            <el-table-column label="公告内容" prop="content"></el-table-column>
            <el-table-column label="发布时间" prop="createTime"></el-table-column>
            <el-table-column label="操作" width="100" v-if="userInfo.role === 'admin'">
                <!-- 按钮 -->
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)" ></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteNotice(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <!-- 添加分类弹窗 -->
        <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form :model="noticeModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="公告id" prop="noticeId">
                    <el-input v-model="noticeModel.tagId" disabled minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="公告标题" prop="name">
                    <el-input v-model="noticeModel.name" minlength="1" maxlength="255"></el-input>
                </el-form-item>
                <el-form-item label="公告内容" prop="content">
                    <el-input v-model="noticeModel.content" minlength="1" ></el-input>
                </el-form-item>

            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>

                    <el-button type="primary" @click="title=='添加公告'?addNotice():updateNotice()" > 确认 </el-button>
                </span>
            </template>
        </el-dialog>

    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}


</style>