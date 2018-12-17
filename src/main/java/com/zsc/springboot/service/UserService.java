package com.zsc.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsc.springboot.model.User;
import com.zsc.springboot.dao.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private UserMapper userMapper;

    /**
     * 测试注解的get方法
     *
     * @param nick
     * @return
     */
    public User getUserByNick(String nick) {
        return userMapper.getUserByNick(nick);
    }

    /**
     * 获取分页
     *
     * @param next
     * @param limit
     * @param status
     * @return
     */
    public List<User> selectPageVo(int next, int limit, Integer status) {

        Page<User> page = new Page<>(next / limit + 1, limit);
        IPage<User> pageVo = userMapper.selectPageVo(page, status);
        return pageVo.getRecords();
    }

    /**
     * 使用lambda表达式
     *
     * @param nick
     * @return
     */
    public User selectUserByNick(String nick) {
        return userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getNick, nick).select(User::getUid, User::getNick));
    }

    /**
     * 插入字段
     *
     * @return
     */
    public boolean insertUser() {
        User user = new User();
        user.setUid(1L);
        user.setNick("Test");
        user.setCreateTime(LocalDateTime.now());
        user.setModifyTime(LocalDateTime.now());
        user.setStatus(1);
        return super.save(user);
    }

}
