package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.entity.PageBean;
import org.example.entity.Result;
import org.example.entity.Users;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){

        //参数校验方法一
//        if(username!=null && username.length()>=5 && username.length()<=16 &&
//            password!=null && password.length()>=5 && password.length()<=16 ){
        //查询用户
        Users u = userService.findByUserName(username);
        if(u==null){
            //没有占用
            //注册
            userService.register(username,password);
            return Result.success();
        }else{
            //占用
            return Result.error("用户名已被占用！");
        }
//        }else{
//            return Result.error("参数不合法");
//        }

    }

    //登录
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password) {

        //根据用户名查询用户
        Users loginUser = userService.findByUserName(username);
        if(loginUser==null){
            //判断用户是否存在
            return Result.error("用户名不存在");
        }

        //判断密码是否正确,loginUser对象中的password是密文的
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            //登录成功，获取jwt令牌，用于登录认证
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());//放入id
            claims.put("username",loginUser.getUsername());//放入username
            claims.put("role",loginUser.getRole());//放入role
            String token = JwtUtil.genToken(claims);//得到token

            //把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);

            return Result.success(token);//将token放在Result的success方法里

        }
        return Result.error("密码错误");
    }

    //获取用户详细信息
    @GetMapping("/userInfo")
    public Result<Users> userInfo(/*@RequestHeader(name = "Authorization") String token*/){//token从请求头中获取
        //根据用户名查询用户
        /*Map<String, Object> map = JwtUtil.parseToken(token);  //解析token
        String username =(String) map.get("username"); //然后得到username*/

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        Users user = userService.findByUserName(username);
        return Result.success(user);
    }

    //获取用户列表（管理员操作）
    @GetMapping("/list")
    public Result<PageBean<Users>> getAllUsers(Integer pageNum, Integer pageSize){
        PageBean<Users> pageBean = userService.findAllUsers(pageNum,pageSize);
        if(pageBean!=null){
            return Result.success(pageBean);
        }
        return Result.error("没有用户数据");
    }

    //更新用户基本信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated Users users){  //@Validated 使得实体类Users里的@NotNull等注解生效

        userService.update(users);
        return Result.success();
    }

    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam String avatarUrl){//@URL 检验是否是一个url地址
        System.out.println(avatarUrl);
        userService.updateAvatar(avatarUrl);
        return Result.success();

    }

    //更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        //1.校验参数
        String oldPwd = params.get("oldPwd");//获取原密码
        String newPwd = params.get("newPwd");//获取新密码
        String rePwd = params.get("rePwd");
        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }

        //校验原密码
        //调用userService根据用户名拿到原密码，再和old_pwd比对
        //通过token拿username
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        Users loginUser = userService.findByUserName(username);
        if(!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码填写不正确");
        }

        //校验newPwd和rePwd是否一样
        if(!rePwd.equals(newPwd)){
            return Result.error("两次填写的新密码不一样");
        }

        //2.调用service完成密码更新
        userService.updatePwd(newPwd);
        //3.密码更新后，删除redis中原密码对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        return Result.success();
    }

    // 用户注销
    @PostMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token) {
        // 清除 ThreadLocal 中存储的用户信息
        ThreadLocalUtil.remove();

        // 返回成功的响应
        return Result.success("操作成功");
    }
}

