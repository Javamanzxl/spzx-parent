package com.atguigu;


import com.atguigu.spzx.common.annotation.EnableUserTokenFeignInterceptor;
import com.atguigu.spzx.common.annotation.EnableUserWebMvcConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@MapperScan("com.atguigu.spzx.order.mapper")
@EnableFeignClients(basePackages = {"com.atguigu.spzx.feign"})
@EnableUserTokenFeignInterceptor
@EnableUserWebMvcConfiguration
@Slf4j
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
        log.info("订单模块启动成功！！！！！！！！");
    }
}