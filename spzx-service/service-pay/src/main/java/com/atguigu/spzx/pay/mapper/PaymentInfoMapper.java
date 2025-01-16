package com.atguigu.spzx.pay.mapper;

import com.atguigu.spzx.model.entity.pay.PaymentInfo;

public interface PaymentInfoMapper {
    PaymentInfo getByOrderNo(String orderNo);

    void save(PaymentInfo paymentInfo);

    void updateById(PaymentInfo paymentInfo);
}

