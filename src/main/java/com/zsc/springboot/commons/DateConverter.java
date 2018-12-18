package com.zsc.springboot.commons;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author shichen
 * @create 2018-12-18
 * @desc
 */
@Slf4j
public class DateConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String s) {

        if (Strings.isNullOrEmpty(s)) {
            return null;
        }

        LocalDateTime dateTime = null;
        try {
            dateTime = LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            log.error("【参数转换】string to date error, param {}", s, e);
        }

        return dateTime;
    }
}
