package com.r7.core.uims;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * uims 启动类
 *
 * @author zhongpingli
 */
@SpringBootApplication(scanBasePackages = {"com.r7.core"})
@EnableDiscoveryClient
public class UimsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UimsApplication.class, args);
    }

}
