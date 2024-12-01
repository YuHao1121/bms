//package org.example.validation;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import org.example.anno.State;
//
////<给哪个注解提供校验规则，校验的数据类型>
//public class StateValidation implements ConstraintValidator<State, String> {
//
//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
//        //提供校验规则
//        if(value == null){
//            return false;
//        }
//        if(value.equals("已发布")||value.equals("草稿")){
//            return true;
//        }
//        return false;
//    }
//}
