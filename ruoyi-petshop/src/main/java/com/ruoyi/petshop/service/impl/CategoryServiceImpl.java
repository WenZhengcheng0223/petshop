package com.ruoyi.petshop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.oss.entity.UploadResult;
import com.ruoyi.oss.factory.OssFactory;
import com.ruoyi.oss.service.IOssStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.petshop.domain.bo.CategoryBo;
import com.ruoyi.petshop.domain.vo.CategoryVo;
import com.ruoyi.petshop.domain.Category;
import com.ruoyi.petshop.mapper.CategoryMapper;
import com.ruoyi.petshop.service.ICategoryService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 分类Service业务层处理
 *
 * @author liurui
 * @date 2022-04-26
 */
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryMapper baseMapper;

    /**
     * 查询分类
     *
     * @param catId 分类主键
     * @return 分类
     */
    @Override
    public CategoryVo queryById(Long catId){

        return baseMapper.selectVoById(catId);
    }


    /**
     * 查询分类列表
     *
     * @param bo 分类
     * @return 分类
     */
    @Override
    public List<CategoryVo> queryList(CategoryBo bo) {
        LambdaQueryWrapper<Category> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Category> buildQueryWrapper(CategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Category> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCatName()), Category::getCatName, bo.getCatName());
        lqw.eq(bo.getCatLevel() != null, Category::getCatLevel, bo.getCatLevel());
        lqw.eq(bo.getCatDeleted() != null, Category::getCatDeleted, bo.getCatDeleted());
        return lqw;
    }

    /**
     * 新增分类
     *
     * @param bo 分类
     * @return 结果
     */
    @Override
    public Boolean insertByBo(CategoryBo bo) {
        Category add = BeanUtil.toBean(bo, Category.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCatId(add.getCatId());
        }
        return flag;
    }

    /**
     * 修改分类
     *
     * @param bo 分类
     * @return 结果
     */
    @Override
    public Boolean updateByBo(CategoryBo bo) {
        Category update = BeanUtil.toBean(bo, Category.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(Category entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除分类
     *
     * @param ids 需要删除的分类主键
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
    public String uploadOne(MultipartFile file) {
        String originalfileName = file.getOriginalFilename();
        String suffix = StringUtils.substring(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        IOssStrategy storage = OssFactory.instance();
        UploadResult uploadResult;
        try {
            uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
        } catch (IOException e) {
            throw new ServiceException(e.getLocalizedMessage());
        }

        return uploadResult.getUrl();
    }

    @Override
    public Integer deleteImg(Long catId, String url) {
        Category category = new Category();
        category.setCatId(catId);
        category.setCatIcon("");
        int i = baseMapper.updateById(category);
        if (i < 1){
            return i;
        }
        IOssStrategy strategy = OssFactory.instance();
        strategy.delete(url);
        return i;

    }
}
