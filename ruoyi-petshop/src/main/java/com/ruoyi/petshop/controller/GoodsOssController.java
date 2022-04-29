package com.ruoyi.petshop.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.petshop.domain.vo.GoodsOssVo;
import com.ruoyi.petshop.domain.bo.GoodsOssBo;
import com.ruoyi.petshop.service.IGoodsOssService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 商品图片关联Controller
 *
 * @author liurui
 * @date 2022-04-27
 */
@Validated
@Api(value = "商品图片关联控制器", tags = {"商品图片关联管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/goods/oss")
public class GoodsOssController extends BaseController {

    private final IGoodsOssService iGoodsOssService;

    /**
     * 查询商品图片关联列表
     */
    @ApiOperation("查询商品图片关联列表")
    @GetMapping("/list")
    public TableDataInfo<GoodsOssVo> list(@Validated(QueryGroup.class) GoodsOssBo bo, PageQuery pageQuery) {
        return iGoodsOssService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出商品图片关联列表
     */
    @ApiOperation("导出商品图片关联列表")
    @Log(title = "商品图片关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated GoodsOssBo bo, HttpServletResponse response) {
        List<GoodsOssVo> list = iGoodsOssService.queryList(bo);
        ExcelUtil.exportExcel(list, "商品图片关联", GoodsOssVo.class, response);
    }

    /**
     * 获取商品图片关联详细信息
     */
    @ApiOperation("获取商品图片关联详细信息")
    @GetMapping("/{ossId}")
    public R<GoodsOssVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("ossId") Long ossId) {
        return R.ok(iGoodsOssService.queryById(ossId));
    }

    /**
     * 新增商品图片关联
     */
    @ApiOperation("新增商品图片关联")
    @Log(title = "商品图片关联", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody GoodsOssBo bo) {
        return toAjax(iGoodsOssService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改商品图片关联
     */
    @ApiOperation("修改商品图片关联")
    @Log(title = "商品图片关联", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody GoodsOssBo bo) {
        return toAjax(iGoodsOssService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除商品图片关联
     */
    @ApiOperation("删除商品图片关联")
    @Log(title = "商品图片关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ossIds}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long ossIds) {
        return toAjax(iGoodsOssService.deleteOneById(ossIds) ? 1 : 0);

    }
}
