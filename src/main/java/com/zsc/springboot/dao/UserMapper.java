package com.zsc.springboot.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsc.springboot.dao.provider.UserSelectProvider;
import com.zsc.springboot.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
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

    /**
     * 使用Results对数据库字段和实体字段进行对应,使用Provider的形式获取sql，这样可以对不同类型的sql进行统一管理
     *
     * 可以使用provider的方式代替xml的方式，动态生成复杂的sql语句
     *
     * @param uid
     * @return
     */
    @Results({
            @Result(property = "uid", column = "uid"),
            @Result(property = "nick", column = "nick"),
            @Result(property = "photo", column = "photo"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "points", column = "points"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "hasTicket", column = "has_ticket"),
            @Result(property = "isValidated", column = "is_validated"),
    })
    @SelectProvider(type = UserSelectProvider.class, method = "getUserByUidWithParam")
    User getUserByUid(@Param("uid") long uid);
}
