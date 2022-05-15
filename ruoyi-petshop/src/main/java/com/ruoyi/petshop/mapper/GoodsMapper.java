package com.ruoyi.petshop.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.petshop.domain.Goods;
import com.ruoyi.petshop.domain.vo.GoodsVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品Mapper接口
 *
 * @author ruoyi
 * @date 2022-04-26
 */
public interface GoodsMapper extends BaseMapperPlus<GoodsMapper, Goods, GoodsVo> {
    List<GoodsVo> queryPageListOss(
            @Param("ew") Wrapper<Goods> wrapper,
            @Param("start") Integer start,
            @Param("size") Integer size);

    GoodsVo selectVoByIdAndOss( @Param("ew") Wrapper<Goods> wrapper);

}
