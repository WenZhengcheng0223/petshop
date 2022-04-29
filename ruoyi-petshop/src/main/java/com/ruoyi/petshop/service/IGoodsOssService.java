package com.ruoyi.petshop.service;

import com.ruoyi.petshop.domain.GoodsOss;
import com.ruoyi.petshop.domain.vo.GoodsOssVo;
import com.ruoyi.petshop.domain.bo.GoodsOssBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 商品图片关联Service接口
 *
 * @author liurui
 * @date 2022-04-27
 */
public interface IGoodsOssService {

    /**
     * 查询商品图片关联
     *
     * @param ossId 商品图片关联主键
     * @return 商品图片关联
     */
    GoodsOssVo queryById(Long ossId);

    /**
     * 查询商品图片关联列表
     *
     * @param goodsOss 商品图片关联
     * @return 商品图片关联集合
     */
    TableDataInfo<GoodsOssVo> queryPageList(GoodsOssBo bo, PageQuery pageQuery);

    /**
     * 查询商品图片关联列表
     *
     * @param goodsOss 商品图片关联
     * @return 商品图片关联集合
     */
    List<GoodsOssVo> queryList(GoodsOssBo bo);

    /**
     * 修改商品图片关联
     *
     * @param goodsOss 商品图片关联
     * @return 结果
     */
    Boolean insertByBo(GoodsOssBo bo);

    /**
     * 修改商品图片关联
     *
     * @param goodsOss 商品图片关联
     * @return 结果
     */
    Boolean updateByBo(GoodsOssBo bo);

    /**
     * 校验并批量删除商品图片关联信息
     *
     * @param ids 需要删除的商品图片关联主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 删除商品图片关联信息
     *
     * @param ossId 需要删除的商品图片关联主键
     * @return 结果
     */
    Boolean deleteOneById(Long ossId);
}
