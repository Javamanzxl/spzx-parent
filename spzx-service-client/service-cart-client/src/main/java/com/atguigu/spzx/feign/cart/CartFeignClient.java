package com.atguigu.spzx.feign.cart;

import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("service-cart")
public interface CartFeignClient {

    @GetMapping(value = "/api/order/cart/auth/getAllChecked")
    List<CartInfo> getAllChecked();

    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    Result deleteChecked();
}
