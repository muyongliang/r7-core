package com.r7.core.stand.video.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author muyongliang
 * @Date 2020/10/26 18:01
 * @Description 加载agora动态链接库
 */
@Configuration
public class AgoraConfig {

    @PostConstruct
    public void init() {
        System.load("/root/data/stand_video_backup/librecording.so");
    }
}
