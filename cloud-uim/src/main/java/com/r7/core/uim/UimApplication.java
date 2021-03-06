package com.r7.core.uim;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * uim 启动类
 *
 * @author zhongpingli
 */
@EnableFeignClients(basePackages = "com.r7.core.common")
@MapperScan("com.r7.core.uim.mapper")
@SpringBootApplication(scanBasePackages = {"com.r7.core"})
@EnableDiscoveryClient
public class UimApplication {

    public static void main(String[] args) {
        SpringApplication.run(UimApplication.class, args);

    }

}
