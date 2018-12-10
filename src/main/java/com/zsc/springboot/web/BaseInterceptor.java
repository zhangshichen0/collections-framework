package com.zsc.springboot.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 所有拦截器都正确执行后的请求日志记录，拦截器抛出异常后由ApiHandlerExceptionResolver打印request日志
 * Created by zhangrui on 16/3/31.
 *
 * @author shichen
 * @date
 */
@Slf4j
public class BaseInterceptor extends HandlerInterceptorAdapter {


}
