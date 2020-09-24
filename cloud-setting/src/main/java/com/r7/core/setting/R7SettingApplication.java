package com.r7.core.setting;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author muyongliang
 */
@MapperScan(value = "com.r7.core.setting.mapper")
@SpringBootApplication(scanBasePackages = {"com.r7.core"})
@EnableDiscoveryClient
//@EnableFeignClients
public class R7SettingApplication {

    public static void main(String[] args) {
        SpringApplication.run(R7SettingApplication.class, args);
    }

}
