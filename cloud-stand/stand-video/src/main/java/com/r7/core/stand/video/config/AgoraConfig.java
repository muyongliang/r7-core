package com.r7.core.stand.video.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author muyongliang
 * @date 2020/10/26 18:01
 * @description 加载agora动态链接库
 */
@Configuration
@Slf4j
public class AgoraConfig {
    @Value("${agora.agoraLib}")
    private String agoraLib;

    @PostConstruct
    public void init() {
        String libPath = System.getProperty("java.library.path");
        log.info("java.library.path:" + libPath);
        System.load(agoraLib);
    }
}
