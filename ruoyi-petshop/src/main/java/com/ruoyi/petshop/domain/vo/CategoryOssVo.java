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
 * 分类-图片关联视图对象 sp_category_oss
 *
 * @author liurui
 * @date 2022-04-28
 */
@Data
@ApiModel("分类-图片关联视图对象")
@ExcelIgnoreUnannotated
public class CategoryOssVo {

    private static final long serialVersionUID = 1L;

    /**
     * 对象存储主键
     */
    @ExcelProperty(value = "对象存储主键")
    @ApiModelProperty("对象存储主键")
    private Long ossId;

    /**
     * 分类主键id
     */
    @ExcelProperty(value = "分类主键id")
    @ApiModelProperty("分类主键id")
    private String catId;

    /**
     * 图片地址
     */
    @ExcelProperty(value = "图片地址")
    @ApiModelProperty("图片地址")
    private String url;


}
