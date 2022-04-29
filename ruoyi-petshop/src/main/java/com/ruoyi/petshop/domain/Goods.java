package com.ruoyi.petshop.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.petshop.domain.vo.GoodsOssVo;
import lombok.Data;
import com.ruoyi.petshop.domain.GoodsOss;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品对象 sp_goods
 *
 * @author ruoyi
 * @date 2022-04-26
 */
@Data
@TableName("sp_goods")
public class Goods extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "goods_id")
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;
    /**
     * 商品数量
     */
    private String goodsNumber;
    /**
     * 商品重量
     */
    private String goodsWeight;
    /**
     * 类型id
     */
    private String catId;
    /**
     * 商品详情介绍
     */
    private String goodsIntroduce;


    /**
     * 商品情况
     */
    private String isDel;
    /**
     * 一级分类id
     */
    private Long catOneId;
    /**
     * 二级分类id
     */
    private Long catTwoId;
    /**
     * 三级分类id
     */
    private Long catThreeId;
    /**
     * 热卖数量
     */
    private String hotMumber;
    /**
     * 是否促销
     */
    private Long isPromote;
    /**
     * 商品状态
     */
    private Long goodsState;
    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 图片集合
     */
    @TableField(exist = false)
    private List<GoodsOssVo> goodsOss;

}
