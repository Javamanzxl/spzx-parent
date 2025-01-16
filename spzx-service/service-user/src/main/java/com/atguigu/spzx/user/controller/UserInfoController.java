package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import com.atguigu.spzx.user.service.UserInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：zxl
 * @Description: 用户
 * @ClassName: UserInfoController
 * @date ：2024/06/02 14:29
 */
@RestController
@RequestMapping("api/user/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 用户注册功能
     * @param userRegisterDto
     * @return
     */
    @PostMapping("register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto){
        userInfoService.register(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 用户登录
     * @param userLoginDto
     * @return
     */
    @PostMapping("login")
    public Result<String> login(@RequestBody UserLoginDto userLoginDto){
        String token = userInfoService.login(userLoginDto);
        return Result.build(token,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/getCurrentUserInfo")
    public Result<UserInfoVo> getCurrentUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token);
        return Result.build(userInfoVo,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("logout")
    public Result logout(HttpServletRequest request){
        userInfoService.logout(request);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

}
