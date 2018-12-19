package com.zsc.springboot.commons.context;

/**
 * @author shichen
 * @create 2018-12-19
 * @desc
 */
public class ThreadLocalContext extends InheritableThreadLocal<RequestContext> {

    private static ThreadLocalContext threadLocalContext = new ThreadLocalContext(){
        @Override
        protected RequestContext initialValue() {
            return new RequestContext();
        }
    };

    public static ThreadLocalContext getInstance() {
        return threadLocalContext;
    }
}
