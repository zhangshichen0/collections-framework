package com.zsc.springboot.web;

import com.zsc.springboot.commons.context.RequestContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 所有拦截器都正确执行后的请求日志记录，拦截器抛出异常后由ApiHandlerExceptionResolver打印request日志
 * Created by zhangrui on 16/3/31.
 *
 * @author shichen
 * @date
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (Objects.isNull(ex)) {
            RequestContext.logRequest(request, response);
            RequestContext.clear();
        }
    }

    /**
     * 处理异步请求
     *
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    }
}
