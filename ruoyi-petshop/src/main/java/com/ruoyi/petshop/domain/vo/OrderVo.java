package com.ruoyi.petshop.domain.vo;

import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 订单视图对象 sp_order
 *
 * @author ruoyi
 * @date 2022-05-07
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
     * 商品ID
     */
    @ExcelProperty(value = "商品ID")
    @ApiModelProperty("商品ID")
    private Long goodsId;

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
     * 商品名称
     */
    @ExcelProperty(value = "商品名称")
    @ApiModelProperty("商品名称")
    private String goodsName;

    /**
     * 商品描述
     */
    @ExcelProperty(value = "商品描述")
    @ApiModelProperty("商品描述")
    private String description;

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
    private Long quantity;

    /**
     * 订单总金额
     */
    @ExcelProperty(value = "订单总金额")
    @ApiModelProperty("订单总金额")
    private BigDecimal orderPrice;

    /**
     * 支付方式  0未支付 1支付宝  2微信  3银行卡
     */
    @ExcelProperty(value = "支付方式  0未支付 1支付宝  2微信  3银行卡")
    @ApiModelProperty("支付方式  0未支付 1支付宝  2微信  3银行卡")
    private String orderPay;

    /**
     * 订单是否已经发货
     */
    @ExcelProperty(value = "订单是否已经发货")
    @ApiModelProperty("订单是否已经发货")
    private String isSend;

    /**
     * 支付宝交易流水号码
     */
    @ExcelProperty(value = "支付宝交易流水号码")
    @ApiModelProperty("支付宝交易流水号码")
    private String tradeNo;

    /**
     * 发票抬头 个人 公司
     */
    @ExcelProperty(value = "发票抬头 个人 公司")
    @ApiModelProperty("发票抬头 个人 公司")
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
     * consignee收货人地址
     */
    @ExcelProperty(value = "consignee收货人地址")
    @ApiModelProperty("consignee收货人地址")
    private String consigneeAddr;

    /**
     * 订单状态： 0未付款、1已付款、2已退款
     */
    @ExcelProperty(value = "订单状态： 0未付款、1已付款、2已退款")
    @ApiModelProperty("订单状态： 0未付款、1已付款、2已退款")
    private String payStatus;


}
