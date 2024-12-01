<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
const tags = ref([
    {
      
    }
])

import {booksTagListService,booksTagAddService,booksTagUpdateService,booksTagDeleteService} from '@/api/books.js'

//声明一个异步的图书标签的函数
const booksTagList= async ()=>{
    let result = await booksTagListService();
    tags.value = result.data;
}
booksTagList();

//控制添加分类弹窗,默认为false，当点击添加分类，就显示弹窗
const dialogVisible = ref(false)

//添加分类数据模型
//模型里的属性名字跟接口里的参数名保持一致
const tagModel = ref({
    tagId: '',
    name: ''
})
//添加分类表单校验
const rules = {
    name: [
        { required: true, message: '请输入图书标签名称', trigger: 'blur' },
    ]
}

import { ElMessage,ElMessageBox } from 'element-plus';
//调用接口，添加表单
const addTag = async ()=>{
    //调用接口
    let result = await booksTagAddService(tagModel.value);
    ElMessage.success(result.msg?result.msg:'添加成功');

    //获取所有图书标签的函数
    booksTagList();
    dialogVisible.value=false;
}

//定义变量，控制标题的展示
const title = ref('')


//展示编辑弹窗
const showDialog= (row) => {
    dialogVisible.value=true;title.value='编辑标签';
    //数据拷贝
    tagModel.value.name = row.name;
    //扩展id属性，将来需要传递给后台，完成分类的修改
    tagModel.value.tagId = row.tagId
}

//编辑分类
const updateTag= async () =>{
    let result = await booksTagUpdateService(tagModel.value);
    ElMessage.success(result.msg?result.msg:'修改成功')

    //获取所有图书标签的函数
    booksTagList();

    dialogVisible.value=false;
}

//清空模型的数据
const clearDialogData = ()=>{
    tagModel.value.tagId = ''
    tagModel.value.name = '';

}

//删除分类
const deleteTag =  (row) =>{
    //提示用户，是否删除
    ElMessageBox.confirm(
    '你确定要删除该标签信息吗?',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then( async () => {
        //调用接口
        let result = await booksTagDeleteService(row.tagId);

      ElMessage({
        type: 'success',
        message: '删除成功',
      })
      //刷新列表
      booksTagList();
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '用户取消了删除',
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
                <span>图书标签</span>
                <div class="extra">
                    <el-button v-if="userInfo.role === 'admin'" type="primary" @click="dialogVisible=true;title='添加标签';clearDialogData()" >添加标签</el-button>
                </div>
            </div>
        </template>

        <el-table :data="tags" style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="图书标签名称" prop="name"></el-table-column>
            <el-table-column v-if="userInfo.role === 'admin'" label="操作" width="100">
                <!-- 按钮 -->
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)" ></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteTag(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <!-- 添加分类弹窗 -->
        <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form :model="tagModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="标签id" prop="tagId">
                    <el-input v-model="tagModel.tagId" disabled minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="标签名称" prop="name">
                    <el-input v-model="tagModel.name" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>

                    <el-button type="primary" @click="title=='添加标签'?addTag():updateTag()" > 确认 </el-button>
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