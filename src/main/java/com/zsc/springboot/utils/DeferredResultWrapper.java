package com.zsc.springboot.utils;

import com.zsc.springboot.commons.Result;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author shichen
 * @create 2018-12-10
 * @desc
 */
public class DeferredResultWrapper {

    private static final long TIME_OUT = 60 * 1000;
    private DeferredResult<Result<Object>> result;

    public DeferredResultWrapper() {
        result = new DeferredResult<>(TIME_OUT, Result.SUCCESS);
    }

    /**
     * 超时回调
     *
     * @param timeoutCallback
     */
    public void onTimeout(Runnable timeoutCallback) {
        result.onTimeout(timeoutCallback);
    }

    /**
     * 正常完成回调
     *
     * @param completionCallback
     */
    public void onCompletion(Runnable completionCallback) {
        result.onCompletion(completionCallback);
    }

    /**
     * 设置返回值
     */
    public void setResult(Result<Object> finalResult) {
        result.setResult(finalResult);
    }

    /**
     * 获取返回值
     *
     * @return
     */
    public DeferredResult<Result<Object>> getResult() {
        return result;
    }

}
