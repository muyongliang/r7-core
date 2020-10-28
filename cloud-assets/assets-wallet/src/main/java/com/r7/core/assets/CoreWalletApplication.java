package com.r7.core.assets;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zs
 * @description: 钱包模块启动类
 * @date : 2020-10-26
 */
@MapperScan("com.r7.core.assets.mapper")
@SpringBootApplication(scanBasePackages = {"com.r7.core"})
public class CoreWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreWalletApplication.class);
    }

}


