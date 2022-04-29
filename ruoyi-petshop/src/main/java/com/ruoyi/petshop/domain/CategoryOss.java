package com.ruoyi.petshop.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 分类-图片关联对象 sp_category_oss
 *
 * @author liurui
 * @date 2022-04-28
 */
@Data
@TableName("sp_category_oss")
public class CategoryOss extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 对象存储主键
     */
    @TableId(value = "oss_id")
    private Long ossId;
    /**
     * 分类主键id
     */
    private Long catId;
    /**
     * 图片地址
     */
    @TableField(value = "img_url")
    private String url;

}
