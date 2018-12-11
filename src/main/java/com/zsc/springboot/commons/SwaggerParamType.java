package com.zsc.springboot.commons;

/**
 * @author shichen
 * @create 2018-12-11
 * @desc
 */
public enum SwaggerParamType {
    HEADER("header"),
    QUERY("query"),
    PATH("path"),
    //不常用
    BODY("body"),
    //不常用
    FORM("form");

    private String value;

    SwaggerParamType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }}
