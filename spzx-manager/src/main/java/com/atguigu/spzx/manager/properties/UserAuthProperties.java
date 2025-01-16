package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: UserAuthProperties
 * @date ：2024/04/16 15:36
 */
@Data
@ConfigurationProperties(prefix = "spzx.auth")
public class UserAuthProperties {
    private List<String> noAuthUrls;
}
