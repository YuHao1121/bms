<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { ref } from 'vue'

//文章分类数据模型
const categorys = ref([])

//用户搜索时选中的图书名称title
const title=ref('')

//用户搜索时选中的图书作者author
const author=ref('')
//用户搜索时选中的图书编号isbn
const isbn=ref('')
// //用户搜索时选中的图书分类category
// const categoryName=ref('')
// //用户搜索时选中的图书标签tag
// const tagName=ref('')

//文章列表数据模型
const books = ref([])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(100)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    booksList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    booksList();
}

//调用后台接口，显示图书分类,回显图书分类
import { booksCategoryListService,booksListService,bookAddService,bookUpdateService,bookDeleteService} from '@/api/books.js'
const booksCategoryList = async () => {
    let result = await booksCategoryListService();
    categorys.value = result.data;
}
booksCategoryList();

//获取文章列表数据
const booksList = async () =>{
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        title: title.value?title.value:null,
        author: author.value?author.value:null,
        isbn: isbn.value?isbn.value:null
        // categoryName: categoryName.value?categoryName.value:null,
        // tagName: tagName.value?tagName.value:null
        
    }
    let result = await booksListService(params);

    //渲染视图
    total.value = result.data.total;
    books.value = result.data.items;

    //由于不应该显示分类的id，而是分类的名称，所以需要处理数据，给数据模型扩展一个属性分类名称name
    for(let i = 0;i<books.value.length;i++){
        let book = books.value[i];
        for(let j=0;j<books.value.length;j++){
            if(book.categoryId == categorys.value[j].categoryId){
                book.categoryName = categorys.value[j].name
            }
        }
    }

}
booksList();

//添加图书
import {Plus} from '@element-plus/icons-vue'
//抽屉quill第三方组件（没用上）
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const bookModel = ref({
    title: '',
    author: '',
    isbn: '',
    publishDate: '',
    categoryId: '',
    coverImg: '',
    description: ''
})

//导入token
import { useTokenStore } from '@/stores/token.js';
import { ElMessage,ElMessageBox} from 'element-plus';
const tokenStore = useTokenStore();

//上传成功的回调函数
const uploadSuccess = (result) =>{
    bookModel.value.coverImg = result.data;
}

const addBook = async () =>{
    //调用接口
    let result = await bookAddService(bookModel.value);

    ElMessage.success(result.msg?result.msg:'添加成功')

    //让抽屉消失
    visibleDrawer.value = false;

    //刷新图书列表
    booksList();
}

//定义变量，控制标题的展示
const dialogtitle = ref('')

//展示编辑弹窗
const showDialog= (row) => {
    visibleDrawer.value=true;
    dialogtitle.value='编辑图书';
    //数据拷贝
    bookModel.value.title = row.title;
    bookModel.value.author = row.author;
    bookModel.value.isbn = row.isbn;
    bookModel.value.publishDate = row.publishDate;
    bookModel.value.categoryId = row.categoryId;
    bookModel.value.coverImg = row.coverImg;
    bookModel.value.description = row.description;
    //扩展id属性，将来需要传递给后台，完成图书的修改
    bookModel.value.bookId = row.bookId
}

//编辑图书
const updateBook= async () =>{
    let result = await bookUpdateService(bookModel.value);
    ElMessage.success(result.msg?result.msg:'修改成功')

    //获取所有文章分类的函数
    booksList();

    visibleDrawer.value=false;
}

//清空模型的数据
const clearDrawerData = ()=>{
    bookModel.value.title = '' 
    bookModel.value.author = ''
    bookModel.value.isbn = ''
    bookModel.value.publishDate = ''
    bookModel.value.categoryId = ''
    bookModel.value.coverImg = ''
    bookModel.value.description = '';
}

