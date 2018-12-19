package com.zsc.springboot.web;

import com.alibaba.fastjson.JSONObject;
import com.zsc.springboot.commons.context.RequestContext;
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

    /**
     * 定义切点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public  void requestPointcut() {
        //注册方法
    }

    /**
     * 前置增强，记录请求开始时间
     */
    @Before("requestPointcut()")
    public void doBefore() {
        RequestContext.getRequestContext().setStartTime(System.currentTimeMillis());
    }

    /**
     * 后置增强
     *
     * @param result
     */
    @AfterReturning(pointcut = "requestPointcut()", returning = "result")
    public void doAfter(Object result) {
        RequestContext.getRequestContext().setResponse(JSONObject.toJSONString(result));
    }
}