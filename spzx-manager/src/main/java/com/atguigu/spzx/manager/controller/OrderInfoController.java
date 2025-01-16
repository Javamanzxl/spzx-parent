package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.OrderInfoService;
import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：zxl
 * @Description: 订单管理
 * @ClassName: OrderInfoController
 * @date ：2024/04/27 16:35
 */
@RestController
@RequestMapping(value="/admin/order/orderInfo")
public class OrderInfoController {
    @Resource
    private OrderInfoService orderInfoService;
    @PostMapping("/getOrderStatisticsData")
    public Result<OrderStatisticsVo> getOrderStatisticsData(@RequestBody OrderStatisticsDto orderStatisticsDto){
        OrderStatisticsVo orderStatisticsVo = orderInfoService.getOrderStatisticsData(orderStatisticsDto);
        return Result.build(orderStatisticsVo, ResultCodeEnum.SUCCESS);
    }
}
