package com.atguigu;


import com.atguigu.spzx.common.log.annotation.EnableLogAspect;
import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.properties.UserAuthProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.spzx"})  //不同子模块直接会扫描，只写好要扫描的包名即可
@MapperScan("com.atguigu.spzx.manager.mapper")
@EnableConfigurationProperties(value = {UserAuthProperties.class, MinioProperties.class})
@Slf4j
@EnableTransactionManagement
@EnableScheduling  //启动定时任务功能
@EnableLogAspect
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
        log.info("==========管理员后台模块启动成功==========");
    }
}