package com.java.nie.bean.field.annotation;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Documented    //标识 可以在javaDoc中生成
@Target(ElementType.METHOD)  //作用域  Method(作用于方法上)
@Retention(RetentionPolicy.RUNTIME)  //注解生命周期(runtime 始终生效)
public @interface DemoAnnotation {

}
