package com.ruoyi.common.utils;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;

import java.util.Date;

/**
 * 订单编号生成工具
 */
public class OrderUtil {

    /**
     * 使用日期+雪花算法生成唯一订单ID
     * @return
     */
    public static String createOrderNumber(){
        return  new DateTime(new Date()).toString("yyyyMMdd") + IdUtil.getSnowflake(1, 15).nextId();
    }
}
