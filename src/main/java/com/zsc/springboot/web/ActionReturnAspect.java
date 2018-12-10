package com.zsc.springboot.web;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by zhangrui on 16/4/1.
 */
@Aspect
@Component
public class ActionReturnAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public  void requestPointcut() {
        //注册方法
    }

    @AfterReturning(pointcut="requestPointcut()",returning = "result")
    public void doAfter(JoinPoint jp, Object result) {
    }


    @Before("requestPointcut()")
    public void doBefore() {
    }
}