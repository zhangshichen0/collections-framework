package com.zsc.springboot.test.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shichen
 * @create 2018-12-07
 * @desc
 */
public class LoggerBackMainTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerBackMainTest.class);

    public static void main(String[] args) {
        LOGGER.warn("【警告】test out to error file");
    }

}
