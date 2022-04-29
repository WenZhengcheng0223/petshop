package com.ruoyi.petshop.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.math.BigDecimal;

/**
 * 订单业务对象 sp_order
 *
 * @author ruoyi
 * @date 2022-04-26
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("订单业务对象")
public class OrderBo extends BaseEntity {


    @ApiModelProperty(value = "主键id", required = true)
    @NotNull(message = "订单编号不能为空", groups = { AddGroup.class })
    private String orderId;


    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号", required = true)
    @NotBlank(message = "订单编号不能为空", groups = { AddGroup.class })
    private String orderNumber;

    /**
     * 订单总金额
     */
    @ApiModelProperty(value = "订单总金额", required = true)
    @NotNull(message = "订单总金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal orderPrice;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式", required = true)
    @NotBlank(message = "支付方式不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderPay;

    /**
     * 发货状态
     */
    @ApiModelProperty(value = "发货状态", required = true)
    @NotBlank(message = "发货状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isSend;

    /**
     * 交易流水号码
     */
    @ApiModelProperty(value = "交易流水号码", required = true)
    @NotBlank(message = "交易流水号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tradeNo;

    /**
     * 发票抬头
     */
    @ApiModelProperty(value = "发票抬头")
    private String orderFapiaoTitle;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String orderFapiaoCompany;

    /**
     * 发票内容
     */
    @ApiModelProperty(value = "发票内容")
    private String orderFapiaoContent;

    /**
     * 收货人地址
     */
    @ApiModelProperty(value = "收货人地址", required = true)
    @NotBlank(message = "收货人地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String consigneeAddr;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态", required = true)
    @NotBlank(message = "订单状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String payStatus;


}
