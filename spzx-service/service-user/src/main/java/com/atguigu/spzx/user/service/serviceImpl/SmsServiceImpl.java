package com.atguigu.spzx.user.service.serviceImpl;

import com.atguigu.spzx.common.exception.MyException;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.SmsService;
import com.atguigu.spzx.utils.HttpUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: SmsServiceImpl
 * @date ：2024/06/02 13:37
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendValidateCode(String phone) {

        //1.生成一个验证码
        String code = RandomStringUtils.randomNumeric(4);
        //2.把生成的验证码放到redis中
        redisTemplate.opsForValue().set("phone:code"+phone, code, 5, TimeUnit.MINUTES);
        //3.向手机号发送短信验证码
        sendMessage(phone, code);
    }

    /**
     * 方法短信验证码的方法，直接从阿里云复制
     *
     * @param phone
     * @param code
     */
    private void sendMessage(String phone, String code) {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "5cc39ce2f73d4bb195140a1fffc1333c";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:"+code);
        bodys.put("template_id", "CST_ptdie100");  //注意，CST_ptdie100该模板ID仅为调试使用，调试结果为"status": "OK" ，即表示接口调用成功，然后联系客服报备自己的专属签名模板ID，以保证短信稳定下发
        bodys.put("phone_number", phone);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

}

