package com.atguigu.spzx.manager.config;

import com.atguigu.spzx.manager.interceptor.LoginAuthInterceptor;
import com.atguigu.spzx.manager.properties.UserAuthProperties;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：zxl
 * @Description: WebMvc配置类
 * @ClassName: WebMvcConfig
 * @date ：2024/04/12 9:10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private LoginAuthInterceptor loginAuthInterceptor;
    @Resource
    private UserAuthProperties userAuthProperties;

    /**
     * 跨域配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    /**
     * 拦截器注册
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
                .addPathPatterns("/**")
//                .excludePathPatterns("/admin/system/index/login" , "/admin/system/index/generateValidateCode");
                .excludePathPatterns(userAuthProperties.getNoAuthUrls());

    }
}
