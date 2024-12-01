import request from '@/utils/request.js';
import { useTokenStore } from '@/stores/token.js';

//图书分类列表查询
export const booksCategoryListService = ()=>{
    //const tokenStore = useTokenStore();

    //在pinia中定义的响应式数据，都不需要.value，直接使用
    //return request.get('/categories/categoryInfo',{headers:{Authorization:tokenStore.token}});

    return request.get('/categories/categoryInfo');
}

//图书分类添加接口
export const booksCategoryAddService=(categoryData)=>{
    return request.post('/categories/addCategory',categoryData);
}


//图书分类修改
export const booksCategoryUpdateService = (categoryData) =>{
    return request.put('/categories/update',categoryData);
}

//图书分类删除
export const booksCategoryDeleteService = (categoryId) =>{
    return request.delete('/categories/delete?categoryId='+categoryId)
}

//图书标签列表查询
export const booksTagListService = ()=>{
    return request.get('/tags/tagInfo');
}

//图书标签添加接口
export const booksTagAddService=(tagData)=>{
    return request.post('/tags/addTag',tagData);
}

//图书标签修改
export const booksTagUpdateService = (tagData) =>{
    return request.put('/tags/update',tagData);
}
//图书标签删除
export const booksTagDeleteService = (tagId) =>{
    return request.delete('/tags/delete?tagId='+tagId)
}



//图书搜索功能（按标题、作者、ISBN）
export const booksListService = (params) =>{
    return request.get('/books/search',{params:params});
}

//图书添加
export const bookAddService=(bookDate)=>{   

    return request.post('/books/insertBook',bookDate);
}

//图书修改
export const bookUpdateService = (bookData) =>{
    return request.put('/books/update',bookData);
}

//图书删除
export const bookDeleteService = (bookId) =>{
    return request.delete('/books/delete?bookId='+bookId)
}