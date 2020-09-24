package com.r7.core.uims;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * uim 启动类
 *
 * @author zhongpingli
 */
@MapperScan("com.r7.core.uims.mapper")
@SpringBootApplication(scanBasePackages = {"com.r7.core"})
@EnableDiscoveryClient
public class UimApplication {

    public static void main(String[] args) {
        SpringApplication.run(UimApplication.class, args);
    }

}
