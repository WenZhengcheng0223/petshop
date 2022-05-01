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
import lombok.experimental.FieldDefaults;

/**
 * 轮播图业务对象 sp_swiper
 *
 * @author liurui
 * @date 2022-04-29
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("轮播图业务对象")
public class SwiperBo extends BaseEntity {

    /**
     * 图片主键
     */
    @ApiModelProperty(value = "图片主键", required = true)
    @NotNull(message = "图片主键不能为空", groups = { EditGroup.class })
    private Long ossId;

    /**
     * 图片名称
     */
    @ApiModelProperty(value = "图片名称")
    private String fileName;

    /**
     * 图片状态
     */
    @ApiModelProperty(value = "图片状态", required = true)
    @NotNull(message = "图片状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;

    /**
     * 图片展示
     */
    @ApiModelProperty(value = "图片展示", required = true)
    @NotBlank(message = "图片展示不能为空", groups = { AddGroup.class })
    private String url;


}
