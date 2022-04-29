package com.ruoyi.petshop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.petshop.domain.bo.CategoryOssBo;
import com.ruoyi.petshop.domain.vo.CategoryOssVo;
import com.ruoyi.petshop.domain.CategoryOss;
import com.ruoyi.petshop.mapper.CategoryOssMapper;
import com.ruoyi.petshop.service.ICategoryOssService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Objects;

/**
 * 分类-图片关联Service业务层处理
 *
 * @author liurui
 * @date 2022-04-28
 */
@RequiredArgsConstructor
@Service
public class CategoryOssServiceImpl implements ICategoryOssService {

    private final CategoryOssMapper baseMapper;

    /**
     * 查询分类-图片关联
     *
     * @param ossId 分类-图片关联主键
     * @return 分类-图片关联
     */
    @Override
    public CategoryOssVo queryById(Long ossId){
        return baseMapper.selectVoById(ossId);
    }

    /**
     * 查询分类-图片关联列表
     *
     * @param bo 分类-图片关联
     * @return 分类-图片关联
     */
    @Override
    public TableDataInfo<CategoryOssVo> queryPageList(CategoryOssBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CategoryOss> lqw = buildQueryWrapper(bo);
        Page<CategoryOssVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询分类-图片关联列表
     *
     * @param bo 分类-图片关联
     * @return 分类-图片关联
     */
    @Override
    public List<CategoryOssVo> queryList(CategoryOssBo bo) {
        LambdaQueryWrapper<CategoryOss> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CategoryOss> buildQueryWrapper(CategoryOssBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CategoryOss> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getOssId() != null, CategoryOss::getOssId, bo.getOssId());
        lqw.eq(Objects.nonNull(bo.getCatId()), CategoryOss::getCatId, bo.getCatId());
        lqw.eq(StringUtils.isNotBlank(bo.getUrl()), CategoryOss::getUrl, bo.getUrl());
        return lqw;
    }

    /**
     * 新增分类-图片关联
     *
     * @param bo 分类-图片关联
     * @return 结果
     */
    @Override
    public Boolean insertByBo(CategoryOssBo bo) {
        CategoryOss add = BeanUtil.toBean(bo, CategoryOss.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOssId(add.getOssId());
        }
        return flag;
    }

    /**
     * 修改分类-图片关联
     *
     * @param bo 分类-图片关联
     * @return 结果
     */
    @Override
    public Boolean updateByBo(CategoryOssBo bo) {
        CategoryOss update = BeanUtil.toBean(bo, CategoryOss.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(CategoryOss entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除分类-图片关联
     *
     * @param ids 需要删除的分类-图片关联主键
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
