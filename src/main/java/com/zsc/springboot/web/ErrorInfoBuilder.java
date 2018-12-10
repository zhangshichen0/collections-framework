package com.zsc.springboot.web;

import com.zsc.springboot.commons.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuqinglei
 * @date 2018/5/25.
 * <p>
 * 描述：快速构建错误信息
 */
@Slf4j
@Component
public class ErrorInfoBuilder {
    /**
     * 错误配置(ErrorConfiguration)
     */
    private ErrorProperties errorProperties;

    public ErrorProperties getErrorProperties() {
        return errorProperties;
    }

    /**
     * 错误构造器 (Constructor) 传递配置属性：server.xx -> server.error.xx
     */
    public ErrorInfoBuilder(ServerProperties serverProperties) {
        this.errorProperties = serverProperties.getError();
    }

    /**
     * 构建错误信息.
     */
    public Result<Object> getErrorInfo(HttpServletRequest request, HttpServletResponse response) {

        Throwable e = this.getError(request);
        if (e instanceof HttpRequestMethodNotSupportedException) {
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        } else if (e instanceof MissingServletRequestParameterException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else {
            log.error("api error " + request.getAttribute(WebUtils.ERROR_REQUEST_URI_ATTRIBUTE) + "\nheaders=" + getHeaders(request), e);
        }

        Result<Object> result = new Result<>(false);
        result.setMsg("请求错误");
        return result;
    }


    /**
     * 获取错误.(Error/Exception)
     * <p>
     * 获取方式：通过Request对象获取(Key="javax.servlet.error.exception").
     *
     * @see DefaultErrorAttributes #addErrorDetails
     */
    public Throwable getError(HttpServletRequest request) {
        //根据Request对象获取错误.
        Throwable error = (Throwable) request.getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR");
        //当获取错误非空,取出RootCause.
        if (error != null) {
            while (error instanceof ServletException && error.getCause() != null) {
                error = error.getCause();
            }
        }//当获取错误为null,此时我们设置错误信息即可.
        else {
            String message = (String) request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE);
            if (StringUtils.isEmpty(message)) {
                HttpStatus status = getHttpStatus(request);
                message = "Unknown Exception With " + status.value() + " " + status.getReasonPhrase();
            }
            error = new Exception(message);
        }
        return error;
    }


    /**
     * 获取通信状态(HttpStatus)
     *
     * @see AbstractErrorController #getStatus
     */
    public HttpStatus getHttpStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
        try {
            return statusCode != null ? HttpStatus.valueOf(statusCode) : HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * 获取请求头
     */
    private Map<String, String> getHeaders(HttpServletRequest httpServletRequest) {
        Enumeration<String> names = httpServletRequest.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headers.put(name, httpServletRequest.getHeader(name));
        }
        return headers;
    }

}
