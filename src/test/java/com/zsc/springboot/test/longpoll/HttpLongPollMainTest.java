package com.zsc.springboot.test.longpoll;

import com.zsc.springboot.utils.HttpUtils;

/**
 * @author shichen
 * @create 2018-12-10
 * @desc
 */
public class HttpLongPollMainTest {

    public static void main(String[] args) {
        while (true) {
            String result = HttpUtils.get("http://127.0.0.1:8181/collections-framework/api/notification?uid=1", null);
            System.out.println(result);
        }
    }

}
