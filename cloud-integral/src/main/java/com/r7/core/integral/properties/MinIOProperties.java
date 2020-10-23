package com.r7.core.integral.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther muyongliang
 * @Date 2020/10/16
 * @Description MinIOProperties
 */
@Component
@ConfigurationProperties(prefix = "minio")
@Data
public class MinIOProperties {
    /**
     * MinIO服务器地址和端口，例如："https://192.168.1.49:9000"
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 秘钥
     */
    private String secretKey;
}
