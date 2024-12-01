package org.example.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;


public class JwtUtil {
    private static final String KEY = "tangyuliang"; //一般放在配置文件里

    //接收业务数据,生成token并返回
    public static String genToken(Map<String, Object> claims) {//Map<String, Object> claims封装业务数据
        //生成token并返回
        return JWT.create()
                .withClaim("claims", claims)  // 添加载荷，比如id："1"，username："tom"。。。。。
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))  //有效期
                .sign(Algorithm.HMAC256(KEY));  //加密算法HMAC256
    }

    //接收token,验证并解析token,并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        //验证并解析token,并返回业务数据
        return JWT.require(Algorithm.HMAC256(KEY))  //申请jwt验证器，加密用什么，解密就用什么
                .build()
                .verify(token)  //验证token，生产一个解析后的jwt对象
                .getClaim("claims")   //得到所有的载荷
                .asMap();
    }

    //验证失败的情况：密钥被改，头部和载荷部分被改，token过期

}
