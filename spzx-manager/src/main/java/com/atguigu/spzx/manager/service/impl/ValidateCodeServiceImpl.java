package com.atguigu.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.sql.Time;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zxl
 * @Description: 验证码
 * @ClassName: ValidateCodeServiceImpl
 * @date ：2024/04/12 9:40
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ValidateCodeVo generateValidateCode() {
        //1.通过hutool工具包生成图片验证码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 2);
        String codeValue = circleCaptcha.getCode(); //生成验证码的值
        String codeImage = circleCaptcha.getImageBase64(); //验证码图片，进行base64编码
        //2.把验证码存储到redis里，设置redis的key:uuid,redis的value:验证码的值，设置过期时间
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:validate" + key, codeValue, 5, TimeUnit.MINUTES);
        //3.返回ValidateCodeVo对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + codeImage);
        return validateCodeVo;
    }
}
