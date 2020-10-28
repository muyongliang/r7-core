package com.r7.core.integral;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wt
 * @Description 积分启动类入口
 */
@MapperScan("com.r7.core.integral.mapper")
@SpringBootApplication(scanBasePackages = {"com.r7.core"})
@EnableDiscoveryClient
public class IntegralStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntegralStartApplication.class);
        System.out.println("启动成功");
    }
}
