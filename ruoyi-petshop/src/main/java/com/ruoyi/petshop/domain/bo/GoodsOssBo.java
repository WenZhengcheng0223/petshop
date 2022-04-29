package com.ruoyi.petshop.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品图片关联业务对象 sp_goods_oss
 *
 * @author liurui
 * @date 2022-04-27
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("商品图片关联业务对象")
public class GoodsOssBo extends BaseEntity {

    /**
     * 对象存储主键
     */
    @ApiModelProperty(value = "对象存储主键", required = true)
    @NotNull(message = "对象存储主键不能为空", groups = { EditGroup.class })
    private Long ossId;

    /**
     * 商品主键id
     */
    @ApiModelProperty(value = "商品主键id", required = true)
    @NotBlank(message = "商品主键id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String goodsId;

    /**
     * 图片logo大图
     */
    @ApiModelProperty(value = "图片logo", required = true)
    @NotBlank(message = "图片logo不能为空", groups = { AddGroup.class, EditGroup.class })
    private String url;



}
