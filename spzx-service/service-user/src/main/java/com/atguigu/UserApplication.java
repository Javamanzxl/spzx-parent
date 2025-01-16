package com.atguigu;



import com.atguigu.spzx.common.annotation.EnableUserWebMvcConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.atguigu.spzx.user.mapper")
@ComponentScan(basePackages = {"com.atguigu.spzx"})
@Slf4j
@EnableDiscoveryClient
@EnableUserWebMvcConfiguration
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
        log.info("用户模块启动成功!!!!!");
    }
}