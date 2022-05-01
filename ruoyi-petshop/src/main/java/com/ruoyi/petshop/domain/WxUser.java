package com.ruoyi.petshop.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员对象 sp_user
 *
 * @author liurui
 * @date 2022-04-30
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
     * 微信名称
     */
    private String nickName;
    /**
     * 头像地址
     */
    private String avatarUrl;
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
     * 帐号状态
     */
    private Integer status;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private Date loginDate;

}
