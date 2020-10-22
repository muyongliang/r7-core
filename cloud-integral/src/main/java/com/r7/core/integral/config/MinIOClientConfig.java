package com.r7.core.integral.config;

import com.r7.core.integral.properties.MinIOProperties;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther muyongliang
 * @Date 2020/10/9
 * @Description MinIOClientConfig
 */
@Configuration
@Slf4j
public class MinIOClientConfig {
    @Autowired
    private MinIOProperties minIOProperties;

    @Bean
    public MinioClient minioClient() {
        String file = Thread.currentThread().getContextClassLoader().getResource("minIO.keystore").getFile();
        log.info("minIO.keystore的绝对路径为：{}", file);
        System.setProperty("javax.net.ssl.trustStore", file);
        return MinioClient.builder()
                .endpoint(minIOProperties.getEndpoint())
                .credentials(minIOProperties.getAccessKey(), minIOProperties.getSecretKey())
                .build();
    }
}
