//package com.r7.core.trigger;
//
//import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//
//
///**
// * 定时任务启动类
// *
// * @author wutao
// * @date 2020/9/24
// */
//
//
//@MapperScan("com.r7.core.trigger.mapper")
//@SpringBootApplication(scanBasePackages = {"com.r7.core"},exclude= {DataSourceAutoConfiguration.class})
//@EnableDiscoveryClient
//public class TriggerStartApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(TriggerStartApplication.class);
//        System.out.println("启动成功");
//    }
//}
