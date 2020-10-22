package com.r7.core.profit;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * @author wutao
 * @Description 分润模块启动类
 *
 */
@MapperScan("com.r7.core.profit.mapper")
@SpringBootApplication(scanBasePackages = {"com.r7.core"})
@EnableDiscoveryClient
public class ProfitStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfitStartApplication.class);
        System.out.println("启动成功");
    }

}


