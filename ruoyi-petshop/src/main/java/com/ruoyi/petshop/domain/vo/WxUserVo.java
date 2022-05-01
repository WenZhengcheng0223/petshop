package com.ruoyi.petshop.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date 2022-04-30
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
     * 微信名称
     */
    @ExcelProperty(value = "微信名称")
    @ApiModelProperty("微信名称")
    private String nickName;

    /**
     * 头像地址
     */
    @ExcelProperty(value = "头像地址")
    @ApiModelProperty("头像地址")
    private String avatarUrl;

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
     * 帐号状态
     */
    @ExcelProperty(value = "帐号状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "user_status")
    @ApiModelProperty("帐号状态")
    private Integer status;

    /**
     * 最后登录IP
     */
    @ExcelProperty(value = "最后登录IP")
    @ApiModelProperty("最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ExcelProperty(value = "最后登录时间")
    @ApiModelProperty("最后登录时间")
    private Date loginDate;


}
