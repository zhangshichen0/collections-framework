package com.zsc.springboot.test.tools;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.zsc.springboot.model.User;

import java.lang.reflect.Field;

/**
 * @author shichen
 * @create 2018-12-29
 * @desc
 */
public class ResultsAnnotationTools {

    public static void main(String[] args) {
        System.out.println(ResultsAnnotationTools.getResultsStr(User.class));
    }

    /**
     * 1.用于获取结果集的映射关系
     */
    public static String getResultsStr(Class origin) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@Results({\n");
        for (Field field : origin.getDeclaredFields()) {
            String property = field.getName();
            //映射关系：对象属性(驼峰)->数据库字段(下划线)
            String column = new PropertyNamingStrategy.SnakeCaseStrategy().translate(field.getName()).toUpperCase();
            stringBuilder.append(String.format("@Result(property = \"%s\", column = \"%s\"),\n", property, column));
        }
        stringBuilder.append("})");
        return stringBuilder.toString();
    }

}
