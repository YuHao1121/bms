package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.entity.Books;
import org.example.entity.PageBean;
import org.example.entity.Result;
import org.example.entity.Users;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceIpml implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Users findByUserName(String username) {

        //调用Mapper层的方法
        Users u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5String = Md5Util.getMD5String(password);
        //添加
        userMapper.add(username,md5String);
    }

    @Override
    public void update(Users users) {
        users.setUpdateTime(LocalDateTime.now());
        userMapper.update(users);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        //获取用户id
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        //获取用户id
        Map<String,Object> map = ThreadLocalUtil.get();//通过token拿id
        Integer id = (Integer) map.get("id");

        userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }

    @Override
    public PageBean<Users> findAllUsers(Integer pageNum, Integer pageSize) {
        //1.创建PageBean对象
        PageBean<Users> pageBean = new PageBean<>();

        //2.开启分页查询 -----pageHelper
        PageHelper.startPage(pageNum, pageSize);

        //3.调用mapper完成查询
        List<Users> b1 =  userMapper.findAllUsers();

        //强转？Page中提供了方法，可以获取PageHelper分页查询后，得到的总记录条数和当前页数据
        Page<Users> p = (Page<Users>) b1;

        //把数据填充到PageBean中
        //总记录数
        pageBean.setTotal(p.getTotal());
        //数据
        pageBean.setItems(p.getResult());


        return pageBean;
    }
}
