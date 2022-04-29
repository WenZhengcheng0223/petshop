package com.ruoyi.petshop.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.petshop.domain.GoodsOss;
import com.ruoyi.petshop.domain.vo.GoodsOssVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品业务对象 sp_goods
 *
 * @author ruoyi
 * @date 2022-04-26
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("商品业务对象")
public class GoodsBo extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", required = true)
    @NotBlank(message = "主键id不能为空", groups = {EditGroup.class})
    private String goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称", required = true)
    @NotBlank(message = "商品名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String goodsName;

    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格", required = true)
    @NotNull(message = "商品价格不能为空", groups = {AddGroup.class, EditGroup.class})
    private BigDecimal goodsPrice;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量", required = true)
    @NotBlank(message = "商品数量不能为空", groups = {AddGroup.class, EditGroup.class})
    private String goodsNumber;

    /**
     * 商品重量
     */
    @ApiModelProperty(value = "商品重量")
    private String goodsWeight;

    /**
     * 类型id
     */
    @ApiModelProperty(value = "类型id", required = true)
    @NotBlank(message = "类型id不能为空", groups = {AddGroup.class, EditGroup.class})
    private String catId;

    /**
     * 商品详情介绍
     */
    @ApiModelProperty(value = "商品详情介绍")
    private String goodsIntroduce;


    /**
     * 商品情况
     */
    @ApiModelProperty(value = "商品情况", required = true)
    @NotBlank(message = "商品情况不能为空", groups = {AddGroup.class, EditGroup.class})
    private String isDel;

    /**
     * 一级分类id
     */
    @ApiModelProperty(value = "一级分类id", required = true)
    @NotNull(message = "一级分类id不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long catOneId;

    /**
     * 二级分类id
     */
    @ApiModelProperty(value = "二级分类id", required = true)
    @NotNull(message = "二级分类id不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long catTwoId;

    /**
     * 三级分类id
     */
    @ApiModelProperty(value = "三级分类id", required = true)
    @NotNull(message = "三级分类id不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long catThreeId;

    /**
     * 热卖数量
     */
    @ApiModelProperty(value = "热卖数量", required = true)
    @NotBlank(message = "热卖数量不能为空", groups = {AddGroup.class, EditGroup.class})
    private String hotMumber;

    /**
     * 是否促销
     */
    @ApiModelProperty(value = "是否促销", required = true)
    @NotNull(message = "是否促销不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long isPromote;

    /**
     * 商品状态
     */
    @ApiModelProperty(value = "商品状态", required = true)
    @NotNull(message = "商品状态不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long goodsState;

    @ApiModelProperty(value = "商品状态", required = false)
    private List<GoodsOssBo> goodsOss;


}
