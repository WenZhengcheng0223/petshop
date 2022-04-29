package com.ruoyi.petshop.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 会员视图对象 sp_user
 *
 * @author liurui
 * @date 2022-04-26
 */
@Data
@ApiModel("会员视图对象")
@ExcelIgnoreUnannotated
public class WxUserVo {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @ExcelProperty(value = "自增id")
    @ApiModelProperty("自增id")
    private Long userId;

    /**
     * 微信唯一编号信息
     */
    @ExcelProperty(value = "微信唯一编号信息")
    @ApiModelProperty("微信唯一编号信息")
    private String openid;

    /**
     * 登录名
     */
    @ExcelProperty(value = "登录名")
    @ApiModelProperty("登录名")
    private String nickName;

    /**
     * 头像地址
     */
    @ExcelProperty(value = "头像地址")
    @ApiModelProperty("头像地址")
    private String avatarUrl;

    /**
     * 登录密码
     */
    @ExcelProperty(value = "登录密码")
    @ApiModelProperty("登录密码")
    private String password;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    @ApiModelProperty("性别")
    private String userSex;

    /**
     * 手机
     */
    @ExcelProperty(value = "手机")
    @ApiModelProperty("手机")
    private String userTel;

    /**
     * 爱好
     */
    @ExcelProperty(value = "爱好")
    @ApiModelProperty("爱好")
    private String userHobby;

    /**
     * 简介
     */
    @ExcelProperty(value = "简介")
    @ApiModelProperty("简介")
    private String userIntroduce;

    /**
     * 创建者
     */
    @ExcelProperty(value = "创建者")
    @ApiModelProperty("创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新者
     */
    @ExcelProperty(value = "更新者")
    @ApiModelProperty("更新者")
    private String updateBy;

    /**
     * 修改时间
     */
    @ExcelProperty(value = "修改时间")
    @ApiModelProperty("修改时间")
    private Date updateTime;


}
