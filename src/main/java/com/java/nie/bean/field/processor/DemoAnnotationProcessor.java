package com.java.nie.bean.field.processor;


import com.java.nie.domain.TUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@Order(2)
public class DemoAnnotationProcessor {


    @Pointcut("@annotation(com.java.nie.bean.field.annotation.DemoAnnotation)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around1--------------------------------------");
        Object arg = joinPoint.getArgs()[0];
        if (arg instanceof TUser) {
            TUser user = (TUser) arg;
            user.setLoginName("zhangsan");
            arg = user;
        }
        joinPoint.proceed();
        System.out.println("around1--------------------------------------return");
        return joinPoint;
    }

    /**
     * 方法执行前
     * @param joinPoint
     */
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) throws Throwable {
        System.out.println("before1--------------------------");
        Object args = joinPoint.getArgs()[0];
        if (args instanceof TUser){
            TUser user =(TUser) args ;
            user.setLoginName("lisi");
            args=user ;
        }
    }

    @After("pointcut()")
    public void after(JoinPoint joinPoint){
        System.out.println("after1-----------------------------");
    }

    @AfterReturning("pointcut()")
    public void afterReturning(JoinPoint joinPoint){
        System.out.println("afterReturning1---------------------");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing(JoinPoint joinPoint){
        System.out.println("afterThrowing1-----------------------");
    }
}
