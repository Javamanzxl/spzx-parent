package com.atguigu.spzx.order.controller;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: OrderInfoController
 * @date ：2024/06/09 14:15
 */
@RestController
@RequestMapping("api/order/orderInfo")
public class OrderInfoController {
    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 在订单页面获取商品信息和总金额
     *
     * @return
     */
    @GetMapping("auth/trade")
    public Result<TradeVo> trade() {
        TradeVo tradeVo = orderInfoService.getTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 生成订单
     *
     * @param orderInfoDto
     * @return
     */
    @PostMapping("auth/submitOrder")
    public Result<Long> submitOrder(@RequestBody OrderInfoDto orderInfoDto) {
        Long orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }

    /**
     * 在支付页面获取订单详细信息
     *
     * @param orderId
     * @return
     */
    @GetMapping("auth/{orderId}")
    public Result<OrderInfo> getOrderInfo(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 立即购买
     *
     * @param skuId
     * @return
     */
    @GetMapping("auth/buy/{skuId}")
    public Result<TradeVo> buy(@PathVariable Long skuId) {
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 分页查询我的所有订单
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("auth/{page}/{limit}")
    public Result<PageInfo<OrderInfo>> findAllOrder(@PathVariable Integer page
            , @PathVariable Integer limit
            , @RequestParam(required = false, defaultValue = "") Integer orderStatus) {
        PageInfo<OrderInfo> pageInfo = orderInfoService.findAllOrder(page, limit, orderStatus);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 获取订单根据订单号
     *
     * @param orderNo
     * @return
     */
    @GetMapping("auth/getOrderInfoByOrderNo/{orderNo}")
    public OrderInfo getOrderInfoByOrderNo(@PathVariable String orderNo) {
        return orderInfoService.getByOrderNo(orderNo);
    }

    @GetMapping("auth/updateOrderStatusPayed/{orderNo}/{orderStatus}")
    public Result updateOrderStatus(@PathVariable(value = "orderNo") String orderNo
            , @PathVariable(value = "orderStatus") Integer orderStatus) {
        orderInfoService.updateOrderStatus(orderNo, orderStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
