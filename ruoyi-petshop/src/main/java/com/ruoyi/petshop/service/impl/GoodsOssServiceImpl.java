package com.ruoyi.petshop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.petshop.domain.bo.GoodsOssBo;
import com.ruoyi.petshop.domain.vo.GoodsOssVo;
import com.ruoyi.petshop.domain.GoodsOss;
import com.ruoyi.petshop.mapper.GoodsOssMapper;
import com.ruoyi.petshop.service.IGoodsOssService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 商品图片关联Service业务层处理
 *
 * @author liurui
 * @date 2022-04-27
 */
@RequiredArgsConstructor
@Service
public class GoodsOssServiceImpl implements IGoodsOssService {

    private final GoodsOssMapper baseMapper;

    /**
     * 查询商品图片关联
     *
     * @param ossId 商品图片关联主键
     * @return 商品图片关联
     */
    @Override
    public GoodsOssVo queryById(Long ossId){
        return baseMapper.selectVoById(ossId);
    }

    /**
     * 查询商品图片关联列表
     *
     * @param bo 商品图片关联
     * @return 商品图片关联
     */
    @Override
    public TableDataInfo<GoodsOssVo> queryPageList(GoodsOssBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<GoodsOss> lqw = buildQueryWrapper(bo);
        Page<GoodsOssVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询商品图片关联列表
     *
     * @param bo 商品图片关联
     * @return 商品图片关联
     */
    @Override
    public List<GoodsOssVo> queryList(GoodsOssBo bo) {
        LambdaQueryWrapper<GoodsOss> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<GoodsOss> buildQueryWrapper(GoodsOssBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<GoodsOss> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getGoodsId()), GoodsOss::getGoodsId, bo.getGoodsId());
        return lqw;
    }

    /**
     * 新增商品图片关联
     *
     * @param bo 商品图片关联
     * @return 结果
     */
    @Override
    public Boolean insertByBo(GoodsOssBo bo) {
        GoodsOss add = BeanUtil.toBean(bo, GoodsOss.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOssId(add.getOssId());
        }
        return flag;
    }

    /**
     * 修改商品图片关联
     *
     * @param bo 商品图片关联
     * @return 结果
     */
    @Override
    public Boolean updateByBo(GoodsOssBo bo) {
        GoodsOss update = BeanUtil.toBean(bo, GoodsOss.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(GoodsOss entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除商品图片关联
     *
     * @param ids 需要删除的商品图片关联主键
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean deleteOneById(Long ossId) {
        return baseMapper.deleteById(ossId) == 1;
    }
}
