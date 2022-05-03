package com.ruoyi.petshop.wx.api;

import cn.hutool.core.lang.tree.Tree;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.TreeBuildUtils;
import com.ruoyi.petshop.domain.bo.CategoryBo;
import com.ruoyi.petshop.domain.bo.GoodsBo;
import com.ruoyi.petshop.domain.vo.CategoryVo;
import com.ruoyi.petshop.domain.vo.GoodsVo;
import com.ruoyi.petshop.domain.vo.SwiperVo;
import com.ruoyi.petshop.service.ICategoryService;
import com.ruoyi.petshop.service.IGoodsService;
import com.ruoyi.petshop.service.ISwiperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: RuoYi-Vue-Plus
 * @Author: WenZhengcheng
 * @Date: 2022-5-1 下午 08:30
 * @Desc:
 */
@Api(value = "小程序通用请求控制器", tags = {"微信小程序通用请求"})
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wx/common")
public class CommonController {

    private final ISwiperService swiperService;
    private final ICategoryService categoryService;
    private final IGoodsService goodsService;


    @ApiOperation("轮播图")
    @GetMapping("/swiper")
    public R<List<SwiperVo>> swiper() {

        return R.ok(swiperService.queryUsableList());
    }


    @ApiOperation("获取树形分类信息")
    @GetMapping("/category")
    public R<List<Tree<Object>>> wxCategory() {
        List<CategoryVo> voList = categoryService.queryList(new CategoryBo());
        List<Tree<Object>> treeList = TreeBuildUtils.build(voList, (list, tree) -> {
            tree.setId(list.getCatId())
                    .setParentId(list.getCatPid())
                    .setName(list.getCatName())
                    .setWeight(list.getCatLevel()).putExtra("catLevel", list.getCatLevel());
        });
        return R.ok(treeList);
    }


    @ApiOperation("获取商品列表")
    @PostMapping("goods")
    public R<TableDataInfo<GoodsVo>> goodsList(GoodsBo bo, PageQuery pageQuery) {
        return R.ok(goodsService.queryPageList(bo, pageQuery));
    }


    @ApiOperation("查询商品详情")
    @GetMapping("/goods/{goodsId}")
    public R<GoodsVo> goodsDetail(
            @ApiParam("商品id")
            @PathVariable("goodsId") String goodsId) {

        return R.ok(goodsService.queryById(goodsId));
    }
}
