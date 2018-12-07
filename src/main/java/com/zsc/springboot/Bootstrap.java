package com.zsc.springboot;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shichen
 * @create 2018-12-07
 * @desc
 */
@SpringBootApplication(scanBasePackages = "com.zsc.springboot")
@EnableApolloConfig
public class Bootstrap {

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

}
