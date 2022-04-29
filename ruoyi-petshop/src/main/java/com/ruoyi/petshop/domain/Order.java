package com.ruoyi.petshop.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
 * @date 2022-04-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sp_order")
public class Order extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "order_id",type = IdType.AUTO)
    private String orderId;
    /**
     * 下订单会员id
     */
    private String userId;
    /**
     * 订单编号
     */
    private String orderNumber;
    /**
     * 订单总金额
     */
    private BigDecimal orderPrice;
    /**
     * 支付方式
     */
    private String orderPay;
    /**
     * 发货状态
     */
    private String isSend;
    /**
     * 交易流水号码
     */
    private String tradeNo;
    /**
     * 发票抬头
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
     * 收货人地址
     */
    private String consigneeAddr;
    /**
     * 订单状态
     */
    private String payStatus;

}
