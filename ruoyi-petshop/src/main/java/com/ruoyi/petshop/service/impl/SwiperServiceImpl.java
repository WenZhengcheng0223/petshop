package com.ruoyi.petshop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.oss.entity.UploadResult;
import com.ruoyi.oss.factory.OssFactory;
import com.ruoyi.oss.service.IOssStrategy;
import com.ruoyi.system.domain.SysOss;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.petshop.domain.bo.SwiperBo;
import com.ruoyi.petshop.domain.vo.SwiperVo;
import com.ruoyi.petshop.domain.Swiper;
import com.ruoyi.petshop.mapper.SwiperMapper;
import com.ruoyi.petshop.service.ISwiperService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 轮播图Service业务层处理
 *
 * @author liurui
 * @date 2022-04-29
 */
@RequiredArgsConstructor
@Service
public class SwiperServiceImpl implements ISwiperService {

    private final SwiperMapper baseMapper;

    private static final String USABLE_STATUS = "1";
    /**
     * 查询轮播图
     *
     * @param ossId 轮播图主键
     * @return 轮播图
     */
    @Override
    public SwiperVo queryById(Long ossId){
        return baseMapper.selectVoById(ossId);
    }

    /**
     * 查询轮播图列表
     *
     * @param bo 轮播图
     * @return 轮播图
     */
    @Override
    public TableDataInfo<SwiperVo> queryPageList(SwiperBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Swiper> lqw = buildQueryWrapper(bo);
        Page<SwiperVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询轮播图列表
     *
     * @param bo 轮播图
     * @return 轮播图
     */
    @Override
    public List<SwiperVo> queryList(SwiperBo bo) {
        LambdaQueryWrapper<Swiper> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    /**
     * 查询可用轮播图列表
     *
     *
     * @return 轮播图
     */
    @Override
    public List<SwiperVo> queryUsableList() {
        LambdaQueryWrapper<Swiper> lqw = Wrappers.lambdaQuery();
        lqw.eq(Swiper::getStatus,USABLE_STATUS);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Swiper> buildQueryWrapper(SwiperBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Swiper> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getFileName()), Swiper::getFileName, bo.getFileName());
        lqw.eq(bo.getStatus() != null, Swiper::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增轮播图
     *
     * @param bo 轮播图
     * @return 结果
     */
    @Override
    public Boolean insertByBo(SwiperBo bo) {
        Swiper add = BeanUtil.toBean(bo, Swiper.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOssId(add.getOssId());
        }
        return flag;
    }

    /**
     * 修改轮播图
     *
     * @param bo 轮播图
     * @return 结果
     */
    @Override
    public Boolean updateByBo(SwiperBo bo) {
        Swiper update = BeanUtil.toBean(bo, Swiper.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(Swiper entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除轮播图
     *
     * @param ossIds 需要删除的轮播图主键
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ossIds, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ossIds) > 0;
    }

    @Override
    public Swiper upload(MultipartFile file) {
        String originalfileName = file.getOriginalFilename();
        String suffix = StringUtils.substring(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        IOssStrategy storage = OssFactory.instance();
        UploadResult uploadResult;
        try {
            uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
        // 保存文件信息
        Swiper oss = new Swiper();
        oss.setStatus(0);
        oss.setUrl(uploadResult.getUrl());
        oss.setFileName(uploadResult.getFilename());
        baseMapper.insert(oss);
        return oss;
    }
}
