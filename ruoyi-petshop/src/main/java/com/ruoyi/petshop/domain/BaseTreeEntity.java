package com.ruoyi.petshop.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree基类
 *
 * @author Lion Li
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseTreeEntity<T> extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父菜单名称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "父菜单名称")
    private String parentName;

    /**
     * 父菜单ID
     */
    @ApiModelProperty(value = "父菜单ID")
    @TableField(exist = false)
    private Long parentId;

    /**
     * 子部门
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "子部门")
    private List<T> children = new ArrayList<>();

}
