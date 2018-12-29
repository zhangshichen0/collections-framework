package com.zsc.springboot.dao.provider;

import org.apache.ibatis.annotations.Param;

/**
 * @author shichen
 * @create 2018-12-29
 * @desc
 */
public class UserSelectProvider {

    /**
     * 携带param
     *
     * @param uid
     * @return
     */
    public String getUserByUidWithParam(@Param("uid") long uid) {
        return "SELECT * FROM user where uid = #{uid}";
    }

}
