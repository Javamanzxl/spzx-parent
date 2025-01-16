package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.order.OrderStatistics;

public interface OrderInfoMapper {
    OrderStatistics selectStatisticsByDate(String afterDay);
}
