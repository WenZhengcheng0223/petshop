package com.ruoyi.petshop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.petshop.domain.Order;
import com.ruoyi.petshop.domain.bo.OrderBo;
import com.ruoyi.petshop.domain.vo.OrderVo;
import com.ruoyi.petshop.mapper.OrderMapper;
import com.ruoyi.petshop.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 订单Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-26
 */
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderMapper baseMapper;

    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    @Override
    public OrderVo queryById(String orderId) {
        return baseMapper.selectVoById(orderId);
    }

    /**
     * 查询订单列表
     *
     * @param bo 订单
     * @return 订单
     */
    @Override
    public TableDataInfo<OrderVo> queryPageList(OrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Order> lqw = buildQueryWrapper(bo);
        Page<OrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询订单列表
     *
     * @param bo 订单
     * @return 订单
     */
    @Override
    public List<OrderVo> queryList(OrderBo bo) {
        LambdaQueryWrapper<Order> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    /**
     * 新增订单
     *
     * @param bo 订单
     * @return 结果
     */
    @Override
    public boolean insertByBo(OrderBo bo) {
        Order order = BeanUtil.toBean(bo, Order.class);
        boolean flag = baseMapper.insert(order) > 0;
        if (flag){
            bo.setOrderId(order.getOrderId());
        }
        return flag;
    }

    private LambdaQueryWrapper<Order> buildQueryWrapper(OrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Order> lqw = Wrappers.lambdaQuery();

        lqw.eq(StringUtils.isNotBlank(bo.getOrderNumber()), Order::getOrderNumber, bo.getOrderNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getUserId()), Order::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderPay()), Order::getOrderPay, bo.getOrderPay());
        lqw.eq(StringUtils.isNotBlank(bo.getIsSend()), Order::getIsSend, bo.getIsSend());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderFapiaoTitle()), Order::getOrderFapiaoTitle, bo.getOrderFapiaoTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderFapiaoCompany()), Order::getOrderFapiaoCompany, bo.getOrderFapiaoCompany());
        lqw.eq(StringUtils.isNotBlank(bo.getPayStatus()), Order::getPayStatus, bo.getPayStatus());
        return lqw;
    }

    private LambdaQueryWrapper<Order> buildUpdateWrapper(OrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Order> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrderNumber()), Order::getOrderNumber, bo.getOrderNumber());
        return lqw;
    }

    /**
     * 修改订单
     *
     * @param bo 订单
     * @return 结果
     */
    @Override
    public Boolean updateById(OrderBo bo) {
        Order update = BeanUtil.toBean(bo, Order.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 修改订单
     *
     * @param bo 订单
     * @return
     */
    @Override
    public boolean updateByOrderNum(OrderBo bo) {
        Order update = BeanUtil.toBean(bo, Order.class);
        LambdaQueryWrapper<Order> lqw = buildUpdateWrapper(bo);
        return baseMapper.update(update, lqw) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(Order entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的订单主键
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
