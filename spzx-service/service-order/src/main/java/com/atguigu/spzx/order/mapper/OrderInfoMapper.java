package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderInfo;

import java.util.List;

public interface OrderInfoMapper {
    void save(OrderInfo orderInfo);

    OrderInfo getOrderInfo(Long orderId);

    List<OrderInfo> find(Long userId, Integer orderStatus);

    OrderInfo getByOrderNo(String orderNo);

    void updateById(OrderInfo orderInfo);
}
