package com.ruoyi.petshop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.petshop.domain.Goods;
import com.ruoyi.petshop.domain.bo.GoodsBo;
import com.ruoyi.petshop.domain.vo.GoodsVo;
import com.ruoyi.petshop.mapper.GoodsMapper;
import com.ruoyi.petshop.service.IGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 商品Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-26
 */
@RequiredArgsConstructor
@Service
public class GoodsServiceImpl implements IGoodsService {

    private final GoodsMapper baseMapper;

    /**
     * 查询商品
     *
     * @param goodsId 商品主键
     * @return 商品
     */
    @Override
    public GoodsVo queryById(String goodsId) {
        GoodsVo goodsVo = baseMapper.selectVoById(goodsId);
        return goodsVo;
    }

    /**
     * 查询商品列表
     *
     * @param bo 商品
     * @return 商品
     */
    @Override
    public TableDataInfo<GoodsVo> queryPageList(GoodsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Goods> lqw = buildQueryWrapper(bo);
        Integer pageNum = pageQuery.getPageNum();
        Integer pageSize = pageQuery.getPageSize();
        List<GoodsVo> result = new ArrayList<>();
        Long totle = baseMapper.selectCount(lqw);
        Integer start = null;
        if(pageNum!=null && pageSize!=null){
            start = (pageNum - 1) * pageSize;
        }
        result = baseMapper.queryPageListOss(lqw, start, pageSize);
        return TableDataInfo.build(result, totle);


    }

    /**
     * 查询商品列表
     *
     * @param bo 商品
     * @return 商品
     */
    @Override
    public List<GoodsVo> queryList(GoodsBo bo) {
        LambdaQueryWrapper<Goods> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Goods> buildQueryWrapper(GoodsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Goods> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getGoodsName()), Goods::getGoodsName, bo.getGoodsName());
        lqw.eq(StringUtils.isNotBlank(bo.getIsDel()), Goods::getIsDel, bo.getIsDel());
        lqw.eq(bo.getIsPromote() != null, Goods::getIsPromote, bo.getIsPromote());
        lqw.eq(bo.getGoodsState() != null, Goods::getGoodsState, bo.getGoodsState());
        return lqw;
    }

    /**
     * 新增商品
     *
     * @param bo 商品
     * @return 结果
     */
    @Override
    public Object insertByBo(GoodsBo bo) {
        Goods add = BeanUtil.toBean(bo, Goods.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setGoodsId(add.getGoodsId());
            return bo;
        }

        return false;

    }

    /**
     * 修改商品
     *
     * @param bo 商品
     * @return 结果
     */
    @Override
    public Boolean updateByBo(GoodsBo bo) {

        Goods update = BeanUtil.toBean(bo, Goods.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(Goods entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的商品主键
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
