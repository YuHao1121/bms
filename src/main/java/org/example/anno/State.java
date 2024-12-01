//package org.example.anno;
//
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//import org.example.validation.StateValidation;
//
//import java.lang.annotation.*;
//
//@Documented //元注解
//@Target({ElementType.FIELD})//元注解
//@Retention(RetentionPolicy.RUNTIME)//元注解
//@Constraint(validatedBy = {StateValidation.class}) //指定提供校验规则的类
//
//
//public @interface State {
//
//    //提供校验失败后的提示信息
//    String message() default "___只能是已发布或者草稿";
//
//    //指定分组
//    Class<?>[] groups() default {};
//
//    //负载  获取到state注解的附加信息
//    Class<? extends Payload>[] payload() default {};
//
//}
