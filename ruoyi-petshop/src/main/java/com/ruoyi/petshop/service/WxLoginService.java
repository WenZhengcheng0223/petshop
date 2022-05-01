package com.ruoyi.petshop.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.service.LogininforService;
import com.ruoyi.common.enums.DeviceType;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.WxMaConfiguration;
import com.ruoyi.petshop.domain.WxLoginUser;
import com.ruoyi.petshop.domain.WxUser;
import com.ruoyi.petshop.domain.vo.WxUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: RuoYi-Vue-Plus
 * @Author: WenZhengcheng
 * @Date: 2022-4-30 下午 02:56
 * @Desc:
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class WxLoginService {


    private final IWxUserService wxUserService;
    private final LogininforService asyncService;

    /**
     * 微信小程序登陆
     *
     * @param code 临时登陆code
     * @return token
     */
    public String miniAppLogin(String appid,String code) throws WxErrorException {

        HttpServletRequest request = ServletUtils.getRequest();
        // xcxCode 为 小程序调用 wx.login 授权后获取
        // todo 以下自行实现
        // 校验 appid + appsrcret + xcxCode 调用登录凭证校验接口 获取 session_key 与 openid
        final WxMaService wxMaService = WxMaConfiguration.getMaService(appid);
        WxMaJscode2SessionResult jscode2SessionResult = wxMaService.jsCode2SessionInfo(code);
        log.info("微信服务器返回的信息：{}", jscode2SessionResult);
        String openid = jscode2SessionResult.getOpenid();
        WxUser user = loadUserByOpenid(openid);

        // 此处可根据登录用户的数据不同 自行创建 loginUser
        WxLoginUser loginUser = new WxLoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUsername(user.getNickName());
        loginUser.setOpenId(openid);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.XCX);

        asyncService.recordLogininfor(user.getNickName(), Constants.LOGIN_SUCCESS, MessageUtils.message("wx.user.login.success"), request);
        recordLoginInfo(user.getUserId(), user.getNickName());
        return StpUtil.getTokenValue();

    }

    /**
     * 小程序退出登陆
     *
     * @param openId 小程序用户openid
     */
    void miniAppLoginOut(String openId) {

    }


    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId, String username) {
        WxUser wxUser = new WxUser();
        wxUser.setUserId(userId);
        wxUser.setLoginIp(ServletUtils.getClientIP());
        wxUser.setLoginDate(DateUtils.getNowDate());
        wxUser.setUpdateBy(username);
        wxUserService.updateByBo(wxUser);
    }


    private WxUser loadUserByOpenid(String openid) {

        // todo  自行实现 userService.selectUserByOpenid(openid);

        WxUser wxUser = wxUserService.queryByOpenId(openid);
        if (ObjectUtil.isNull(wxUser)) {
            log.info("登录用户：{} 不存在.", openid);
            // todo 用户不存在 业务逻辑自行实现
        } else if (UserStatus.DISABLE.getCode().equals(String.valueOf(wxUser.getStatus()))) {
            log.info("登录用户：{} 已被停用.", openid);
            // todo 用户已被停用 业务逻辑自行实现
        }
        return wxUser;
    }
}
