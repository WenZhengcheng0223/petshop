package com.ruoyi.petshop.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;


/**
 * 分类视图对象 sp_category
 *
 * @author liurui
 * @date 2022-04-26
 */
@Data
@ApiModel("分类视图对象")
@ExcelIgnoreUnannotated
public class CategoryVo {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @ExcelProperty(value = "分类ID")
    @ApiModelProperty("分类ID")
    private Long catId;

    /**
     * 分类名称
     */
    @ExcelProperty(value = "分类名称")
    @ApiModelProperty("分类名称")
    private String catName;

    /**
     * 分类父ID
     */
    @ExcelProperty(value = "分类父ID")
    @ApiModelProperty("分类父ID")
    private Long catPid;

    /**
     * 分类层级
     */
    @ExcelProperty(value = "分类层级", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "cat_level")
    @ApiModelProperty("分类层级")
    private Long catLevel;

    /**
     * 是否删除
     */
    @ExcelProperty(value = "是否删除", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "delete_status")
    @ApiModelProperty("是否删除")
    private Long catDeleted;

    /**
     * 图标
     */
    @ExcelProperty(value = "图标")
    @ApiModelProperty("图标")
    private String catIcon;

    /**
     * 图标地址
     */
    @ExcelProperty(value = "图标地址")
    @ApiModelProperty("图标地址")
    private String catSrc;

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
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    @ApiModelProperty("更新时间")
    private Date updateTime;

    private CategoryOssVo categoryOssVo;

    private Long parentId;
    private Long getParentId(){
        return getCatPid();
    }
}
