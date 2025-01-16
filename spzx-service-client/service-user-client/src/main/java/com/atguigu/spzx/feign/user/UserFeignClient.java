package com.atguigu.spzx.feign.user;

import com.atguigu.spzx.model.entity.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: UserFeignClient
 * @date ：2024/06/10 13:26
 */
@FeignClient("service-user")
public interface UserFeignClient {

    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    UserAddress getUserAddress(@PathVariable Long id) ;
}
