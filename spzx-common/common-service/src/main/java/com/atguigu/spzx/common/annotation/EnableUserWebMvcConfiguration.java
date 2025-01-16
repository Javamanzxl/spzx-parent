package com.atguigu.spzx.common.annotation;

import com.atguigu.spzx.common.config.UserWebMvcConfig;
import com.atguigu.spzx.common.interceptor.LoginUserInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = {LoginUserInterceptor.class , UserWebMvcConfig.class})
public @interface EnableUserWebMvcConfiguration {

}