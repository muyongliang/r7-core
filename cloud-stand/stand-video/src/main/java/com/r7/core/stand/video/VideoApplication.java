package com.r7.core.stand.video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zs
 * @description: video启动类
 * @date : 2020-10-10
 */
@MapperScan("com.r7.core.stand.video.mapper")
@SpringBootApplication(scanBasePackages = {"com.r7.core.stand.video"})
@EnableSwagger2
public class VideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class, args);
    }
}
