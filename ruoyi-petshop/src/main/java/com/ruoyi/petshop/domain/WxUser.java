package com.ruoyi.petshop.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员对象 sp_user
 *
 * @author liurui
 * @date 2022-04-26
 */
@Data
@TableName("sp_user")
public class WxUser extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 自增id
     */
    @TableId(value = "user_id")
    private Long userId;
    /**
     * 微信唯一编号信息
     */
    private String openid;
    /**
     * 登录名
     */
    private String nickName;
    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 性别
     */
    private String userSex;
    /**
     * 手机
     */
    private String userTel;
    /**
     * 爱好
     */
    private String userHobby;
    /**
     * 简介
     */
    private String userIntroduce;

}
