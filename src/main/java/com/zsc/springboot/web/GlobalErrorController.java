package com.zsc.springboot.web;

import com.zsc.springboot.commons.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuqinglei
 * @date 2018/5/25.
 * <p>
 * 描述：统一处理错误/异常(针对控制层)
 */
@Controller
@RequestMapping("${server.error.path:/error}")
public class GlobalErrorController implements ErrorController {

    /**
     * 错误信息的构建工具.
     */
    @Autowired
    private ErrorInfoBuilder errorInfoBuilder;

    /**
     * 获取错误控制器的映射路径.
     */
    @Override
    public String getErrorPath() {
        return errorInfoBuilder.getErrorProperties().getPath();
    }

    /**
     * 情况2：其它预期类型 则返回详细的错误信息(JSON).
     */
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Result<Object> error(HttpServletRequest request, HttpServletResponse response) {
        return errorInfoBuilder.getErrorInfo(request, response);
    }


}
