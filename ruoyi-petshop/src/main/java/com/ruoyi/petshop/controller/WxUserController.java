package com.ruoyi.petshop.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.petshop.domain.WxUser;
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
import com.ruoyi.petshop.domain.vo.WxUserVo;
import com.ruoyi.petshop.domain.bo.WxUserBo;
import com.ruoyi.petshop.service.IWxUserService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 会员Controller
 *
 * @author liurui
 * @date 2022-04-30
 */
@Validated
@Api(value = "会员控制器", tags = {"会员管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/person/detail")
public class WxUserController extends BaseController {

    private final IWxUserService iWxUserService;

    /**
     * 查询会员列表
     */
    @ApiOperation("查询会员列表")
    @SaCheckPermission("person:detail:list")
    @GetMapping("/list")
    public TableDataInfo<WxUserVo> list(@Validated(QueryGroup.class) WxUserBo bo, PageQuery pageQuery) {
        return iWxUserService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员列表
     */
    @ApiOperation("导出会员列表")
    @SaCheckPermission("person:detail:export")
    @Log(title = "会员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated WxUserBo bo, HttpServletResponse response) {
        List<WxUserVo> list = iWxUserService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员", WxUserVo.class, response);
    }

    /**
     * 获取会员详细信息
     */
    @ApiOperation("获取会员详细信息")
    @SaCheckPermission("person:detail:query")
    @GetMapping("/{userId}")
    public R<WxUserVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("userId") Long userId) {
        return R.ok(iWxUserService.queryById(userId));
    }

    /**
     * 新增会员
     */
    @ApiOperation("新增会员")
    @SaCheckPermission("person:detail:add")
    @Log(title = "会员", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody WxUserBo bo) {
        return toAjax(iWxUserService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改会员
     */
    @ApiOperation("修改会员")
    @SaCheckPermission("person:detail:edit")
    @Log(title = "会员", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WxUserBo bo) {
        return toAjax(iWxUserService.updateByBo(BeanUtil.toBean(bo, WxUser.class)) ? 1 : 0);
    }

    /**
     * 删除会员
     */
    @ApiOperation("删除会员")
    @SaCheckPermission("person:detail:remove")
    @Log(title = "会员", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] userIds) {
        return toAjax(iWxUserService.deleteWithValidByIds(Arrays.asList(userIds), true) ? 1 : 0);
    }
}
