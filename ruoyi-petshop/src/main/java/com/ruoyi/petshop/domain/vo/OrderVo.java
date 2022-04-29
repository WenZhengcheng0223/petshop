package com.ruoyi.petshop.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import com.ruoyi.petshop.domain.GoodsOss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 订单视图对象 sp_order
 *
 * @author ruoyi
 * @date 2022-04-26
 */
@Data
@ApiModel("订单视图对象")
@ExcelIgnoreUnannotated
public class OrderVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    @ApiModelProperty("主键id")
    private String orderId;

    /**
     * 下订单会员id
     */
    @ExcelProperty(value = "下订单会员id")
    @ApiModelProperty("下订单会员id")
    private String userId;

    /**
     * 订单编号
     */
    @ExcelProperty(value = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderNumber;

    /**
     * 订单总金额
     */
    @ExcelProperty(value = "订单总金额")
    @ApiModelProperty("订单总金额")
    private BigDecimal orderPrice;

    /**
     * 支付方式
     */
    @ExcelProperty(value = "支付方式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "order_status")
    @ApiModelProperty("支付方式")
    private String orderPay;

    /**
     * 发货状态
     */
    @ExcelProperty(value = "发货状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "order_out_status")
    @ApiModelProperty("发货状态")
    private String isSend;

    /**
     * 交易流水号码
     */
    @ExcelProperty(value = "交易流水号码")
    @ApiModelProperty("交易流水号码")
    private String tradeNo;

    /**
     * 发票抬头
     */
    @ExcelProperty(value = "发票抬头", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "order_fapiao_title")
    @ApiModelProperty("发票抬头")
    private String orderFapiaoTitle;

    /**
     * 公司名称
     */
    @ExcelProperty(value = "公司名称")
    @ApiModelProperty("公司名称")
    private String orderFapiaoCompany;

    /**
     * 发票内容
     */
    @ExcelProperty(value = "发票内容")
    @ApiModelProperty("发票内容")
    private String orderFapiaoContent;

    /**
     * 收货人地址
     */
    @ExcelProperty(value = "收货人地址")
    @ApiModelProperty("收货人地址")
    private String consigneeAddr;

    /**
     * 订单状态
     */
    @ExcelProperty(value = "订单状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "pay_status")
    @ApiModelProperty("订单状态")
    private String payStatus;

    /**
     * 记录生成时间
     */
    @ExcelProperty(value = "记录生成时间")
    @ApiModelProperty("记录生成时间")
    private Date createTime;

    /**
     * 记录修改时间
     */
    @ExcelProperty(value = "记录修改时间")
    @ApiModelProperty("记录修改时间")
    private Date updateTime;

    private GoodsOss goodsOss;

}
