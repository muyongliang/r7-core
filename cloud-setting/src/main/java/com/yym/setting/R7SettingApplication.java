package com.yym.setting;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author muyongliang
 */
@MapperScan(value = "com.yym.setting.mapper")
@SpringBootApplication(exclude = {})
@EnableDiscoveryClient
@EnableFeignClients
public class R7SettingApplication {

    public static void main(String[] args) {
        SpringApplication.run(R7SettingApplication.class, args);
    }

}