//删除图书
const deleteBook =  (row) =>{
    //提示用户，是否删除
    ElMessageBox.confirm(
    '你确定要删除该图书吗?',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then( async () => {
        //调用接口
        let result = await bookDeleteService(row.bookId);

      ElMessage({
        type: 'success',
        message: '删除成功',
      })
      //刷新列表
      booksList();
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
                <span>图书管理</span>
                <div class="extra">
                    <el-button v-if="userInfo.role === 'admin'" type="primary" @click="visibleDrawer=true ;dialogtitle='添加图书';clearDrawerData()" >添加图书</el-button>
                </div>
            </div>
        </template>

        <!-- 这里还需要改 -->
        <!-- 搜索表单 -->
        <el-form :inline="true"  class="demo-form-inline">
            <el-form-item  label="图书名称：" >
                <el-input v-model="title" placeholder="请输入书名" clearable />
            </el-form-item>

            <el-form-item  label="作者名称：">
                <el-input v-model="author" placeholder="请输入作者名字" clearable />
            </el-form-item>

            <el-form-item label="ISBN:">
                <el-input v-model="isbn"  placeholder="请输入ISBN" clearable />
            </el-form-item>

            <el-form-item label="图书分类：">
                <el-input  v-model="categoryName" placeholder="请输入图书分类" clearable />
            </el-form-item>

            <el-form-item label="图书标签：">
                <el-input  placeholder="请输入图书标签" clearable />
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="booksList">搜索</el-button>
                <el-button @click="title='';author='';isbn='';categoryName='';tagName='';booksList()">重置</el-button>
            </el-form-item>
        </el-form>

        <!-- 图书列表 -->
        <el-table :data="books" style="width: 100%">
            <el-table-column label="图书名称" width="200" prop="title"></el-table-column>
            <el-table-column label="作者" prop="author"></el-table-column>
            <el-table-column label="ISBN编号" prop="isbn"> </el-table-column>
            <el-table-column label="出版日期" prop="publishDate"></el-table-column>
            <el-table-column label="分类" prop="categoryName"></el-table-column>
            <!-- <el-table-column label="标签" prop="categoryTag"></el-table-column> -->
            <el-table-column label="图书封面" prop="coverImg" width="150">
                <template #default="scope">
                    <img 
                    :src="scope.row.coverImg|| 'https://tse1-mm.cn.bing.net/th/id/OIP-C.sMODYwSPKYD3o9G0UNrDQwHaIk?w=162&h=188&c=7&r=0&o=5&dpr=1.5&pid=1.7'" 
                    alt="图书封面" 
                    style="width: 100px; height: 150px; object-fit: cover; border-radius: 4px;" 
                    />
                </template>
            </el-table-column>
            <el-table-column label="图书简介" prop="description"></el-table-column>
           
            <el-table-column v-if="userInfo.role === 'admin'" label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteBook(row)"></el-button>
                </template>
            </el-table-column>

            <template #empty>
                <el-empty description="没有数据" />
            </template>

        </el-table>

        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5 ,10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />


         <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" :title="dialogtitle" direction="rtl" size="50%">
            <!-- 添加图书表单 -->
            <el-form :model="bookModel" label-width="100px" >

                <el-form-item label="图书名称" >
                    <el-input v-model="bookModel.title" placeholder="请输入书名"></el-input>
                </el-form-item>

                <el-form-item label="作者" >
                    <el-input v-model="bookModel.author" placeholder="请输入作者"></el-input>
                </el-form-item>

                <el-form-item label="ISBN" >
                    <el-input v-model="bookModel.isbn" placeholder="请输入isbn"></el-input>
                </el-form-item>

                <el-form-item label="出版日期" >
                    <el-input v-model="bookModel.publishDate" placeholder="请输入出版日期"></el-input>
                </el-form-item>

                <el-form-item label="图书分类">
                    <el-select placeholder="请选择" v-model="bookModel.categoryId">
                        <el-option v-for="c in categorys" :key="c.categoryId" :label="c.name" :value="c.categoryId">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="图书封面">
                    <!-- 
                    auto-upload 设置是否自动上传
                    action 设置服务器接口路径
                    name     设置上传的文件字段名
                    headers     设置上传的请求头
                    on—success  设置上传成功的回调函数

                    -->
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"
                        action="/api/upload"
                        name="file"
                        :headers="{'Authorization':tokenStore.token}"
                        :on-success="uploadSuccess">
                        <img v-if="bookModel.coverImg" :src="bookModel.coverImg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>

                <el-form-item label="图书简介">
                    <!-- <div class="editor">
                        <quill-editor
                            theme="snow"
                            v-model:description="bookModel.description"
                            contentType="html"
                            >
                        </quill-editor>
                    </div> -->
                    <el-input class="editor" v-model="bookModel.description" placeholder="请输入简介"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="dialogtitle=='添加图书'?addBook():updateBook()">发布</el-button>

                </el-form-item>
            </el-form>
        </el-drawer>   
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

    // .demo-form-inline .el-select {
    //     --el-select-width: 250px;
    // }

    .demo-form-inline .el-input {
        --el-input-width: 250px;
    }
}

/* 抽屉样式 */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}


</style>