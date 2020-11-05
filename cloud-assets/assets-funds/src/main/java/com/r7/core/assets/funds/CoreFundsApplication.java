package com.r7.core.assets.funds;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zs
 * @description: 资金模块启动类
 * @date : 2020-10-26
 */
@MapperScan("com.r7.core.assets.funds.mapper")
@SpringBootApplication(scanBasePackages = {"com.r7.core"})
public class CoreFundsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreFundsApplication.class);
    }

}


