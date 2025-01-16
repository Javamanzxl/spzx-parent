package com.atguigu.spzx.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.pay.properties.AliPayProperties;
import com.atguigu.spzx.pay.service.AlipayService;
import com.atguigu.spzx.pay.service.PaymentInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: AliPayController
 * @date ：2024/06/10 19:29
 */
@RestController
@RequestMapping("api/order/alipay")
public class AliPayController {

    @Resource
    private AlipayService alipayService;
    @Resource
    private AliPayProperties aliPayProperties;
    @Resource
    private PaymentInfoService paymentInfoService;


    /**
     * 支付宝支付
     *
     * @param orderNo
     * @return
     */
    @GetMapping("submitAlipay/{orderNo}")
    public Result<String> submitAlipay(@PathVariable String orderNo) {
        String form = alipayService.submitAlipay(orderNo);
        return Result.build(form, ResultCodeEnum.SUCCESS);
    }

    @RequestMapping("callback/notify")
    public String alipayNotify(@RequestParam Map<String, String> paramMap, HttpServletRequest request) {
        //签名校验
        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(paramMap, aliPayProperties.getAlipayPublicKey(), AliPayProperties.charset, AliPayProperties.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        // 交易状态
        String trade_status = paramMap.get("trade_status");
        if (signVerified) {
            if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
                // 正常的支付成功，更新交易记录状态
                paymentInfoService.updatePaymentStatus(paramMap, 2);
                return "success";
            }
        } else {
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            return "failure";
        }
        return "failure";

    }
}
