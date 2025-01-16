package com.atguigu.spzx.cart.controller;

import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：zxl
 * @Description: 购物车接口
 * @ClassName: CartController
 * @date ：2024/06/05 20:27
 */
@RestController
@RequestMapping("api/order/cart")
public class CartController {
    @Resource
    private CartService cartService;

    /**
     * 添加购物车
     * @param skuId
     * @param skuNum
     * @return
     */
    @GetMapping("auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable Long skuId, @PathVariable Integer skuNum){
        cartService.addToCart(skuId,skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @GetMapping("auth/cartList")
    public Result<List<CartInfo>> cartList(){
        List<CartInfo> cartInfos = cartService.cartList();
        return Result.build(cartInfos,ResultCodeEnum.SUCCESS);
    }
    @DeleteMapping("auth/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable Long skuId){
        cartService.deleteCart(skuId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 更新购物车选中状态
     * @param skuId
     * @param isChecked
     * @return
     */
    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId,@PathVariable int isChecked){
        cartService.checkCart(skuId,isChecked);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 全选购物车和全部取消
     * @param isChecked
     * @return
     */
    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@PathVariable int isChecked){
        cartService.allCheckCart(isChecked);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 清空购物车
     * @return
     */
    @GetMapping("/auth/clearCart")
    public Result clearCart(){
        cartService.clearCart();
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 获取所有选中的商品
     * @return
     */
    @GetMapping("/auth/getAllChecked")
    public List<CartInfo> getAllChecked(){
        return cartService.getAllChecked();
    }

    @GetMapping("auth/deleteChecked")
    public Result deletedChecked(){
        cartService.deleteChecked();
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

}
