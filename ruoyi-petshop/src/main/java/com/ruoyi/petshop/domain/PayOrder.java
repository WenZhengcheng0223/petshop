package com.ruoyi.petshop.domain;


import com.ruoyi.petshop.domain.bo.GoodsBo;
import com.ruoyi.petshop.domain.bo.OrderBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode
@Data
public class PayOrder {

    /**
     * 地址
     */
    private String address;

    /**
     * 商品列表
     */
    private List<GoodsBo> goods;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;


    /**
     * 订单描述
     */
    private String description;

}
