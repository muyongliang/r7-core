package com.r7.core.stand.video.service.impl;

import com.r7.core.stand.video.properties.AgoraProperties;
import com.r7.core.stand.video.service.RecordingService;
import io.agora.recording.RecordingSDK;
import io.agora.recording.test.RecordingSample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

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
        //should config -Djava.library.path to load library
        RecordingSDK recordingSDK = new RecordingSDK();
        RecordingSample recordingSample = new RecordingSample(recordingSDK);
//        String appId = agoraProperties.getAppId();
//        String appCertificate = agoraProperties.getAppCertificate();
//        Integer expirationTimeInSeconds = agoraProperties.getExpirationTimeInSeconds();

        //生成token
//        RtcTokenBuilder token = new RtcTokenBuilder();
//        int privilegeExpiredTs = (int) (System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
//        String channelKey = token.buildTokenWithUid(appId, appCertificate, channel, agoraProperties.getServerUid(), RtcTokenBuilder.Role.Role_Subscriber, privilegeExpiredTs);
        HashMap<String, Object> map = new HashMap<>();
        map.put("appId", appId);
        map.put("uid", agoraProperties.getServerUid());
        map.put("userAccount", userAccount);
        map.put("appliteDir", agoraProperties.getAppliteDir());
        map.put("channel", channel);
        map.put("channelKey", channelKey);
        map.put("isMixingEnabled", agoraProperties.getIsMixingEnabled());
        map.put("mixedVideoAudio", agoraProperties.getMixedVideoAudio());
        map.put("recordFileRootDir", agoraProperties.getRecordFileRootDir());
        map.put("lowUdpPort", agoraProperties.getLowUdpPort());
        map.put("highUdpPort", agoraProperties.getHighUdpPort());
        map.put("logLevel", agoraProperties.getLogLevel());
        map.put("autoSubscribe", agoraProperties.getAutoSubscribe());
        map.put("subscribeVideoUids", uidToSubscribeString(uids));
        map.put("subscribeAudioUids", uidToSubscribeString(uids));
        recordingSample.createChannel(map);
        recordingSample.unRegister();
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

