package com.atguigu.spzx.common.config;

import com.atguigu.spzx.common.interceptor.LoginUserInterceptor;

import jakarta.annotation.Resource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: WebMvcConfig
 * @date ：2024/06/05 20:37
 */
public class UserWebMvcConfig implements WebMvcConfigurer {
    @Resource
    private LoginUserInterceptor loginUserInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginUserInterceptor)
                .addPathPatterns("/api/**");
    }
}
