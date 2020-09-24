package com.r7.core.stand.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 支架项目启动类
 *
 * @author zhongpingli
 */
@MapperScan("com.r7.core.stand.*.mapper")
@SpringBootApplication(scanBasePackages = {"com.r7.core"})
@EnableDiscoveryClient
public class StandApplication {

    public static void main(String[] args) {
        SpringApplication.run(StandApplication.class, args);
    }

}
