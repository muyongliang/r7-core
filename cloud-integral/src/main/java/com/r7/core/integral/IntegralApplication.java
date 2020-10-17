package com.r7.core.integral;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 文件资源服务启动类
 *
 * @author zhongpingli
 */
@MapperScan("com.r7.core.integral.mapper")
@SpringBootApplication(scanBasePackages = {"com.r7.core"})
@EnableDiscoveryClient
public class IntegralApplication {

    public static void main(String[] args) {


        SpringApplication.run(IntegralApplication.class, args);
    }

}
