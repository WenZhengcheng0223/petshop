package com.ruoyi.petshop.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 轮播图对象 sp_swiper
 *
 * @author liurui
 * @date 2022-04-29
 */
@Data
@TableName("sp_swiper")
public class Swiper extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 图片主键
     */
    @TableId(value = "oss_id")
    private Long ossId;
    /**
     * 图片名称
     */
    private String fileName;
    /**
     * 图片状态
     */
    private Integer status;
    /**
     * 图片展示
     */
    private String url;

}
