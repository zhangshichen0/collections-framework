package com.zsc.springboot.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsc.springboot.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author shichen
 * @since 2018-12-17
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过nick获取用户信息
     *
     * @param nick
     * @return
     */
    @Select(value = "select * from user where nick = #{nick}")
    User getUserByNick(@Param("nick") String nick);

    /**
     * 分页查询
     *
     * @param page
     * @param status
     * @return
     */
    IPage<User> selectPageVo(Page<User> page, @Param("status") Integer status);
}
