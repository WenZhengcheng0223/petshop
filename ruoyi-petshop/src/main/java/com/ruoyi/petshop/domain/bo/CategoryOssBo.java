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
 * 分类-图片关联业务对象 sp_category_oss
 *
 * @author liurui
 * @date 2022-04-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("分类-图片关联业务对象")
public class CategoryOssBo extends BaseEntity {

    /**
     * 对象存储主键
     */
    @ApiModelProperty(value = "对象存储主键")
    private Long ossId;

    /**
     * 分类主键id
     */
    @ApiModelProperty(value = "分类主键id")
    private String catId;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String url;


}
