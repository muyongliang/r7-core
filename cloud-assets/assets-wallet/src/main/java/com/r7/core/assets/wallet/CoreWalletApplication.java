package com.r7.core.assets.wallet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zs
 * @description: 钱包模块启动类
 * @date : 2020-10-26
 */
@MapperScan("com.r7.core.assets.wallet.mapper")
@SpringBootApplication(scanBasePackages = {"com.r7.core"})
@EnableDiscoveryClient
public class CoreWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreWalletApplication.class);
    }

}


