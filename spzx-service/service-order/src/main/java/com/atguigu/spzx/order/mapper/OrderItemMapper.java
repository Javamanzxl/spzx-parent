package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderItem;

import java.util.List;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: OrderItemMapper
 * @date ：2024/06/10 13:30
 */
public interface OrderItemMapper {
    void save(OrderItem orderItem);

    List<OrderItem> find(Long id);
}
