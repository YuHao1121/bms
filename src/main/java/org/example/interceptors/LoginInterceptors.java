package org.example.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.utils.JwtUtil;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:LoginInterceptors
 * Package:org.example.interceptors
 * Description:
 *
 * @Author: 唐玉亮
 * @Craete:2024/11/1 - 11:13
 */
 // 拦截器，使用拦截器同一验证并解析token
@Component
public class LoginInterceptors implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //验证令牌
        String token = request.getHeader("Authorization");

        //验证token
        try {
            //从redis中获取相同的token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if (redisToken == null) {
                //证明token已经失效
                throw new RuntimeException();
            }

            Map<String, Object> claims = JwtUtil.parseToken(token);//解析token

            //角色权限控制
            String role = claims.get("role").toString();
            String requestURI = request.getRequestURI();
            //假设你有一个需要管理员权限的URL列表
            Map<String,String> adminUrls = new HashMap<>();
            //adminUrls.put("/notices/list","reader");//查询通知 都可以的不限制
            //管理员才具有的功能
            adminUrls.put("/users/list","admin");

            adminUrls.put("/tags/addTag","admin");
            adminUrls.put("/tags/update","admin");
            adminUrls.put("/tags/delete","admin");

            adminUrls.put("/notices/save","admin");
            adminUrls.put("/notices/delete","admin");
            adminUrls.put("/notices/update","admin");

            adminUrls.put("/categories/addCategory","admin");
            adminUrls.put("/categories/update","admin");
            adminUrls.put("/categories/delete","admin");

            adminUrls.put("/books/insertBook","admin");
            adminUrls.put("/books/delete","admin");
            adminUrls.put("/books/update","admin");
            adminUrls.put("/books/addTagToBook","admin");

            for (Map.Entry<String, String> entry : adminUrls.entrySet()) {
                if (requestURI.matches(entry.getKey())) {
                    if (!entry.getValue().equals(role) ) {
                        response.setStatus(403);
                        return false;
                    }
                    break;
                }
            }

            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            //不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        //清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
