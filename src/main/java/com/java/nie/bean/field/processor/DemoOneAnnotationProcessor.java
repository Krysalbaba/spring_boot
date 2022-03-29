package com.java.nie.bean.field.processor;


import com.java.nie.domain.TUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect //定义切面
@Component
@Order(1)
public class DemoOneAnnotationProcessor {


    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.java.nie.bean.field.annotation.DemoOneAnnotation)")
    public void pointcutOne(){

    }

    @Around("pointcutOne()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around2--------------------------------------");
        Object arg = joinPoint.getArgs()[0];
        if (arg instanceof TUser) {
            TUser user = (TUser) arg;
            user.setLoginName("zhangsan");
            arg = user;
        }
        joinPoint.proceed();
        return joinPoint;
    }



    /**
     * 方法执行前
     * @param joinPoint
     */
    @Before("pointcutOne()")
    public void before(JoinPoint joinPoint) throws Throwable {
        System.out.println("before2--------------------------");
        Object args = joinPoint.getArgs()[0];
        if (args instanceof TUser){
            TUser user =(TUser) args ;
            user.setLoginName("lisi");
            args=user ;
        }
    }
}
