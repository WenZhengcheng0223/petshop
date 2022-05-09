package com.ruoyi.petshop.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单业务对象 sp_order
 *
 * @author ruoyi
 * @date 2022-05-07
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("订单业务对象")
public class OrderBo extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", required = true)
    @NotBlank(message = "主键id不能为空", groups = { EditGroup.class })
    private String orderId;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", required = true)
    @NotNull(message = "商品ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long goodsId;

    /**
     * 下订单会员id
     */
    @ApiModelProperty(value = "下订单会员id", required = true)
    @NotBlank(message = "下订单会员id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号", required = true)
    @NotBlank(message = "订单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderNumber;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称", required = true)
    @NotBlank(message = "商品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String goodsName;

    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述", required = true)
    @NotBlank(message = "商品描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String description;

    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格", required = true)
    @NotNull(message = "商品价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal goodsPrice;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量", required = true)
    @NotNull(message = "商品数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long quantity;

    /**
     * 订单总金额
     */
    @ApiModelProperty(value = "订单总金额", required = true)
    @NotNull(message = "订单总金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal orderPrice;

    /**
     * 支付方式  0未支付 1支付宝  2微信  3银行卡
     */
    @ApiModelProperty(value = "支付方式  0未支付 1支付宝  2微信  3银行卡", required = true)
    @NotBlank(message = "支付方式  0未支付 1支付宝  2微信  3银行卡不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderPay;

    /**
     * 订单是否已经发货
     */
    @ApiModelProperty(value = "订单是否已经发货", required = true)
    @NotBlank(message = "订单是否已经发货不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isSend;

    /**
     * 支付宝交易流水号码
     */
    @ApiModelProperty(value = "支付宝交易流水号码", required = true)
    @NotBlank(message = "支付宝交易流水号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tradeNo;

    /**
     * 发票抬头 个人 公司
     */
    @ApiModelProperty(value = "发票抬头 个人 公司", required = true)
    @NotBlank(message = "发票抬头 个人 公司不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderFapiaoTitle;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称", required = true)
    @NotBlank(message = "公司名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderFapiaoCompany;

    /**
     * 发票内容
     */
    @ApiModelProperty(value = "发票内容", required = true)
    @NotBlank(message = "发票内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderFapiaoContent;

    /**
     * consignee收货人地址
     */
    @ApiModelProperty(value = "consignee收货人地址", required = true)
    @NotBlank(message = "consignee收货人地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String consigneeAddr;

    /**
     * 订单状态： 0未付款、1已付款、2已退款
     */
    @ApiModelProperty(value = "订单状态： 0未付款、1已付款、2已退款", required = true)
    @NotBlank(message = "订单状态： 0未付款、1已付款、2已退款不能为空", groups = { AddGroup.class, EditGroup.class })
    private String payStatus;


}
