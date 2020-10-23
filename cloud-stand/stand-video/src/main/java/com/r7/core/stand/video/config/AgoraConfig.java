package com.r7.core.stand.video.config;

import com.r7.core.stand.video.properties.AgoraProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author zs
 * @description:
 * @date : 2020-10-22
 */
@Configuration
public class AgoraConfig {
    @Autowired
    private AgoraProperties agoraProperties;

    @PostConstruct
    public void init() {
        //    System.loadLibrary(agoraProperties.getAgoraLib());
        System.load(agoraProperties.getAgoraLib());
    }
}
