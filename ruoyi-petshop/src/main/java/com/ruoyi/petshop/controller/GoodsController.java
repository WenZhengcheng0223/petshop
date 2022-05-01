package com.ruoyi.petshop.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.petshop.domain.bo.GoodsBo;
import com.ruoyi.petshop.domain.bo.GoodsOssBo;
import com.ruoyi.petshop.domain.vo.GoodsOssVo;
import com.ruoyi.petshop.domain.vo.GoodsVo;
import com.ruoyi.petshop.service.IGoodsOssService;
import com.ruoyi.petshop.service.IGoodsService;
import com.ruoyi.system.domain.SysOss;
import com.ruoyi.system.service.ISysOssService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 商品Controller
 *
 * @author ruoyi
 * @date 2022-04-26
 */
@Validated
@Api(value = "商品控制器", tags = {"商品管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/goods/detail")
public class GoodsController extends BaseController {

    private final IGoodsService iGoodsService;
    private final IGoodsOssService iGoodsOssService;
    private final ISysOssService iSysOssService;

    /**
     * 查询商品列表
     */
    @ApiOperation("查询商品列表")
    @SaCheckPermission("goods:detail:list")
    @GetMapping("/list")
    public TableDataInfo<GoodsVo> list(
            @Validated(QueryGroup.class) GoodsBo bo, PageQuery pageQuery) {
        return iGoodsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出商品列表
     */
    @ApiOperation("导出商品列表")
    @SaCheckPermission("goods:detail:export")
    @Log(title = "商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated GoodsBo bo, HttpServletResponse response) {
        List<GoodsVo> list = iGoodsService.queryList(bo);
        ExcelUtil.exportExcel(list, "商品", GoodsVo.class, response);
    }

    /**
     * 获取商品详细信息
     */
    @ApiOperation("获取商品详细信息")
    @SaCheckPermission("goods:detail:query")
    @GetMapping("/{goodsId}")
    public R<GoodsVo> getInfo(@ApiParam("主键")
                              @NotNull(message = "主键不能为空")
                              @PathVariable("goodsId") String goodsId) {
        GoodsVo goodsVo = iGoodsService.queryById(goodsId);
        if (ObjectUtil.isNotNull(goodsVo)) {
            GoodsOssBo bo = new GoodsOssBo();
            bo.setGoodsId(goodsVo.getGoodsId());
            List<GoodsOssVo> voList = iGoodsOssService.queryList(bo);
            goodsVo.setGoodsOss(voList);
            return R.ok(goodsVo);
        } else {
            return R.fail();
        }
    }

    /**
     * 新增商品
     */
    @ApiOperation("新增商品")
    @SaCheckPermission("goods:detail:add")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody GoodsBo bo) {
        List<GoodsOssBo> goodsOss = bo.getGoodsOss();
        GoodsBo insertByBo = (GoodsBo) iGoodsService.insertByBo(bo);
        if (ObjectUtil.isNotNull(goodsOss)) {
            goodsOss.forEach(goodsOssBo -> {
                goodsOssBo.setGoodsId(insertByBo.getGoodsId());
                iGoodsOssService.insertByBo(goodsOssBo);
            });
        }
        return toAjax( ObjectUtil.isNotNull(insertByBo) ? 1 : 0);
    }

    /**
     * 修改商品
     */
    @ApiOperation("修改商品")
    @SaCheckPermission("goods:detail:edit")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody GoodsBo bo) {
        System.out.println(bo);
        List<GoodsOssBo> goodsOss = bo.getGoodsOss();
        goodsOss.forEach(goodsOssBo -> {
            GoodsOssVo vo = iGoodsOssService.queryById(goodsOssBo.getOssId());
            if (ObjectUtil.isNotNull(vo)) {
                iGoodsOssService.updateByBo(goodsOssBo);
            } else {
                goodsOssBo.setGoodsId(bo.getGoodsId());
                iGoodsOssService.insertByBo(goodsOssBo);
            }
        });
        return toAjax(iGoodsService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除商品
     */
    @ApiOperation("删除商品")
    @SaCheckPermission("goods:detail:remove")
    @Log(title = "商品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{goodsIds}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable String[] goodsIds) {
        return toAjax(iGoodsService.deleteWithValidByIds(Arrays.asList(goodsIds), true) ? 1 : 0);
    }

    @ApiOperation("上传图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "query", dataTypeClass = File.class, required = true)
    })
    @Log(title = "OSS对象存储", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    public R<Map<String, String>> upload(@RequestPart("file") MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            throw new ServiceException("上传文件不能为空");
        }
        SysOss oss = iSysOssService.upload(file);
        Map<String, String> map = new HashMap<>(2);
        map.put("url", oss.getUrl());
        map.put("fileName", oss.getOriginalName());
        map.put("ossId", oss.getOssId().toString());
        return R.ok(map);
    }

}
