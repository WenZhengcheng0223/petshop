package com.ruoyi.common.core.domain.model;

import com.ruoyi.common.core.domain.dto.RoleDTO;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.helper.LoginHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @ProjectName: RuoYi-Vue-Plus
 * @Author: WenZhengcheng
 * @Date: 2022-4-30 下午 03:09
 * @Desc:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class WxLoginUser extends LoginUser {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String openId;

}
