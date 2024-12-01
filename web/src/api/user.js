//调用后台接口，发送异步请求，所以导入request.js请求工具
import request from '@/utils/request.js'

//提供调用注册接口的函数
export const userRegisterService = (registerData)=>{
    //参数不能直接传，借助于UrlSearchParams完成传递
    const params = new URLSearchParams()
    for(let key in registerData){
        params.append(key,registerData[key]);
    }
    return request.post('/users/register',params);
}

//提供调用登录接口的函数
export const userLoginService = (LoginData)=>{
    //借助于UrlSearchParams完成传递
    const params = new URLSearchParams()
    for(let key in LoginData){
        params.append(key,LoginData[key]);
    }
    return request.post('/users/login',params)
}

//获取用户详细信息
export const userInfoService = ()=>{
    return request.get('users/userInfo')
}

//修改个人信息
export const userInfoUpdateService =(userInfoData)=>{
    return request.put('/users/update',userInfoData)
}

//修改头像
export const UserAvatarUpdateService = (avatarUrl)=>{
    //借助于UrlSearchParams完成传递
    const params = new URLSearchParams()
    params.append('avatarUrl',avatarUrl)
    
    return request.patch('/users/updateAvatar',params)

}


// 重置用户密码
export const UserPasswordUpdateService = (updateData) => {
    // const params = new URLSearchParams();
    // params.append('avatarUrl',avatarUrl)
    return request.patch('/users/updatePwd', updateData);
};

// 获取用户列表接口
export const getUserListService = ({ pageNum, pageSize }) => {
  return request.get('users/list',{
    params: { pageNum, pageSize }, // GET 请求参数
  });
};