package com.r7.core.stand.video.config;

import com.r7.core.stand.video.properties.AgoraProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
//        System.load("D:\\project\\r7-core\\cloud-stand\\stand-video\\lib\\librecording.so");
        System.load("/root/data/stand_video/librecording.so");
    }
}
