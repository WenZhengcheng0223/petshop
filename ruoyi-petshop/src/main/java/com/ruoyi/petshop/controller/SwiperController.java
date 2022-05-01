package com.ruoyi.petshop.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.petshop.domain.Swiper;
import com.ruoyi.system.domain.SysOss;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.http.MediaType;
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
import com.ruoyi.petshop.domain.vo.SwiperVo;
import com.ruoyi.petshop.domain.bo.SwiperBo;
import com.ruoyi.petshop.service.ISwiperService;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 轮播图Controller
 *
 * @author liurui
 * @date 2022-04-29
 */
@Validated
@Api(value = "轮播图控制器", tags = {"轮播图管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/goods/swiper")
public class SwiperController extends BaseController {

    /**
     * 下载无读取权限
     */
    private static final String NO_DOWNLOAD_PERMISSION = "403";

    private final ISwiperService iSwiperService;

    /**
     * 查询轮播图列表
     */
    @ApiOperation("查询轮播图列表")
    @SaCheckPermission("goods:swiper:list")
    @GetMapping("/list")
    public TableDataInfo<SwiperVo> list(@Validated(QueryGroup.class) SwiperBo bo, PageQuery pageQuery) {
        return iSwiperService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出轮播图列表
     */
    @ApiOperation("导出轮播图列表")
    @SaCheckPermission("goods:swiper:export")
    @Log(title = "轮播图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated SwiperBo bo, HttpServletResponse response) {
        List<SwiperVo> list = iSwiperService.queryList(bo);
        ExcelUtil.exportExcel(list, "轮播图", SwiperVo.class, response);
    }

    /**
     * 获取轮播图详细信息
     */
    @ApiOperation("获取轮播图详细信息")
    @SaCheckPermission("goods:swiper:query")
    @GetMapping("/{ossId}")
    public R<SwiperVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("ossId") Long ossId) {
        return R.ok(iSwiperService.queryById(ossId));
    }

    /**
     * 新增轮播图
     */
    @ApiOperation("新增轮播图")
    @SaCheckPermission("goods:swiper:add")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SwiperBo bo) {
        Integer status = bo.getStatus();
        if (status==null){
            bo.setStatus(0);
        }
        return toAjax(iSwiperService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改轮播图
     */
    @ApiOperation("修改轮播图")
    @SaCheckPermission("goods:swiper:edit")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SwiperBo bo) {
        return toAjax(iSwiperService.updateByBo(bo) ? 1 : 0);
    }


    /**
     * 上传OSS对象存储
     */
    @ApiOperation("上传OSS对象存储")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "query", dataTypeClass = File.class, required = true)
    })
    @SaCheckPermission("goods:swiper:upload")
    @Log(title = "OSS对象存储", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    public R<Map<String, String>> upload(@RequestPart("file") MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            throw new ServiceException("上传文件不能为空");
        }
        Swiper oss = iSwiperService.upload(file);
        Map<String, String> map = new HashMap<>(2);
        map.put("url", oss.getUrl());
        map.put("ossId", oss.getOssId().toString());
        return R.ok(map);
    }




    @ApiOperation("下载轮播图图片")
    @SaCheckPermission("goods:swiper:download")
    @GetMapping("/download/{ossId}")
    public void download(@ApiParam("swiper对象OSS ID") @PathVariable Long ossId, HttpServletResponse response) throws IOException {
        SwiperVo vo = iSwiperService.queryById(ossId);
        if (ObjectUtil.isNull(vo)) {
            throw new ServiceException("文件数据不存在!");
        }
        response.reset();
        FileUtils.setAttachmentResponseHeader(response, vo.getFileName());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + "; charset=UTF-8");
        long data;
        try {
            data = HttpUtil.download(vo.getUrl(), response.getOutputStream(), false);
        } catch (HttpException e) {
            if (e.getMessage().contains(NO_DOWNLOAD_PERMISSION)) {
                throw new ServiceException("无读取权限, 请在对应的OSS开启'公有读'权限!");
            } else {
                throw new ServiceException(e.getMessage());
            }
        }
        response.setContentLength(Convert.toInt(data));
    }


    /**
     * 删除轮播图
     */
    @ApiOperation("删除轮播图")
    @SaCheckPermission("goods:swiper:remove")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ossIds}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ossIds) {

        return toAjax(iSwiperService.deleteWithValidByIds(Arrays.asList(ossIds), true) ? 1 : 0);
    }
}
