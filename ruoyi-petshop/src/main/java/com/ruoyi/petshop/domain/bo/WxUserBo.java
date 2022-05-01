package com.ruoyi.petshop.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员业务对象 sp_user
 *
 * @author liurui
 * @date 2022-04-30
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
     * 微信名称
     */
    @ApiModelProperty(value = "微信名称", required = true)
    @NotBlank(message = "微信名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nickName;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址", required = true)
    private String avatarUrl;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", required = true)
    @NotBlank(message = "性别不能为空", groups = { AddGroup.class, EditGroup.class })
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
     * 帐号状态
     */
    @ApiModelProperty(value = "帐号状态", required = true)
    @NotNull(message = "帐号状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间", required = true)
    @NotNull(message = "最后登录时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date loginDate;


}
