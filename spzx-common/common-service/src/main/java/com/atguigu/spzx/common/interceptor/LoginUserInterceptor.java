package com.atguigu.spzx.common.interceptor;

import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

/**
 * @author ：zxl
 * @Description: 获取登录用户信息拦截器
 * @ClassName: LoginUserInterceptor
 * @date ：2024/06/05 20:33
 */

public class LoginUserInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String userJson = redisTemplate.opsForValue().get("user:"+token);
        UserInfo userInfo = JSON.parseObject(userJson,UserInfo.class);
        AuthContextUtil.setUserInfo(userInfo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.removeUserInfo();
    }
}
