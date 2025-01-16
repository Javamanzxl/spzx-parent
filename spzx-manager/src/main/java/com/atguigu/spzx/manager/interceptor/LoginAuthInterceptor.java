package com.atguigu.spzx.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.common.exception.MyException;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jshell.execution.Util;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zxl
 * @Description: 后台登录拦截器
 * @ClassName: LoginAuthInterceptor
 * @date ：2024/04/16 15:12
 */
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取请求方式
            //如果请求方式是options 跨域预检请求，直接放行
        String method = request.getMethod();
        if("OPTIONS".equals(method)){
            return true;
        }
        //2.从请求头中获取token
        String token = request.getHeader("token");
        //3.如果token为空，返回错误提示
        if(StrUtil.isEmpty(token)){
            responseNoLoginInfo(response);
            return false;
        }
        //4.如果token不为空，去查redis
        String sysUserInfoJson = redisTemplate.opsForValue().get("user:login" + token);
        //5.如果redis没有该信息，返回错误提示
        if(StrUtil.isEmpty(sysUserInfoJson)){
            responseNoLoginInfo(response);
            return false;
        }
        //6.如果redis查询到用户信息，把用户信息放到ThreadLocal里
        SysUser sysUser = JSON.parseObject(sysUserInfoJson, SysUser.class);
        AuthContextUtil.set(sysUser);
        //7.更新redis用户信息过期时间
        redisTemplate.expire("user:login"+token,30, TimeUnit.MINUTES);
        //8.放行
        return true;
    }

    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.remove();
    }
}
