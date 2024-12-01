package org.example.service;


import org.example.entity.PageBean;
import org.example.entity.Users;

import java.util.List;


public interface UserService {



    //  根据用户名查询用户
    Users findByUserName(String username);

    //注册
    void register(String username, String password);

    //更新
    void update(Users users);

    //更新头像
    void updateAvatar(String avatarUrl);

    //更新密码
    void updatePwd(String newPwd);

    //查看所有用户信息（仅管理员可以访问）
    PageBean<Users> findAllUsers(Integer pageNum, Integer pageSize);
}
