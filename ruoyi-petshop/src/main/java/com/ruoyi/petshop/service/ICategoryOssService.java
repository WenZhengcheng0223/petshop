package com.ruoyi.petshop.service;

import com.ruoyi.petshop.domain.CategoryOss;
import com.ruoyi.petshop.domain.vo.CategoryOssVo;
import com.ruoyi.petshop.domain.bo.CategoryOssBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 分类-图片关联Service接口
 *
 * @author liurui
 * @date 2022-04-28
 */
public interface ICategoryOssService {

    /**
     * 查询分类-图片关联
     *
     * @param ossId 分类-图片关联主键
     * @return 分类-图片关联
     */
    CategoryOssVo queryById(Long ossId);


    /**
     * 查询分类-图片关联列表
     *
     * @param categoryOss 分类-图片关联
     * @return 分类-图片关联集合
     */
    TableDataInfo<CategoryOssVo> queryPageList(CategoryOssBo bo, PageQuery pageQuery);

    /**
     * 查询分类-图片关联列表
     *
     * @param categoryOss 分类-图片关联
     * @return 分类-图片关联集合
     */
    List<CategoryOssVo> queryList(CategoryOssBo bo);

    /**
     * 修改分类-图片关联
     *
     * @param categoryOss 分类-图片关联
     * @return 结果
     */
    Boolean insertByBo(CategoryOssBo bo);

    /**
     * 修改分类-图片关联
     *
     * @param categoryOss 分类-图片关联
     * @return 结果
     */
    Boolean updateByBo(CategoryOssBo bo);

    /**
     * 校验并批量删除分类-图片关联信息
     *
     * @param ossIds 需要删除的分类-图片关联主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
