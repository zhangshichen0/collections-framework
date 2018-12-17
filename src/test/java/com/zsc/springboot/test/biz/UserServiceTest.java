package com.zsc.springboot.test.biz;

import com.zsc.springboot.service.UserService;
import com.zsc.springboot.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author shichen
 * @create 2018-12-17
 * @desc
 */
public class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetByNick() {
        userService.getUserByNick("江强");
    }

    /**
     * 测试插入
     *
     */
    @Test
    public void testInsertUser() {
        userService.insertUser();
    }

}
