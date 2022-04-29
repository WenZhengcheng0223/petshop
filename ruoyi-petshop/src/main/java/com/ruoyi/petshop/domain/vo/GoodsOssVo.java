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
 * 商品图片关联视图对象 sp_goods_oss
 *
 * @author liurui
 * @date 2022-04-27
 */
@Data
@ApiModel("商品图片关联视图对象")
@ExcelIgnoreUnannotated
public class GoodsOssVo {

    private static final long serialVersionUID = 1L;

    /**
     * 对象存储主键
     */
    @ExcelProperty(value = "对象存储主键")
    @ApiModelProperty("对象存储主键")
    private Long ossId;

    /**
     * 商品主键id
     */
    @ExcelProperty(value = "商品主键id")
    @ApiModelProperty("商品主键id")
    private String goodsId;

    /**
     * 图片logo大图
     */
    @ExcelProperty(value = "图片logo")
    @ApiModelProperty("图片logo")
    private String url;
}
