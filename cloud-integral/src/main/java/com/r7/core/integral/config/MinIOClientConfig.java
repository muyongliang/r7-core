package com.r7.core.integral.config;

import com.r7.core.integral.properties.MinIOProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther muyongliang
 * @Date 2020/10/9
 * @Description MinIOClientConfig
 */
@Configuration
public class MinIOClientConfig {
    @Autowired
    private MinIOProperties minIOProperties;

    @Bean
    public MinioClient minioClient() {
        String file = Thread.currentThread().getContextClassLoader().getResource("minIO.keystore").getFile();
        System.setProperty("javax.net.ssl.trustStore", file);
        return MinioClient.builder()
                .endpoint(minIOProperties.getEndpoint())
                .credentials(minIOProperties.getAccessKey(), minIOProperties.getSecretKey())
                .build();
    }
}
