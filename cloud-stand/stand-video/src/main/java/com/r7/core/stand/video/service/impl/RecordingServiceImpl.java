package com.r7.core.stand.video.service.impl;

import com.r7.core.stand.video.agora.media.RtcTokenBuilder;
import com.r7.core.stand.video.agora.sample.RtcTokenBuilderSample;
import com.r7.core.stand.video.common.RecordingConfig;
import com.r7.core.stand.video.common.RecordingSDK;
import com.r7.core.stand.video.sample.RecordingSample;
import com.r7.core.stand.video.service.RecordingService;

/**
 * @author zs
 * @description:
 * @date : 2020-10-20
 */
public class RecordingServiceImpl implements RecordingService {


    @Override
    public boolean createChannel(String channel, Integer uid) {
        //should config -Djava.library.path to load library
        RecordingSDK RecordingSdk = new RecordingSDK();
        RecordingSample recordingSample = new RecordingSample(RecordingSdk);

        String userAccount = "";
        RecordingConfig recordingConfig = new RecordingConfig();
        recordingConfig.appliteDir = "";
        recordingConfig.highUdpPort = 0;
        recordingConfig.lowUdpPort = 0;
        Integer logLevel = 0;

        String appId = recordingConfig.appId;
        String appCertificate = recordingConfig.appCertificate;
        Integer expirationTimeInSeconds = recordingConfig.expirationTimeInSeconds;
        //生成token
        RtcTokenBuilder token = new RtcTokenBuilder();
        int timestamp = (int)(System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
        String result = token.buildTokenWithUid(appId, appCertificate,
                "channelName", uid, RtcTokenBuilder.Role.Role_Publisher, timestamp);

        recordingSample.createChannel("appId", "channelKey", channel, uid, userAccount, recordingConfig, logLevel);
        recordingSample.unRegister();
        return true;
    }

}

