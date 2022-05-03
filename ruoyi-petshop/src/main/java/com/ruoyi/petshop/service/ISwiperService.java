package com.ruoyi.petshop.service;

import com.ruoyi.petshop.domain.Swiper;
import com.ruoyi.petshop.domain.vo.SwiperVo;
import com.ruoyi.petshop.domain.bo.SwiperBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * 轮播图Service接口
 *
 * @author liurui
 * @date 2022-04-29
 */
public interface ISwiperService {

    /**
     * 查询轮播图
     *
     * @param ossId 轮播图主键
     * @return 轮播图
     */
    SwiperVo queryById(Long ossId);

    /**
     * 查询轮播图列表
     *
     * @param SwiperBo 轮播图
     * @return 轮播图集合
     */
    TableDataInfo<SwiperVo> queryPageList(SwiperBo bo, PageQuery pageQuery);

    /**
     * 查询轮播图列表
     *
     * @param SwiperBo 轮播图
     * @return 轮播图集合
     */
    List<SwiperVo> queryList(SwiperBo bo);

    List<SwiperVo> queryUsableList();

    /**
     * 修改轮播图
     *
     * @param SwiperBo 轮播图
     * @return 结果
     */
    Boolean insertByBo(SwiperBo bo);

    /**
     * 修改轮播图
     *
     * @param SwiperBo 轮播图
     * @return 结果
     */
    Boolean updateByBo(SwiperBo bo);

    /**
     * 校验并批量删除轮播图信息
     *
     * @param ossIds 需要删除的轮播图主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ossIds, Boolean isValid);

    Swiper upload(MultipartFile file);
}
