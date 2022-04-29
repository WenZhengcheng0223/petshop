package com.ruoyi.petshop.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员业务对象 sp_user
 *
 * @author liurui
 * @date 2022-04-26
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("会员业务对象")
public class WxUserBo extends BaseEntity {

    /**
     * 自增id
     */
    @ApiModelProperty(value = "自增id", required = true)
    @NotNull(message = "自增id不能为空", groups = { EditGroup.class })
    private Long userId;

    /**
     * 微信唯一编号信息
     */
    @ApiModelProperty(value = "微信唯一编号信息", required = true)
    @NotBlank(message = "微信唯一编号信息不能为空", groups = { EditGroup.class })
    private String openid;

    /**
     * 登录名
     */
    @ApiModelProperty(value = "登录名", required = true)
    @NotBlank(message = "登录名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nickName;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码")
    private String password;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String userSex;

    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    private String userTel;

    /**
     * 爱好
     */
    @ApiModelProperty(value = "爱好")
    private String userHobby;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String userIntroduce;


}
