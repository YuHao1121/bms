package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.entity.Users;

import java.util.List;

@Mapper
public interface UserMapper {
    //根据用户名查询用户
    @Select("select * from users where username=#{username}")
    Users findByUserName(String username);

    //添加
    @Insert("insert into users(username, password,create_time,update_time)"+
            "values(#{username},#{password},now(),now())")
    void add(String username, String password);

    //更新用户基本信息
    @Update("update users set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(Users users);

    //更新用户头像
    @Update("update users set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);

    //更新用户密码
    @Update("update users set password=#{md5String},update_time=now() where id=#{id}")
    void updatePwd(String md5String, Integer id);

    @Select("select * from users")
    List<Users> findAllUsers();
}
