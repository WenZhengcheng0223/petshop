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
import com.ruoyi.petshop.domain.vo.CategoryOssVo;
import com.ruoyi.petshop.domain.bo.CategoryOssBo;
import com.ruoyi.petshop.service.ICategoryOssService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 分类-图片关联Controller
 *
 * @author liurui
 * @date 2022-04-28
 */
@Validated
@Api(value = "分类-图片关联控制器", tags = {"分类-图片关联管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/goods/categoryOss")
public class CategoryOssController extends BaseController {

    private final ICategoryOssService iCategoryOssService;

    /**
     * 查询分类-图片关联列表
     */
    @ApiOperation("查询分类-图片关联列表")
    @SaCheckPermission("goods:categoryOss:list")
    @GetMapping("/list")
    public TableDataInfo<CategoryOssVo> list(@Validated(QueryGroup.class) CategoryOssBo bo, PageQuery pageQuery) {
        return iCategoryOssService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出分类-图片关联列表
     */
    @ApiOperation("导出分类-图片关联列表")
    @SaCheckPermission("goods:categoryOss:export")
    @Log(title = "分类-图片关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated CategoryOssBo bo, HttpServletResponse response) {
        List<CategoryOssVo> list = iCategoryOssService.queryList(bo);
        ExcelUtil.exportExcel(list, "分类-图片关联", CategoryOssVo.class, response);
    }

    /**
     * 获取分类-图片关联详细信息
     */
    @ApiOperation("获取分类-图片关联详细信息")
    @SaCheckPermission("goods:categoryOss:query")
    @GetMapping("/{ossId}")
    public R<CategoryOssVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("ossId") Long ossId) {
        return R.ok(iCategoryOssService.queryById(ossId));
    }

    /**
     * 新增分类-图片关联
     */
    @ApiOperation("新增分类-图片关联")
    @SaCheckPermission("goods:categoryOss:add")
    @Log(title = "分类-图片关联", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CategoryOssBo bo) {
        return toAjax(iCategoryOssService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改分类-图片关联
     */
    @ApiOperation("修改分类-图片关联")
    @SaCheckPermission("goods:categoryOss:edit")
    @Log(title = "分类-图片关联", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CategoryOssBo bo) {
        return toAjax(iCategoryOssService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除分类-图片关联
     */
    @ApiOperation("删除分类-图片关联")
    @SaCheckPermission("goods:categoryOss:remove")
    @Log(title = "分类-图片关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ossIds}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ossIds) {
        return toAjax(iCategoryOssService.deleteWithValidByIds(Arrays.asList(ossIds), true) ? 1 : 0);
    }
}
