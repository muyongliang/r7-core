package com.r7.core.integral.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther muyongliang
 * @Date 2020/10/9
 * @Description MinIOClientConfig
 */
@Configuration
public class MinIOClientConfig {
    @Bean
    public MinioClient minioClient() {
        String file = Thread.currentThread().getContextClassLoader().getResource("minIO.keystore").getFile();
        System.setProperty("javax.net.ssl.trustStore", file);
        return MinioClient.builder()
                .endpoint("https://192.168.1.49:9000")
                .credentials("admin", "admin123456")
                .build();
    }
}
