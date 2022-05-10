package com.ruoyi.petshop.wx.api;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayOrderCloseResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.common.constant.OrderConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.petshop.domain.bo.OrderBo;
import com.ruoyi.petshop.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: RuoYi-Vue-Plus
 * @Author: WenZhengcheng
 * @Date: 2022-5-1 下午 08:33
 * @Desc:
 */
@Api(value = "小程序支付控制器", tags = {"微信小程序支付"})
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wx/pay")
public class WxPayController {
    private final WxPayService wxService;
    private final IOrderService orderService;
    /**
     * 支付方式 微信支付
     */
    private static final String WX_PAY ="2";

    /**
     * 分转元 倍数差
     */
    private static final BigDecimal DIVIDE = new BigDecimal("100");

    @ApiOperation("统一下单，并组装所需支付参数")
    @PostMapping("/createOrder")
    public R<Object> pay(@RequestBody WxPayUnifiedOrderV3Request request) {
        String orderNo = new DateTime(new Date()).toString("yyyyMMdd") + IdUtil.getSnowflake(1, 15).nextId();
        WxPayUnifiedOrderV3Request.Payer payer = new WxPayUnifiedOrderV3Request.Payer();
        BigDecimal total = new BigDecimal(request.getAmount().getTotal());
        request.setPayer(payer.setOpenid(LoginHelper.getWxLoginUser().getOpenId()));
        request.setOutTradeNo(orderNo);
        List<WxPayUnifiedOrderV3Request.GoodsDetail> list = request.getDetail().getGoodsDetails();
        list.forEach(item -> {
            BigDecimal goodsPrice = new BigDecimal(item.getUnitPrice());
            OrderBo orderBo = new OrderBo();
            orderBo.setOrderNumber(orderNo);
            orderBo.setOrderPrice(total.divide(DIVIDE, RoundingMode.CEILING));
            orderBo.setUserId(String.valueOf(LoginHelper.getLoginUser().getUserId()));
            orderBo.setGoodsId(Long.valueOf(item.getMerchantGoodsId()));
            orderBo.setGoodsName(request.getDescription());
            orderBo.setQuantity(item.getQuantity().longValue());
            orderBo.setGoodsPrice(goodsPrice.divide(DIVIDE, RoundingMode.CEILING));
            orderBo.setPayStatus(OrderConstants.UN_PAID);
            orderBo.setOrderPay(WX_PAY);
            orderService.insertByBo(orderBo);
        });

        try {
            Object orderV3 = this.wxService.createOrderV3(TradeTypeEnum.JSAPI, request);
            return R.ok(orderV3);
        } catch (WxPayException e) {
            return R.fail(e.getReturnMsg());
        }
    }

    /**
     * <pre>
     * 关闭订单
     * 应用场景
     * 以下情况需要调用关单接口：
     * 1. 商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
     * 2. 系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
     * 注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。
     * 接口地址：https://api.mch.weixin.qq.com/pay/closeorder
     * 是否需要证书：   不需要。
     * </pre>
     *
     * @param outTradeNo 商户系统内部的订单号
     */
    @ApiOperation(value = "关闭订单")
    @GetMapping("/closeOrder/{outTradeNo}")
    public WxPayOrderCloseResult closeOrder(@PathVariable String outTradeNo) throws WxPayException {
        return this.wxService.closeOrder(outTradeNo);
    }

    /**
     * <pre>
     * 微信支付-申请退款
     * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
     * 接口链接：https://api.mch.weixin.qq.com/secapi/pay/refund
     * </pre>
     *
     * @param request 请求对象
     * @return 退款操作结果
     */
    @ApiOperation(value = "退款")
    @PostMapping("/refund")
    public R<WxPayRefundV3Result> refund(@RequestBody WxPayRefundV3Request request) throws WxPayException {
        request.setNotifyUrl(this.wxService.getConfig().getNotifyUrl());
        return R.ok(this.wxService.refundV3(request));
    }


    @ApiOperation(value = "支付回调通知处理")
    @PostMapping("/notify/order")
    public String parseOrderNotifyResult(@RequestBody String data) throws WxPayException {
        WxPayOrderNotifyV3Result v3Result = this.wxService.parseOrderNotifyV3Result(data, null);
        OrderBo orderBo = new OrderBo();
        orderBo.setTradeNo(v3Result.getResult().getTransactionId());
        orderBo.setOrderNumber(v3Result.getResult().getOutTradeNo());

        if (OrderConstants.REFUND_SUCCESS.equals(v3Result.getRawData().getEventType())) {

            orderBo.setPayStatus(OrderConstants.REFUNDED);
            orderService.updateByOrderNum(orderBo);
            return WxPayNotifyResponse.success("成功");

        } else if (OrderConstants.TRANSACTION_SUCCESS.equals(v3Result.getRawData().getEventType())) {
            orderBo.setPayStatus(OrderConstants.PAID);
            orderService.updateByOrderNum(orderBo);
            return WxPayNotifyResponse.success("成功");
        }
        return WxPayNotifyResponse.success("成功");
    }

    /**
     * <pre>
     * 微信支付-查询退款
     * 应用场景：
     *  提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，
     *  银行卡支付的退款3个工作日后重新查询退款状态。
     * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
     * 接口链接：https://api.mch.weixin.qq.com/pay/refundquery
     * </pre>
     * 以下四个参数四选一
     *
     * @param transactionId 微信订单号
     * @param outTradeNo    商户订单号
     * @param outRefundNo   商户退款单号
     * @param refundId      微信退款单号
     * @return 退款信息
     */
    @ApiOperation(value = "退款查询")
    @GetMapping("/refundQuery")
    public WxPayRefundQueryResult refundQuery(@RequestParam(required = false) String transactionId,
                                              @RequestParam(required = false) String outTradeNo,
                                              @RequestParam(required = false) String outRefundNo,
                                              @RequestParam(required = false) String refundId)
            throws WxPayException {
        return this.wxService.refundQuery(transactionId, outTradeNo, outRefundNo, refundId);
    }


    /**
     * <pre>
     * 查询订单(详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2)
     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况：
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     * ◆ 调用被扫支付API，返回USERPAYING的状态；
     * ◆ 调用关单或撤销接口API之前，需确认支付状态；
     * 接口地址：https://api.mch.weixin.qq.com/pay/orderquery
     * </pre>
     *
     * @param transactionId 微信订单号
     * @param outTradeNo    商户系统内部的订单号，当没提供transactionId时需要传这个。
     */
    @ApiOperation(value = "查询订单")
    @GetMapping("/queryOrder")
    public WxPayOrderQueryResult queryOrder(@RequestParam(required = false) String transactionId,
                                            @RequestParam(required = false) String outTradeNo)
            throws WxPayException {
        return this.wxService.queryOrder(transactionId, outTradeNo);
    }
}
