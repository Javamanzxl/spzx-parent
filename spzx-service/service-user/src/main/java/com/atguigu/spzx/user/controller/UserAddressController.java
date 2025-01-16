package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.UserAddressService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: UserAddressController
 * @date ：2024/06/09 14:03
 */
@RestController
@RequestMapping(value="/api/user/userAddress")
public class UserAddressController {
    @Resource
    private UserAddressService userAddressService;

    /**
     * 查询用户地址
     * @return
     */
    @GetMapping("auth/findUserAddressList")
    public Result<List<UserAddress>> findUserAddressList(){
        List<UserAddress> userAddressList = userAddressService.findUserAddressList();
        return Result.build(userAddressList, ResultCodeEnum.SUCCESS);
    }
}
