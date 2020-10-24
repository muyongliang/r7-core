package com.r7.core.stand.video.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author zs
 * @description:
 * @date : 2020-10-22
 */
@Configuration
public class AgoraConfig {

    @PostConstruct
    public void init() {
        System.load("/root/data/stand_video_backup/librecording.so");
    }
}
