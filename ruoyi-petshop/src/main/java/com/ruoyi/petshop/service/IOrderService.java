package com.ruoyi.petshop.service;

import com.ruoyi.petshop.domain.vo.OrderVo;
import com.ruoyi.petshop.domain.bo.OrderBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单Service接口
 *
 * @author ruoyi
 * @date 2022-04-26
 */
public interface IOrderService {

    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    OrderVo queryById(String orderId);

    /**
     * 查询订单列表
     *
     * @param bo 订单
     * @return 订单集合
     */
    TableDataInfo<OrderVo> queryPageList(OrderBo bo, PageQuery pageQuery);

    /**
     * 查询订单列表
     *
     * @param bo 订单
     * @return 订单集合
     */
    List<OrderVo> queryList(OrderBo bo);

    /**
     * 新增订单
     *
     * @param bo 订单
     * @return 结果
     */

    boolean insertByBo(OrderBo bo);
    /**
     * 修改订单
     *
     * @param bo 订单
     * @return 结果
     */
    Boolean updateById(OrderBo bo);


    /**
     * 修改订单
     *
     * @param bo 订单
     * @return
     */
    boolean updateByOrderNum(OrderBo bo);

    /**
     * 校验并批量删除订单信息
     *
     * @param ids 需要删除的订单主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);

}
