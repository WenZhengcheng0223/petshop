package com.ruoyi.petshop.wx.api;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.petshop.domain.WxUser;
import com.ruoyi.petshop.domain.bo.OrderBo;
import com.ruoyi.petshop.service.IOrderService;
import com.ruoyi.petshop.service.IWxUserService;
import com.ruoyi.petshop.service.WxLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: RuoYi-Vue-Plus
 * @Author: WenZhengcheng
 * @Date: 2022-4-30 下午 02:44
 * @Desc:
 */

@Api(value = "微信小程序登陆控制器", tags = {"微信小程序登陆"})
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wx")
public class LoginController {

    private final WxLoginService loginService;
    private final IWxUserService wxUserService;
    private final IOrderService orderService;

    @ApiOperation("登录")
    @PostMapping("login")
    public R<Object> login(
            @ApiParam("微信小程序appid") @NotBlank(message = "{xcx.appid.not.blank}") @RequestParam String appid,
            @ApiParam("微信用户临时登陆code") @NotBlank(message = "{xcx.code.not.blank}") @RequestParam String code) {
        Map<String, Object> ajax = new HashMap<>(2);
        // 生成令牌
        String token = null;
        try {
            token = loginService.miniAppLogin(appid, code);
            ajax.put("token", token);
            return R.ok(ajax);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return R.fail(e.getError());
        }
    }


    @ApiOperation("退出")
    @GetMapping("/logout")
    public R<Object> logout() {
        StpUtil.logout();
        loginService.miniAppLoginOut(LoginHelper.getLoginUser().getLoginId());
        return R.ok();
    }


    @ApiOperation("用户信息")
    @PostMapping("/user")
    public R<Object> user(WxUser wxUser) {
        wxUser.setOpenid(LoginHelper.getWxLoginUser().getOpenId());
        wxUserService.updateByBo(wxUser);
        return R.ok();
    }

    @ApiOperation("用户订单")
    @PostMapping("/order")
    public R<Object> order(@RequestBody OrderBo bo) {
        bo.setUserId(String.valueOf(LoginHelper.getUserId()));

        return R.ok(orderService.queryList(bo));
    }

}
