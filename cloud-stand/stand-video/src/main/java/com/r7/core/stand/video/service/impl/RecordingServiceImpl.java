package com.r7.core.stand.video.service.impl;

import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.ClientUploadUtil;
import com.r7.core.stand.video.agora.AgoraRecordingEventHandler;
import com.r7.core.stand.video.constant.RecordErrorEnum;
import com.r7.core.stand.video.properties.AgoraProperties;
import com.r7.core.stand.video.service.RecordingService;
import io.agora.recording.RecordingSDK;
import io.agora.recording.common.Common;
import io.agora.recording.common.RecordingConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
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
    @Value("${resource.address}")
    private String resourceAddress;

    @Override
    public boolean createChannel(String appId, String channel, String channelKey, Integer... uids) throws Exception {
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
//        String channelKey = token.buildTokenWithUid(appId, appCertificate, channel
//        , agoraProperties.getServerUid(), RtcTokenBuilder.Role.Role_Subscriber, privilegeExpiredTs);
        RecordingConfig recordingConfig = new RecordingConfig();
//        系统配置参数
        recordingConfig.appliteDir = agoraProperties.getAppliteDir();
//        recordingConfig.isMixingEnabled = agoraProperties.isMixingEnabled();
        recordingConfig.isMixingEnabled = true;
        recordingConfig.idleLimitSec = agoraProperties.getIdleLimitSec();
        if (StringUtils.isNotBlank(agoraProperties.getMixedVideoAudio())) {
            recordingConfig.mixedVideoAudio = Common.MIXED_AV_CODEC_TYPE.valueOf(agoraProperties.getMixedVideoAudio());
        }
        recordingConfig.mixResolution = agoraProperties.getMixResolution();
        recordingConfig.recordFileRootDir = agoraProperties.getRecordFileRootDir();
        recordingConfig.lowUdpPort = agoraProperties.getLowUdpPort();
        recordingConfig.highUdpPort = agoraProperties.getHighUdpPort();
        recordingConfig.autoSubscribe = agoraProperties.isAutoSubscribe();


//        自定义参数
        String uidStr = Arrays.toString(uids).replace("[", "").replace("]", "");
        recordingConfig.subscribeVideoUids = uidStr;
        recordingConfig.subscribeAudioUids = uidStr;
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
        agoraRecordingEventHandler.setCleanTimer(new Timer());
        long start = System.currentTimeMillis();
        boolean success = recordingSDK.createChannel(appId
                , channelKey, channel, agoraProperties.getServerUid(), recordingConfig, agoraProperties.getLogLevel());
        long end = System.currentTimeMillis();
        log.info("创建频道是否成功:{},channel:{},用时:{}ms", success, channel, end - start);
        agoraRecordingEventHandler.getCleanTimer().cancel();
        log.info("jni layer has been exited...");
//        销毁监听器
        recordingSDK.unRegisterOberserver(agoraRecordingEventHandler);

        //        异步上传视频
        File mp4 = getMP4(agoraRecordingEventHandler.getStorageDir());
        if (mp4 == null) {
            throw new BusinessException(RecordErrorEnum.VIDEO_NOT_EXIST);
        }
        String url = resourceAddress + "?encrypted=false";
        Response upload = ClientUploadUtil.upload(url, mp4.getAbsolutePath(), mp4.getName());
        if (upload == null || 200 != upload.code()) {
            throw new BusinessException(RecordErrorEnum.FAILURE_UPLOAD_FILE);
        }
        return true;
    }

    private File getMP4(String path) {
        File file = new File(path);
        if (!file.isDirectory()) {
            return null;
        }
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file1 = files[i];
            if (file1.isFile() && file1.getName().toLowerCase().endsWith("mp4")) {
                return file1;
            }
        }
        return null;
    }
}

