package com.atguigu;


import com.atguigu.spzx.common.annotation.EnableUserWebMvcConfiguration;
import com.atguigu.spzx.pay.properties.AliPayProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableUserWebMvcConfiguration
@MapperScan("com.atguigu.spzx.pay.mapper")
@EnableFeignClients(basePackages = "com.atguigu.spzx")
@EnableConfigurationProperties(value = {AliPayProperties.class})
@Slf4j
public class PayApplication {
    public static void main(String[] args) {

        SpringApplication.run(PayApplication.class,args);
        log.info("支付模块启动成功!!!!!!");
    }
}