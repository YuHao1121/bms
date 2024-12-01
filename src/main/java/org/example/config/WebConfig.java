package org.example.config;

import org.example.interceptors.LoginInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName:WebConfig
 * Package:org.example.config
 * Description:
 *
 * @Author: 唐玉亮
 * @Craete:2024/11/1 - 11:20
 */

//注册拦截器
@Configuration


//@EnableGlobalMethodSecurity(prePostEnabled = true)


public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptors loginInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //登录接口和注册接口不拦截
        registry.addInterceptor(loginInterceptors)
                .excludePathPatterns("/users/login","/users/register");
    }

//    @Bean
//    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
//        return new GrantedAuthorityDefaults(""); // 去除 ROLE_ 前缀
//    }
}
