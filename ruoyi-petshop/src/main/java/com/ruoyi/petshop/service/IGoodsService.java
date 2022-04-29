package com.ruoyi.petshop.service;

import com.ruoyi.petshop.domain.Goods;
import com.ruoyi.petshop.domain.vo.GoodsVo;
import com.ruoyi.petshop.domain.bo.GoodsBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 商品Service接口
 *
 * @author ruoyi
 * @date 2022-04-26
 */
public interface IGoodsService {

    /**
     * 查询商品
     *
     * @param goodsId 商品主键
     * @return 商品
     */
    GoodsVo queryById(String goodsId);

    /**
     * 查询商品列表
     *
     * @param goods 商品
     * @return 商品集合
     */
    TableDataInfo<GoodsVo> queryPageList(GoodsBo bo, PageQuery pageQuery);

    /**
     * 查询商品列表
     *
     * @param goods 商品
     * @return 商品集合
     */
    List<GoodsVo> queryList(GoodsBo bo);

    /**
     * 修改商品
     *
     * @param goods 商品
     * @return 结果
     */
    Boolean insertByBo(GoodsBo bo);

    /**
     * 修改商品
     *
     * @param goods 商品
     * @return 结果
     */
    Boolean updateByBo(GoodsBo bo);

    /**
     * 校验并批量删除商品信息
     *
     * @param goodsIds 需要删除的商品主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
