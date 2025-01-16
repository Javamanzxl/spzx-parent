package com.atguigu.spzx.pay.service.impl;

import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.feign.order.OrderFeignClient;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.dto.product.SkuSaleDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import com.atguigu.spzx.pay.mapper.PaymentInfoMapper;
import com.atguigu.spzx.pay.service.PaymentInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: PaymentInfoServiceImpl
 * @date ：2024/06/10 19:30
 */
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
    @Resource
    private PaymentInfoMapper paymentInfoMapper;

    @Resource
    private OrderFeignClient orderFeignClient;
    @Resource
    private ProductFeignClient productFeignClient;

    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        //1.根据订单编号查询支付记录
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(orderNo);
        //2.判断支付记录是否存在
        if (paymentInfo == null) {
            //获取订单信息
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
            //把orderInfo数据封装到paymentInfo
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            StringBuilder content = new StringBuilder();
            for (OrderItem item : orderInfo.getOrderItemList()) {
                content.append(item.getSkuName()).append(" ");
            }
            paymentInfo.setContent(content.toString());
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            paymentInfoMapper.save(paymentInfo);
        }
        return paymentInfo;
    }

    @Override
    public void updatePaymentStatus(Map<String, String> map, int i) {
        // 查询PaymentInfo
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(map.get("out_trade_no"));
        if (paymentInfo.getPaymentStatus() == 1) {
            return;
        }
        //更新支付信息
        paymentInfo.setPaymentStatus(1);
        paymentInfo.setOutTradeNo(map.get("trade_no"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(map));
        paymentInfoMapper.updateById(paymentInfo);
        //更新订单状态
        orderFeignClient.updateOrderStatus(paymentInfo.getOrderNo(), i);
        //更新sku商品销量
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(paymentInfo.getOrderNo());
        List<SkuSaleDto> skuSaleDtoList = orderInfo.getOrderItemList().stream().map(item -> {
            SkuSaleDto skuSaleDto = new SkuSaleDto();
            skuSaleDto.setSkuId(item.getSkuId());
            skuSaleDto.setNum(item.getSkuNum());
            return skuSaleDto;
        }).collect(Collectors.toList());
        productFeignClient.updateSkuSaleNum(skuSaleDtoList) ;
    }
}
