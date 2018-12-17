package com.zsc.springboot.service;

import com.zsc.springboot.model.User;
import com.zsc.springboot.dao.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author shichen
 * @since 2018-12-17
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

}
