package com.zsc.springboot.commons;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;


/**
 * @author shichen
 * @create 2018-12-07
 * @desc
 */
@Getter
@Setter
public class Result<T> {

    public static final Result<Object> SUCCESS = new Result<>(true);
    public static final Result<Object> FAIL = new Result<>(false);


    private int code;
    private boolean status;
    private String msg;
    private T data;

    public Result(boolean status) {
        this.status = status;
        if (status) {
            this.code = 0;
            this.msg = "SUCCESS";
        } else {
            this.code = 1;
            this.msg = "FAIL";
        }
    }

    public Result(T data) {
        this.status = true;
        this.code = 1;
        this.msg = "SUCCESS";
        this.data = data;
    }
}
