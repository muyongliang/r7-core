package com.r7.core.integral.config;

import cn.hutool.http.ssl.SSLSocketFactoryBuilder;
import com.r7.core.integral.properties.MinIOProperties;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author muyongliang
 * @date 2020/10/9
 * @description MinIOClientConfig
 */
@Configuration
@Slf4j
public class MinIOClientConfig {
    @Autowired
    private MinIOProperties minIOProperties;

    @Bean
    public MinioClient minioClient() throws Exception {
        SSLSocketFactory sslSocketFactory = SSLSocketFactoryBuilder.create().build();
        return MinioClient.builder()
                .endpoint(minIOProperties.getEndpoint())
                .credentials(minIOProperties.getAccessKey(), minIOProperties.getSecretKey())
                .httpClient(new OkHttpClient()
                        .newBuilder()
                        .connectTimeout(5, TimeUnit.MINUTES)
                        .writeTimeout(5, TimeUnit.MINUTES)
                        .readTimeout(5, TimeUnit.MINUTES)
                        .protocols(Arrays.asList(Protocol.HTTP_1_1))
                        .sslSocketFactory(sslSocketFactory, getX509TrustManager())
                        .build())
                .build();
    }


    private X509TrustManager getX509TrustManager() throws Exception {
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("minIO.keystore"), "root123456".toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(ks);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            return (X509TrustManager) trustManagers[0];
        } catch (GeneralSecurityException e) {
            // The system has no TLS. Just give up.
            throw new AssertionError("No System TLS", e);
        }
    }
}

