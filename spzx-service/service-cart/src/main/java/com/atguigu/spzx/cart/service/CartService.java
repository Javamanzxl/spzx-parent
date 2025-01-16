package com.atguigu.spzx.cart.service;

import com.atguigu.spzx.model.entity.h5.CartInfo;

import java.util.List;

public interface CartService {
    void addToCart(Long skuId, Integer skuNum);

    List<CartInfo> cartList();

    void deleteCart(Long skuId);

    void checkCart(Long skuId, int isChecked);

    void allCheckCart(int isChecked);

    void clearCart();

    List<CartInfo> getAllChecked();

    void deleteChecked();
}
