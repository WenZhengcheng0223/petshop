package com.ruoyi.petshop.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.petshop.domain.BaseTreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 分类业务对象 sp_category
 *
 * @author liurui
 * @date 2022-04-26
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("分类业务对象")
public class CategoryBo extends BaseTreeEntity<CategoryBo> {

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID", required = true)
    @NotNull(message = "分类ID不能为空", groups = { EditGroup.class })
    private Long catId;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称", required = true)
    @NotBlank(message = "分类名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String catName;

    /**
     * 分类父ID
     */
    @ApiModelProperty(value = "分类父ID", required = true)
    @NotNull(message = "分类父ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long catPid;

    /**
     * 分类层级
     */
    @ApiModelProperty(value = "分类层级", required = true)
    @NotNull(message = "分类层级不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long catLevel;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Long catDeleted;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String catIcon;

    /**
     * 图标地址
     */
    @ApiModelProperty(value = "图标地址")
    private String catSrc;


}
