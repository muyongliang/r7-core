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
    public boolean createChannel(String channel, Integer[] uids) {
        //should config -Djava.library.path to load library
        RecordingSDK recordingSDK = new RecordingSDK();
        RecordingSample recordingSample = new RecordingSample(recordingSDK);

        String userAccount = "";
        RecordingConfig recordingConfig = new RecordingConfig();

        recordingConfig.appliteDir = agoraProperties.getAppliteDir();
        recordingConfig.highUdpPort = agoraProperties.getHighUdpPort();
        recordingConfig.lowUdpPort = agoraProperties.getLowUdpPort();
        recordingConfig.recordFileRootDir = agoraProperties.getRecordFileRootDir();
        recordingConfig.isMixingEnabled = agoraProperties.getIsMixingEnabled();
        recordingConfig.mixedVideoAudio = agoraProperties.getMixedVideoAudio();
//      录制指定 UID 的音视频流。UID 组成的数组，为用逗号隔开的字符串，例如 "1","2","3"。
//      当 autoSubscribe 为 false 时，你可设置此参数来指定 UID 进行录制。该参数设为 NULL 则表示录制所有发流用户的视频。
        recordingConfig.autoSubscribe = false;
        recordingConfig.subscribeAudioUids = uidToSubscribeString(uids);
        recordingConfig.subscribeVideoUids = uidToSubscribeString(uids);
        int logLevel = agoraProperties.getLogLevel();
        String appId = agoraProperties.getAppId();
        String appCertificate = agoraProperties.getAppCertificate();
        Integer expirationTimeInSeconds = agoraProperties.getExpirationTimeInSeconds();

        //生成token
        RtcTokenBuilder token = new RtcTokenBuilder();
        int privilegeExpiredTs = (int) (System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
        String channelKey = token.buildTokenWithUid(appId, appCertificate,
                channel, agoraProperties.getServerUid(), RtcTokenBuilder.Role.Role_Subscriber, privilegeExpiredTs);
        recordingSample.createChannel(appId, channelKey, channel, agoraProperties.getServerUid(), userAccount, recordingConfig, logLevel);
        recordingSample.unRegister();
        return true;
    }

    public String uidToSubscribeString(Integer[] uid) {
        StringBuilder sb = new StringBuilder();
        sb.append('"');
        for (int i = 0; i < uid.length - 1; i++) {
            sb.append(uid[i]);
            sb.append('"');
            sb.append(',');
        }
        sb.append(uid[uid.length - 1]);
        sb.append('"');
        return sb.toString();
    }

}

