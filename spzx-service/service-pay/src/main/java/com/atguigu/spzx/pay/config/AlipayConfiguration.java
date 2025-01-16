package com.atguigu.spzx.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.atguigu.spzx.pay.properties.AliPayProperties;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zxl
 * @Description:支付宝配置类
 * @ClassName: AlipayConfiguration
 * @date ：2024/06/10 20:05
 */
@Configuration
public class AlipayConfiguration {
    @Resource
    private AliPayProperties alipayProperties ;

    @Bean
    public AlipayClient alipayClient(){
        AlipayClient alipayClient = new DefaultAlipayClient(alipayProperties.getAlipayUrl() ,
                alipayProperties.getAppId() ,
                alipayProperties.getAppPrivateKey() ,
                AliPayProperties.format ,
                AliPayProperties.charset ,
                alipayProperties.getAlipayPublicKey() ,
                AliPayProperties.sign_type );
        return alipayClient;
    }
}
