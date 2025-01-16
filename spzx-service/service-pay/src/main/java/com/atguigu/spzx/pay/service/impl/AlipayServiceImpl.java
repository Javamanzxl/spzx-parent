package com.atguigu.spzx.pay.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.atguigu.spzx.common.exception.MyException;
import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.pay.properties.AliPayProperties;
import com.atguigu.spzx.pay.service.AlipayService;
import com.atguigu.spzx.pay.service.PaymentInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: AlipayServiceImpl
 * @date ：2024/06/10 20:10
 */
@Service
public class AlipayServiceImpl implements AlipayService {
    @Resource
    private PaymentInfoService paymentInfoService;
    @Resource
    private AliPayProperties alipayProperties;
    @Resource
    private AlipayClient alipayClient;

    @Override
    public String submitAlipay(String orderNo) {
        //1.保存支付记录
        PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);
        //2.调用支付宝服务接口
        //构建需要参数，进行调用
        AlipayTradeWapPayRequest alipayTradeWapPayRequest = new AlipayTradeWapPayRequest();
        // 同步回调
        alipayTradeWapPayRequest.setReturnUrl(alipayProperties.getReturnPaymentUrl());
        // 异步回调
        alipayTradeWapPayRequest.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());
        // 准备请求参数 ，声明一个map 集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no", paymentInfo.getOrderNo());
        map.put("product_code", "QUICK_WAP_WAY");
        //map.put("total_amount",paymentInfo.getAmount());
        map.put("total_amount", new BigDecimal("0.01"));
        map.put("subject", paymentInfo.getContent());
        alipayTradeWapPayRequest.setBizContent(JSON.toJSONString(map));

        //调用支付宝服务接口
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayTradeWapPayRequest);
            if (response.isSuccess()) {
                return response.getBody();
            } else {
                throw new MyException(ResultCodeEnum.DATA_ERROR);
            }

        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}
