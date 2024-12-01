//调用后台接口，发送异步请求，所以导入request.js请求工具
import request from '@/utils/request.js'

//系统公告列表查询
export const noticeListService = ()=>{
    return request.get('/notices/list');
}

//系统公告添加接口
export const noticeAddService = (noticeData) =>{
    return request.post('/notices/save',noticeData)
}

//系统公告修改
export const noticeUpdateService = (noticeData) =>{
    return request.put('/notices/update',noticeData)
}

//系统公告删除
export const noticeDeleteService = (noticeId) =>{
    return request.delete('/notices/delete?noticeId='+noticeId)
}