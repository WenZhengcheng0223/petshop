package com.ruoyi.petshop.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.petshop.service.ICategoryOssService;
import com.ruoyi.petshop.service.impl.CategoryOssServiceImpl;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ruoyi.petshop.domain.vo.CategoryVo;
import com.ruoyi.petshop.domain.bo.CategoryBo;
import com.ruoyi.petshop.service.ICategoryService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 分类Controller
 *
 * @author liurui
 * @date 2022-04-26
 */
@Validated
@Api(value = "分类控制器", tags = {"分类管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/goods/category")
public class CategoryController extends BaseController {

    private final ICategoryService iCategoryService;


    /**
     * 查询分类列表
     */
    @ApiOperation("查询分类列表")
    @SaCheckPermission("goods:category:list")
    @GetMapping("/list")
    public R<List<CategoryVo>> list(@Validated(QueryGroup.class) CategoryBo bo) {
        List<CategoryVo> list = iCategoryService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 导出分类列表
     */
    @ApiOperation("导出分类列表")
    @SaCheckPermission("goods:category:export")
    @Log(title = "分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated CategoryBo bo, HttpServletResponse response) {
        List<CategoryVo> list = iCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "分类", CategoryVo.class, response);
    }

    /**
     * 获取分类详细信息
     */
    @ApiOperation("获取分类详细信息")
    @SaCheckPermission("goods:category:query")
    @GetMapping("/{catId}")
    public R<CategoryVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("catId") Long catId) {
        return R.ok(iCategoryService.queryById(catId));
    }

    /**
     * 新增分类
     */
    @ApiOperation("新增分类")
    @SaCheckPermission("goods:category:add")
    @Log(title = "分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CategoryBo bo) {
        return toAjax(iCategoryService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改分类
     */
    @ApiOperation("修改分类")
    @SaCheckPermission("goods:category:edit")
    @Log(title = "分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CategoryBo bo) {
        return toAjax(iCategoryService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除分类
     */
    @ApiOperation("删除分类")
    @SaCheckPermission("goods:category:remove")
    @Log(title = "分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{catIds}")
    public R<Void> remove(@ApiParam("主键串")
                                        @NotEmpty(message = "主键不能为空")
                                        @PathVariable Long[] catIds) {
        return toAjax(iCategoryService.deleteWithValidByIds(Arrays.asList(catIds), true) ? 1 : 0);
    }

    @ApiOperation("上传单个OSS对象存储")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "query", dataTypeClass = File.class, required = true)
    })

    @Log(title = "OSS单一对象存储", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    public R<Object> upload(@RequestPart("file") MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            throw new ServiceException("上传文件不能为空");
        }
        String url = iCategoryService.uploadOne(file);
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        return R.ok(map);
    }



    /**
     * 删除OSS对象存储
     */
    @ApiOperation("删除OSS对象存储")
    @Log(title = "OSS对象存储", businessType = BusinessType.DELETE)
    @DeleteMapping("/img/{catId}")
    public R<Void> remove(@ApiParam("OSS对象ID串")
                          @NotNull(message = "主键不能为空")
                          @PathVariable Long catId,
                          @ApiParam("需要删除的图片的url地址")
                          @NotEmpty(message = "图片地址不能为空") String url
                          ) {
        return toAjax(iCategoryService.deleteImg(catId,url));
    }

}
