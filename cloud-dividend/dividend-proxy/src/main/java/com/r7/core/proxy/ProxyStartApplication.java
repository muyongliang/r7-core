package com.r7.core.proxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 代理模块启动类
 *
 * @author wutao
 * @date 2020/10/09
 */
@MapperScan("com.r7.core.proxy.mapper")
@SpringBootApplication(scanBasePackages = {"com.r7.core"})
@EnableDiscoveryClient
public class ProxyStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProxyStartApplication.class);
        System.out.println("启动成功");
    }
}
