package com.r7.core.stand.video.service.impl;

import com.r7.core.stand.video.agora.media.RtcTokenBuilder;
import com.r7.core.stand.video.common.RecordingConfig;
import com.r7.core.stand.video.common.RecordingSDK;
import com.r7.core.stand.video.properties.AgoraProperties;
import com.r7.core.stand.video.sample.RecordingSample;
import com.r7.core.stand.video.service.RecordingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author zs
 * @description: 录制服务实现层
 * @date : 2020-10-20
 */
@Slf4j
@Service
@EnableConfigurationProperties(value = AgoraProperties.class)
public class RecordingServiceImpl implements RecordingService {

    @Resource
    private AgoraProperties agoraProperties;

    @Override
    public boolean createChannel(String channel, Integer uid) {
        //should config -Djava.library.path to load library
        RecordingSDK RecordingSdk = new RecordingSDK();
        RecordingSample recordingSample = new RecordingSample(RecordingSdk);

        String userAccount = "";
        RecordingConfig recordingConfig = new RecordingConfig();

        recordingConfig.appliteDir = agoraProperties.getAppliteDir();
        recordingConfig.highUdpPort= agoraProperties.getHighUdpPort();
        recordingConfig.lowUdpPort = agoraProperties.getLowUdpPort();
        Integer logLevel = agoraProperties.getLogLevel();
        String appId = agoraProperties.getAppId();
        String appCertificate = agoraProperties.getAppCertificate();
        Integer expirationTimeInSeconds = agoraProperties.getExpirationTimeInSeconds();

        //生成token
        RtcTokenBuilder token = new RtcTokenBuilder();
        int privilegeExpiredTs = (int)(System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
        String channelKey = token.buildTokenWithUid(appId, appCertificate,
                channel, uid, RtcTokenBuilder.Role.Role_Subscriber, privilegeExpiredTs);
        recordingSample.createChannel(appId, channelKey, channel, uid, userAccount, recordingConfig, logLevel);
        recordingSample.unRegister();
        return true;
    }

}

