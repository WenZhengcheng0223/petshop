package com.ruoyi.petshop.service;

import com.ruoyi.petshop.domain.vo.CategoryVo;
import com.ruoyi.petshop.domain.bo.CategoryBo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * 分类Service接口
 *
 * @author liurui
 * @date 2022-04-26
 */
public interface ICategoryService {

    /**
     * 查询分类
     *
     * @param catId 分类主键
     * @return 分类
     */
    CategoryVo queryById(Long catId);


    /**
     * 查询分类列表
     *
     * @param category 分类
     * @return 分类集合
     */
    List<CategoryVo> queryList(CategoryBo bo);

    /**
     * 修改分类
     *
     * @param category 分类
     * @return 结果
     */
    Boolean insertByBo(CategoryBo bo);

    /**
     * 修改分类
     *
     * @param category 分类
     * @return 结果
     */
    Boolean updateByBo(CategoryBo bo);

    /**
     * 校验并批量删除分类信息
     *
     * @param catIds 需要删除的分类主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 图片上传，上传单个图片到分类表
     * @param file
     * @return
     */
    String uploadOne(MultipartFile file);

    /**
     * 根据分类Id 删除图片地址
     * @param catId
     * @param url
     * @return
     */
    Integer deleteImg(Long catId, String url);
}
