package com.ruoyi.petshop.service;

import com.ruoyi.petshop.domain.WxUser;
import com.ruoyi.petshop.domain.vo.WxUserVo;
import com.ruoyi.petshop.domain.bo.WxUserBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 会员Service接口
 *
 * @author liurui
 * @date 2022-04-30
 */
public interface IWxUserService {

    /**
     * 查询会员
     *
     * @param userId 会员主键
     * @return 会员
     */
    WxUserVo queryById(Long userId);


    /**
     * 查询会员
     *
     * @param openId 微信openid
     * @return 会员
     */
    WxUser queryByOpenId(String openId);
    /**
     * 查询会员列表
     *
     * @param bo 会员
     * @return 会员集合
     */
    TableDataInfo<WxUserVo> queryPageList(WxUserBo bo, PageQuery pageQuery);

    /**
     * 查询会员列表
     *
     * @param bo 会员
     * @return 会员集合
     */
    List<WxUserVo> queryList(WxUserBo bo);

    abstract Boolean insertByBo(WxUserBo bo);

    WxUser insertByOpenId(WxUserBo bo);

    /**
     * 修改会员
     *
     * @param wxUser 会员
     * @return 结果
     */
    Boolean updateByBo(WxUser wxUser);

    /**
     * 校验并批量删除会员信息
     *
     * @param userIds 需要删除的会员主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> userIds, Boolean isValid);
}
