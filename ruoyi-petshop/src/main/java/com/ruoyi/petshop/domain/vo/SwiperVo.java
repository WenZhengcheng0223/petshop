package com.ruoyi.petshop.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 轮播图视图对象 sp_swiper
 *
 * @author liurui
 * @date 2022-04-29
 */
@Data
@ApiModel("轮播图视图对象")
@ExcelIgnoreUnannotated
public class SwiperVo {

    private static final long serialVersionUID = 1L;

    /**
     * 图片主键
     */
    @ExcelProperty(value = "图片主键")
    @ApiModelProperty("图片主键")
    private Long ossId;

    /**
     * 图片名称
     */
    @ExcelProperty(value = "图片名称")
    @ApiModelProperty("图片名称")
    private String fileName;

    /**
     * 图片状态
     */
    @ExcelProperty(value = "图片状态")
    @ApiModelProperty("图片状态")
    private Integer status;

    /**
     * 图片展示
     */
    @ExcelProperty(value = "图片展示")
    @ApiModelProperty("图片展示")
    private String url;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 上传人
     */
    @ExcelProperty(value = "上传人")
    @ApiModelProperty("上传人")
    private String createBy;


}
