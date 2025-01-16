package com.atguigu.spzx.common.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author ：zxl
 * @Description: openfeign拦截器传递token
 * @ClassName: UserTokenOpenFeignInterceptor
 * @date ：2024/06/10 13:11
 */
public class UserTokenOpenFeignInterceptor implements RequestInterceptor {
    //requestTemplate是openfeign的方法
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("token");
        requestTemplate.header("token",token);
    }
}
