package com.ruoyi.petshop.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 分类对象 sp_category
 *
 * @author liurui
 * @date 2022-04-26
 */
@Data
@TableName("sp_category")
public class Category extends BaseTreeEntity<Category> {

    private static final long serialVersionUID=1L;

    /**
     * 分类ID
     */
    @TableId(value = "cat_id",type = IdType.AUTO)
    private Long catId;
    /**
     * 分类名称
     */
    private String catName;
    /**
     * 分类父ID
     */
    private Long catPid;
    /**
     * 分类层级
     */
    private Long catLevel;
    /**
     * 是否删除
     */
    private Long catDeleted;
    /**
     * 图标
     */
    private String catIcon;
    /**
     * 图标地址
     */
    private String catSrc;

}
