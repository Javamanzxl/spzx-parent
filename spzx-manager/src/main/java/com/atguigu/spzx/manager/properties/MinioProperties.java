package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ：zxl
 * @Description: Minio配置文件
 * @ClassName: MinioProperties
 * @date ：2024/04/18 15:45
 */
@Data
@ConfigurationProperties(prefix = "spzx.minio")
public class MinioProperties {
    private String endpointUrl;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
