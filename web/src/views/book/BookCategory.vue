<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
const categorys = ref([
    {
        
    }
])

import {booksCategoryListService,booksCategoryAddService,booksCategoryUpdateService,booksCategoryDeleteService} from '@/api/books.js'

//声明一个异步的函数
const booksCategoryList= async ()=>{
    let result = await booksCategoryListService();
    categorys.value = result.data;
}
booksCategoryList();

//控制添加分类弹窗,默认为false，当点击添加分类，就显示弹窗
const dialogVisible = ref(false)

//添加分类数据模型
//模型里的属性名字跟接口里的参数名保持一致
const categoryModel = ref({
    categoryId: '',
    name: ''
})
//添加分类表单校验
const rules = {
    name: [
        { required: true, message: '请输入图书分类名称', trigger: 'blur' },
    ]
}

import { ElMessage,ElMessageBox } from 'element-plus';
//调用接口，添加表单
const addCategory = async ()=>{
    //调用接口
    let result = await booksCategoryAddService(categoryModel.value);
    ElMessage.success(result.msg?result.msg:'添加成功');

    //获取所有文章分类的函数
    booksCategoryList();
    dialogVisible.value=false;
}

//定义变量，控制标题的展示
const title = ref('')


//展示编辑弹窗
const showDialog= (row) => {
    dialogVisible.value=true;title.value='编辑分类';
    //数据拷贝
    categoryModel.value.name = row.name;
    //扩展id属性，将来需要传递给后台，完成分类的修改
    categoryModel.value.categoryId = row.categoryId
}

//编辑分类
const updateCategory= async () =>{
    let result = await booksCategoryUpdateService(categoryModel.value);
    ElMessage.success(result.msg?result.msg:'修改成功')

    //获取所有文章分类的函数
    booksCategoryList();

    dialogVisible.value=false;
}

//清空模型的数据
const clearDialogData = ()=>{
    categoryModel.value.categoryId = ''
    categoryModel.value.name = '';

}

//删除分类
const deleteCategory =  (row) =>{
    //提示用户，是否删除
    ElMessageBox.confirm(
    '你确定要删除该分类信息吗?',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then( async () => {
        //调用接口
        let result = await booksCategoryDeleteService(row.categoryId);

      ElMessage({
        type: 'success',
        message: '删除成功',
      })
      //刷新列表
      booksCategoryList();
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '用户取消了删除',
      })
    })  
}

//用于权限控制，拿role数据用v-if进行对比
import useUserInfoStore from '@/stores/userinfo.js';
const userInfoStore = useUserInfoStore();
const userInfo = ref({...userInfoStore.info})
</script>
<template>
    <el-card class="page-container">

        <template #header>
            <div class="header">
                <span>图书分类</span>
                <div class="extra">
                    <el-button v-if="userInfo.role === 'admin'" type="primary" @click="dialogVisible=true;title='添加分类';clearDialogData()" >添加分类</el-button>
                </div>
            </div>
        </template>

        <el-table :data="categorys" style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="图书分类名称" prop="name"></el-table-column>
            <el-table-column v-if="userInfo.role === 'admin'" label="操作" width="100">
                <!-- 按钮 -->
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)" ></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCategory(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <!-- 添加分类弹窗 -->
        <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="分类id" prop="categoryId">
                    <el-input v-model="categoryModel.categoryId" disabled minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="分类名称" prop="name">
                    <el-input v-model="categoryModel.name" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>

                    <el-button type="primary" @click="title=='添加分类'?addCategory():updateCategory()" > 确认 </el-button>
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