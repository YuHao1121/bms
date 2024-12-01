package org.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @NotNull
    private Integer id;//主键ID

    private String username;//用户名
    @JsonIgnore //让springmvc把当前对象转换成json字符串的时候，忽略password，最终的json字符串中就没有password属性
    private String password;//密码

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;//昵称

    @NotEmpty
    @Email
    private String email;//邮箱

    private String role; //角色

    @URL
    private String userPic;//用户头像地址

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")//根据接口文档要求
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间
}
