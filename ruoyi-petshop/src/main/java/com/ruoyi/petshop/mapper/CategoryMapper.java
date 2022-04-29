package com.ruoyi.petshop.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.ruoyi.petshop.domain.Category;
import com.ruoyi.petshop.domain.vo.CategoryVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类Mapper接口
 *
 * @author liurui
 * @date 2022-04-26
 */
public interface CategoryMapper extends BaseMapperPlus<CategoryMapper, Category, CategoryVo> {
    List<CategoryVo> queryList(@Param("ew")Wrapper<Category> wrapper);
}
