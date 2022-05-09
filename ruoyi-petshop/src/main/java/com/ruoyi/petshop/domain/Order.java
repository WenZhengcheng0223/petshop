package com.ruoyi.petshop.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单对象 sp_order
 *
 * @author ruoyi
 * @date 2022-05-07
 */
@Data
@TableName("sp_order")
public class Order extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "order_id")
    private String orderId;
    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 下订单会员id
     */
    private String userId;
    /**
     * 订单编号
     */
    private String orderNumber;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;
    /**
     * 商品数量
     */
    private Long quantity;
    /**
     * 订单总金额
     */
    private BigDecimal orderPrice;
    /**
     * 支付方式  0未支付 1支付宝  2微信  3银行卡
     */
    private String orderPay;
    /**
     * 订单是否已经发货
     */
    private String isSend;
    /**
     * 支付宝交易流水号码
     */
    private String tradeNo;
    /**
     * 发票抬头 个人 公司
     */
    private String orderFapiaoTitle;
    /**
     * 公司名称
     */
    private String orderFapiaoCompany;
    /**
     * 发票内容
     */
    private String orderFapiaoContent;
    /**
     * consignee收货人地址
     */
    private String consigneeAddr;
    /**
     * 订单状态： 0未付款、1已付款、2已退款
     */
    private String payStatus;

}
