package com.r7.core.stand.video.service.impl;

import com.r7.core.stand.video.agora.AgoraRecordingEventHandler;
import com.r7.core.stand.video.properties.AgoraProperties;
import com.r7.core.stand.video.service.RecordingService;
import io.agora.recording.RecordingSDK;
import io.agora.recording.common.Common;
import io.agora.recording.common.RecordingConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Timer;

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
    public boolean createChannel(String appId, String channel, String channelKey, Integer... uids) {
//        新建sdk
        RecordingSDK recordingSDK = new RecordingSDK();
//        配置回调处理类
        AgoraRecordingEventHandler agoraRecordingEventHandler = new AgoraRecordingEventHandler(recordingSDK);
//        String appId = agoraProperties.getAppId();
//        String appCertificate = agoraProperties.getAppCertificate();
//        Integer expirationTimeInSeconds = agoraProperties.getExpirationTimeInSeconds();

        //生成token
//        RtcTokenBuilder token = new RtcTokenBuilder();
//        int privilegeExpiredTs = (int) (System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
//        String channelKey = token.buildTokenWithUid(appId, appCertificate, channel, agoraProperties.getServerUid(), RtcTokenBuilder.Role.Role_Subscriber, privilegeExpiredTs);
        RecordingConfig recordingConfig = new RecordingConfig();
        recordingConfig.appliteDir = agoraProperties.getAppliteDir();
//        recordingConfig.isMixingEnabled = agoraProperties.isMixingEnabled();
        recordingConfig.isMixingEnabled = true;
        if (StringUtils.isNotBlank(agoraProperties.getMixedVideoAudio())) {
            recordingConfig.mixedVideoAudio = Common.MIXED_AV_CODEC_TYPE.valueOf(agoraProperties.getMixedVideoAudio());
        }
        recordingConfig.mixResolution = agoraProperties.getMixResolution();
        recordingConfig.recordFileRootDir = agoraProperties.getRecordFileRootDir();
        recordingConfig.lowUdpPort = agoraProperties.getLowUdpPort();
        recordingConfig.highUdpPort = agoraProperties.getHighUdpPort();
        recordingConfig.autoSubscribe = agoraProperties.isAutoSubscribe();
        recordingConfig.subscribeVideoUids = uidToSubscribeString(uids);
        recordingConfig.subscribeAudioUids = uidToSubscribeString(uids);
//        开始调用本地方法进行录制
        log.info("appId: {}", appId);
        log.info("channelKey: {}", channelKey);
        log.info("channel: {}", channel);
        log.info("uid: {}", agoraProperties.getServerUid());
        log.info("recordingConfig: {}", recordingConfig.toString());
        log.info("logLevel: {}", agoraProperties.getLogLevel());
        //        回调处理类初始化
        agoraRecordingEventHandler.init(recordingConfig
                , null, null, null, null, null
                , recordingConfig.isMixingEnabled, null);
        // run jni event loop , or start a new thread to do it
        agoraRecordingEventHandler.setCleanTimer(new Timer());
        boolean success = recordingSDK.createChannel(appId
                , channelKey, channel, agoraProperties.getServerUid(), recordingConfig, agoraProperties.getLogLevel());
        log.info("创建频道成功,channel:{}", channel);
        agoraRecordingEventHandler.getCleanTimer().cancel();
        log.info("jni layer has been exited...");
//        销毁监听器
        recordingSDK.unRegisterOberserver(agoraRecordingEventHandler);
        return success;
    }

    public String uidToSubscribeString(Integer... uid) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < uid.length - 1; i++) {
            sb.append(uid[i]);
            sb.append(',');
        }
        sb.append(uid[uid.length - 1]);
        return sb.toString();
    }
}

