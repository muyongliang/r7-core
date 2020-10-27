package com.r7.core.stand.video.service.impl;

import com.r7.core.stand.video.properties.AgoraProperties;
import com.r7.core.stand.video.service.RecordingService;
import io.agora.recording.RecordingSDK;
import io.agora.recording.common.Common;
import io.agora.recording.common.RecordingConfig;
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
    public boolean createChannel(String appId, String channel, String userAccount, String channelKey, Integer... uids) {
//        新建sdk
        RecordingSDK recordingSDK = new RecordingSDK();
//        配置监听回调
        AgoraRecordingEventHandler agoraRecordingEventHandler = new AgoraRecordingEventHandler();
//        注册监听回调到声网
        recordingSDK.registerOberserver(agoraRecordingEventHandler);
//        String appId = agoraProperties.getAppId();
//        String appCertificate = agoraProperties.getAppCertificate();
//        Integer expirationTimeInSeconds = agoraProperties.getExpirationTimeInSeconds();

        //生成token
//        RtcTokenBuilder token = new RtcTokenBuilder();
//        int privilegeExpiredTs = (int) (System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
//        String channelKey = token.buildTokenWithUid(appId, appCertificate, channel, agoraProperties.getServerUid(), RtcTokenBuilder.Role.Role_Subscriber, privilegeExpiredTs);
        RecordingConfig recordingConfig = new RecordingConfig();
        recordingConfig.appliteDir = agoraProperties.getAppliteDir();
        recordingConfig.isMixingEnabled = agoraProperties.isMixingEnabled();
        recordingConfig.mixedVideoAudio = Common.MIXED_AV_CODEC_TYPE.valueOf(agoraProperties.getMixedVideoAudio());
        recordingConfig.recordFileRootDir = agoraProperties.getRecordFileRootDir();
        recordingConfig.lowUdpPort = agoraProperties.getLowUdpPort();
        recordingConfig.highUdpPort = agoraProperties.getHighUdpPort();
        recordingConfig.autoSubscribe = agoraProperties.isAutoSubscribe();
        recordingConfig.subscribeVideoUids = uidToSubscribeString(uids);
        recordingConfig.subscribeAudioUids = uidToSubscribeString(uids);
//        开始调用本地方法进行录制
        recordingSDK.createChannel(appId, channelKey, channel, agoraProperties.getServerUid(), recordingConfig, agoraProperties.getLogLevel());
//        销毁监听器
        recordingSDK.unRegisterOberserver(agoraRecordingEventHandler);
        return true;
    }

    public String uidToSubscribeString(Integer... uid) {
        StringBuilder sb = new StringBuilder();
        sb.append('"');
        for (int i = 0; i < uid.length - 1; i++) {
            sb.append(uid[i]);
            sb.append('"');
            sb.append(',');
            sb.append('"');
        }
        sb.append(uid[uid.length - 1]);
        sb.append('"');
        return sb.toString();
    }
}

