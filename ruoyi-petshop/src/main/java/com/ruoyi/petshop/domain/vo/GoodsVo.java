package com.ruoyi.petshop.domain.vo;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import com.ruoyi.petshop.domain.GoodsOss;
import com.ruoyi.system.domain.SysOss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;


/**
 * 商品视图对象 sp_goods
 *
 * @author ruoyi
 * @date 2022-04-26
 */
@Data
@ApiModel("商品视图对象")
@ExcelIgnoreUnannotated
public class GoodsVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    @ApiModelProperty("主键id")
    private String goodsId;

    /**
     * 商品名称
     */
    @ExcelProperty(value = "商品名称")
    @ApiModelProperty("商品名称")
    private String goodsName;

    /**
     * 商品价格
     */
    @ExcelProperty(value = "商品价格")
    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;

    /**
     * 商品数量
     */
    @ExcelProperty(value = "商品数量")
    @ApiModelProperty("商品数量")
    private String goodsNumber;

    /**
     * 商品重量
     */
    @ExcelProperty(value = "商品重量")
    @ApiModelProperty("商品重量")
    private String goodsWeight;

    /**
     * 类型id
     */
    @ExcelProperty(value = "类型id")
    @ApiModelProperty("类型id")
    private String catId;

    /**
     * 商品详情介绍
     */
    @ExcelProperty(value = "商品详情介绍")
    @ApiModelProperty("商品详情介绍")
    private String goodsIntroduce;


    /**
     * 商品情况
     */
    @ExcelProperty(value = "商品情况", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "goods_situation")
    @ApiModelProperty("商品情况")
    private String isDel;

    /**
     * 一级分类id
     */
    @ExcelProperty(value = "一级分类id")
    @ApiModelProperty("一级分类id")
    private Long catOneId;

    /**
     * 二级分类id
     */
    @ExcelProperty(value = "二级分类id")
    @ApiModelProperty("二级分类id")
    private Long catTwoId;

    /**
     * 三级分类id
     */
    @ExcelProperty(value = "三级分类id")
    @ApiModelProperty("三级分类id")
    private Long catThreeId;

    /**
     * 热卖数量
     */
    @ExcelProperty(value = "热卖数量")
    @ApiModelProperty("热卖数量")
    private String hotMumber;

    /**
     * 是否促销
     */
    @ExcelProperty(value = "是否促销", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "is_promote")
    @ApiModelProperty("是否促销")
    private Long isPromote;

    /**
     * 商品状态
     */
    @ExcelProperty(value = "商品状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "goods_status")
    @ApiModelProperty("商品状态")
    private Long goodsState;

    private List<GoodsOssVo> goodsOss;

}
