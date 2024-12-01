package org.example.exception;

import org.example.entity.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName:GlobalExceptionHandler
 * Package:org.example.exception
 * Description:
 *
 * @Author: 唐玉亮
 * @Craete:2024/10/31 - 20:48
 */


//当注册不符合条件时，返回状态码为500，不符合我们要的返回类型
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e) {

        e.printStackTrace();//异常信息输出到控制台

        //有些异常没有e.getMessage()给出错误提示信息
        //调用StringUtils.hasLength()问有没有错误原因，有就返回，没有就返回“操作失败”
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");

    }
}
