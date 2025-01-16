package com.atguigu.spzx.cart.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: CartServiceImpl
 * @date ：2024/06/05 20:28
 */
@Service
public class CartServiceImpl implements CartService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private ProductFeignClient productFeignClient;

    private String getCartKey(Long userId) {
        //定义key user:cart:userId
        return "user:cart:" + userId;
    }

    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        //1.必须是登录状态，获取当前登录用户id(作为redis的hash类型的key的值)
        String cartKey = this.getCartKey(AuthContextUtil.getUserInfo().getId());
        //2.因为购物车放在redis中，从redis里面获取购物车数据，根据用户id+skuId获取(hash类型key+field)
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        //3.如果购物车已经存在添加商品，把商品的数量相加
        CartInfo cartInfo = null;
        if (cartInfoObj != null) {
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            //数量相加
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            //设置属性，表示购物车商品为选中状态
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        } else {
            //4.如果购物车没有该商品，直接添加到购物车(redis中)
            //远程调用实现：通过nacos+openFeign 显示 根据skuId获取商品sku信息
            cartInfo = new CartInfo();
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(AuthContextUtil.getUserInfo().getId());
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        //添加到redis中
        redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));

    }

    @Override
    public List<CartInfo> cartList() {
        String cartKey = this.getCartKey(AuthContextUtil.getUserInfo().getId());
        List<Object> cartInfoJSONList = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtil.isEmpty(cartInfoJSONList)) {
            List<CartInfo> cartInfoList = cartInfoJSONList.stream()
                    .map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class)
                    ).sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .toList();
            return cartInfoList;
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteCart(Long skuId) {
        String cartKey = this.getCartKey(AuthContextUtil.getUserInfo().getId());
        redisTemplate.opsForHash().delete(cartKey, String.valueOf(skuId));
    }

    @Override
    public void checkCart(Long skuId, int isChecked) {
        String cartKey = this.getCartKey(AuthContextUtil.getUserInfo().getId());
        Object cart = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        if (cart != null) {
            CartInfo cartInfo = JSON.parseObject(cart.toString(), CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));
        }
    }

    @Override
    public void allCheckCart(int isChecked) {
        String cartKey = this.getCartKey(AuthContextUtil.getUserInfo().getId());
        List<Object> allCartString = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtil.isEmpty(allCartString)) {
            allCartString.stream()
                    .map(cartString -> {
                        CartInfo cartInfo = JSON.parseObject(cartString.toString(), CartInfo.class);
                        cartInfo.setIsChecked(isChecked);
                        return cartInfo;
                    }).forEach(cartInfo -> {
                        redisTemplate.opsForHash()
                                .put(cartKey, String.valueOf(cartInfo.getSkuId()), JSON.toJSONString(cartInfo));
                    });
        }
    }

    @Override
    public void clearCart() {
        String cartKey = this.getCartKey(AuthContextUtil.getUserInfo().getId());
        redisTemplate.delete(cartKey);
    }

    @Override
    public List<CartInfo> getAllChecked() {
        String cartKey = this.getCartKey(AuthContextUtil.getUserInfo().getId());
        List<Object> allCartJSON = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtil.isEmpty(allCartJSON)) {
            return allCartJSON.stream()
                    .map(cartJSON -> JSON.parseObject(cartJSON.toString(), CartInfo.class)
                    ).filter(cartInfo -> cartInfo.getIsChecked() == 1).toList();
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteChecked() {
        String cartKey = this.getCartKey(AuthContextUtil.getUserInfo().getId());
        List<Object> allCartJSON = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtil.isEmpty(allCartJSON)) {
            allCartJSON.stream().map(cartJson -> JSON.parseObject(cartJson.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .forEach(cartInfo -> redisTemplate.opsForHash().delete(cartKey, String.valueOf(cartInfo.getSkuId())));
        }
    }
}
