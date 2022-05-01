package com.ruoyi.petshop.wx.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.petshop.service.IWxUserService;
import com.ruoyi.petshop.service.WxLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: RuoYi-Vue-Plus
 * @Author: WenZhengcheng
 * @Date: 2022-4-30 下午 02:44
 * @Desc:
 */

@Api(value = "微信小程序",tags = {"微信小程序"})
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wx")
public class LoginController {

    private final IWxUserService wxUserService;
    private final WxLoginService loginService;



    @ApiOperation("登陆方法")
    @PostMapping("login")
    public R<Object> login(
            @ApiParam("微信小程序appid") @NotBlank(message = "{xcx.appid.not.blank}") @RequestParam String appid,
            @ApiParam("微信用户临时登陆code") @NotBlank(message = "{xcx.code.not.blank}") @RequestParam String code){
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = null;
        try {
            token = loginService.miniAppLogin(appid,code);
            ajax.put("token", token);
            return R.ok(ajax);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return R.fail(e.getError());
        }
    }


}
