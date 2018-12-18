package com.zsc.springboot.model;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author shichen
 * @since 2018-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId
    private Long uid;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 头像
     */
    private String photo;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别 1男2女3未知
     */
    private Integer gender;

    /**
     * 用户积分
     */
    private Integer points;

    /**
     * 绑定手机号
     */
    private String phone;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;

    /**
     * 状态 1正常 2冻结
     */
    private Integer status;

    /**
     * 是否领取过门票
     */
    private Boolean hasTicket;

    /**
     * 是否验证过
     */
    private Boolean isValidated;


}
