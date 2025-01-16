package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.SmsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: SmsController
 * @date ：2024/06/02 13:36
 */
@Tag(name="短信接口")
@RestController
@RequestMapping("/api/user/sms")
public class SmsController {
    @Resource
    private SmsService smsService;

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @GetMapping(value = "/sendCode/{phone}")
    public Result sendValidateCode(@PathVariable String phone) {
        smsService.sendValidateCode(phone);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
