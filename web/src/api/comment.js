//调用后台接口，发送异步请求，所以导入request.js请求工具
import request from '@/utils/request.js'

// 查询留言列表
export const getCommentList = (params) => {
    return request.get('/comments/list', { params });
  };
  
  // 发布留言
  export const addComment = (commentData) => {
    return request.post('/comments/add', commentData);
  };
  
  // 删除留言
  export const deleteComment = (commentId) => {
    return request.delete(`/comments/delete/${commentId}`);
  };
  
  // 回复留言
  export const replyComment = (replyData) => {
    return request.post('/comments/reply', replyData);
  };

  //获取嵌套留言列表
  export const getNestedComments = (params) => {
    return request.get('/comments/nested-list', { params });
  };