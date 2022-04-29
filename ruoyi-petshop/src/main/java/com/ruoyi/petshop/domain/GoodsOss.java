package com.ruoyi.petshop.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品图片关联对象 sp_goods_oss
 *
 * @author liurui
 * @date 2022-04-27
 */
@Data
@TableName("sp_goods_oss")
public class GoodsOss extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 对象存储主键
     */
    @TableId(value = "oss_id")
    private Long ossId;
    /**
     * 商品主键id
     */
    private String goodsId;
    /**
     * 图片logo
     */
    @TableField(value = "img_url")
    private String url;


}
