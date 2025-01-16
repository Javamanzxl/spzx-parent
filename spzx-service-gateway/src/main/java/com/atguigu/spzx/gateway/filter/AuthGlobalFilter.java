package com.atguigu.spzx.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author ：zxl
 * @Description: 自定义登录校验过滤器
 * @ClassName: AuthGlobalFilter
 * @date ：2024/06/02 15:45
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求路径
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        //判断路径是否符合  /api/**/auth/**，符合进行登录校验
        if(antPathMatcher.match("/api/**/auth/**",path)){
            //登录校验
            UserInfo userInfo = this.getUserInfo(request);
            if(userInfo==null){
                ServerHttpResponse response = exchange.getResponse();
                return out(response,ResultCodeEnum.LOGIN_AUTH);
            }
        }
        //放行
        return chain.filter(exchange);
    }

    private UserInfo getUserInfo(ServerHttpRequest request) {
        String token = "";
        List<String> tokens = request.getHeaders().get("token");
        if(tokens != null){
            token = tokens.get(0);
        }
        //判断token是否为空
        if(StringUtils.hasText(token)){
            String userJson = redisTemplate.opsForValue().get("user:"+token);
            if(!StringUtils.hasText(userJson)){
                return null;
            }
            UserInfo userInfo = JSON.parseObject(userJson, UserInfo.class);
            return userInfo;
        }
        return null;
    }

    //处理返回信息
    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
