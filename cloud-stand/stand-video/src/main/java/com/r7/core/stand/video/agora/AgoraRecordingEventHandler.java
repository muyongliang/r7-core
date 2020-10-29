package com.r7.core.stand.video.agora;

import com.r7.core.common.constant.CommonErrorEnum;
import com.r7.core.common.exception.BusinessException;
import io.agora.recording.RecordingEventHandler;
import io.agora.recording.RecordingSDK;
import io.agora.recording.common.Common;
import io.agora.recording.common.RecordingConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author muyongliang
 * @date 2020/10/27
 * @description AgoraRecordingEventHandler
 */
@Slf4j
@Data
public class AgoraRecordingEventHandler implements RecordingEventHandler {
    public static final int DEFAULT_LAYOUT = 0;
    public static final int BESTFIT_LAYOUT = 1;
    public static final int VERTICALPRESENTATION_LAYOUT = 2;
    Vector<Long> m_peers = new Vector<Long>();
    HashMap<String, UserInfo> audioChannels = new HashMap();
    HashMap<String, UserInfo> videoChannels = new HashMap();
    private Timer cleanTimer = null;
    // java run status flag
    private boolean isMixingEnabled = false;
    private int width = 0;
    private int height = 0;
    private int fps = 0;
    private int kbps = 0;
    private String storageDir = "./";
    private long aCount = 0;
    private long count = 0;
    private long size = 0;
    private Common.CHANNEL_PROFILE_TYPE profile_type;
    private RecordingConfig config = null;
    private RecordingSDK RecordingSDKInstance = null;
    private boolean m_receivingAudio = false;
    private boolean m_receivingVideo = false;
    private HashSet<Long> subscribedVideoUids = new HashSet<Long>();
    private HashSet<String> subscribedVideoUserAccount = new HashSet<String>();
    private int layoutMode = 0;
    private long maxResolutionUid = -1;
    private String maxResolutionUserAccount = "";
    private int keepLastFrame = 0;
    private String userAccount = "";
    private long keepMediaTime = 0;
    private long lastKeepAudioTime = 0;
    private long lastKeepVideoTime = 0;

    public void init(RecordingConfig config, String userAccount, String keepLastFrame, String layoutMode
            , String maxResolutionUid, String maxResolutionUserAccount, Boolean isMixingEnabled, String channelProfile) {
        this.config = config;
        this.userAccount = userAccount;
        if (StringUtils.isNotBlank(keepLastFrame)) {
            this.keepLastFrame = Integer.parseInt(keepLastFrame);
        }
        if (StringUtils.isNotBlank(layoutMode)) {
            this.layoutMode = Integer.parseInt(layoutMode);
        }
        if (StringUtils.isNotBlank(maxResolutionUid)) {
            this.maxResolutionUid = Integer.parseInt(maxResolutionUid);
        }
        if (StringUtils.isNotBlank(maxResolutionUserAccount)) {
            this.maxResolutionUserAccount = maxResolutionUserAccount;
        }
//        如果不是自动订阅，则解析订阅uids
        if (!config.autoSubscribe) {
            String[] uids = config.subscribeVideoUids.split(",");
            for (int i = 0; i < uids.length; i++) {
                if (StringUtils.isNoneBlank(userAccount)) {
                    this.subscribedVideoUserAccount.add(uids[i]);
                } else {
                    this.subscribedVideoUids.add(Long.parseLong(uids[i]));
                }
            }
        }
        this.isMixingEnabled = isMixingEnabled;
        if (StringUtils.isNotBlank(channelProfile)) {
            this.profile_type = Common.CHANNEL_PROFILE_TYPE.valueOf(channelProfile);
        }
        if (isMixingEnabled && !config.isAudioOnly) {
//            解析视频设置参数,格式为：width，hight，fps，kbps，分别对应合流的宽、高、帧率和码率。
            String[] resolution = config.mixResolution.split(",");
            if (resolution.length != 4) {
                log.error("Illegal resolution:{}", config.mixResolution);
                throw new BusinessException(CommonErrorEnum.ILLEGAL_ARGUMENT.setMessage("mixResolution配置错误"));
            }
            this.width = Integer.parseInt(resolution[0]);
            this.height = Integer.parseInt(resolution[1]);
            this.fps = Integer.parseInt(resolution[2]);
            this.kbps = Integer.parseInt(resolution[3]);
        }
        String tmpEnv = System.getenv("KEEPMEDIATIME");
        if (StringUtils.isNotBlank(tmpEnv)) {
            keepMediaTime = Integer.parseInt(tmpEnv);
            log.info("Get system env:KEEPMEDIATIME string:{}, int value:{}", tmpEnv, keepMediaTime);
        } else {
            log.info("No system env:KEEPMEDIATIME");
        }

    }

    @Override
    public void onLeaveChannel(int reason) {
        //System.out.println("RecordingSDK onLeaveChannel,code:" + reason);
    }

    @Override
    public void onError(int error, int stat_code) {
        System.out.println("RecordingSDK onError,error:" + error + ",stat code:" + stat_code);
    }

    @Override
    public void onWarning(int warn) {
        System.out.println("RecordingSDK onWarning,warn:" + warn);
    }

    @Override
    public void onJoinChannelSuccess(String channelId, long uid) {
        if (config.decodeAudio != Common.AUDIO_FORMAT_TYPE.AUDIO_FORMAT_DEFAULT_TYPE) {
            cleanTimer.schedule(new RecordingCleanTimer(this), 10000);
        }
        System.out.println("RecordingSDK joinChannel success, channelId:" + channelId + ", uid:" + uid);
    }

    @Override
    public void onRejoinChannelSuccess(String channelId, long uid) {
        System.out.println("onRejoinChannelSuccess, channel id : " + channelId + ", uid: " + uid);
    }

    @Override
    public void onConnectionStateChanged(Common.CONNECTION_STATE_TYPE state, Common.CONNECTION_CHANGED_REASON_TYPE reason) {
        //System.out.println("onConnectioNStatsChanged, stats: " + state + ", reason: " + reason);
    }

    @Override
    public void onRemoteAudioStats(long uid, Common.RemoteAudioStats stats) {
    /*
    System.out.println("onRemoteAudioStats, quality: " + stats.quality + ", networkTransportDelay : " + stats.networkTransportDelay + ", jitterBufferDelay:" + stats.jitterBufferDelay + ", audio loss rate : " + stats.audioLossRate);
    */
    }

    @Override
    public void onRemoteVideoStats(long uid, Common.RemoteVideoStats stats) {
    /*
    System.out.println("onRemoteVideoStats, delay : " + stats.delay + ", width" + stats.width + ", height : " + stats.height + ", receivedBitrate:" + stats.receivedBitrate + ", decoderOutputFrameRate:" + stats.decoderOutputFrameRate + ", rxStreamType : " + stats.rxStreamType);
    */
    }

    @Override
    public void onRecordingStats(Common.RecordingStats stats) {
    /*
    System.out.println("onRecordingStats, duration : " + stats.duration + ", rxByets " + stats.rxBytes + ", rxKBitRate: " + stats.rxKBitRate + ", rxAudioKBitRate: " + stats.rxAudioKBitRate + ", rxVideoKBitRate:" + stats.rxVideoKBitRate + ", lastmileDelay : " + stats.lastmileDelay + ", userCount : " + stats.userCount + ", cpuAppUsage : " + stats.cpuAppUsage + ", cpuTotalUsage: " + stats.cpuTotalUsage);
    */
    }

    @Override
    public void onUserOffline(long uid, int reason) {
        System.out.println("RecordingSDK onUserOffline uid:" + uid + ",offline reason:" + reason);
        m_peers.remove(uid);
        //PrintUsersInfo(m_peers);
        SetVideoMixingLayout();
    }


    @Override
    public void onUserJoined(long uid, String recordingDir) {
        System.out.println("onUserJoined uid:" + uid + ",recordingDir:" + recordingDir);
        storageDir = recordingDir;
        m_peers.add(uid);
        //PrintUsersInfo(m_peers);
        // When the user joined, we can re-layout the canvas
        if (userAccount.length() > 0) {
            if (layoutMode != VERTICALPRESENTATION_LAYOUT || RecordingSDKInstance.getUidByUserAccount(maxResolutionUserAccount) != 0) {
                SetVideoMixingLayout();
            }
        } else {
            SetVideoMixingLayout();
        }
    }

    @Override
    public void onLocalUserRegistered(long uid, String userAccount) {
        System.out.println("onLocalUserRegistered: " + uid + " => " + userAccount);
    }

    @Override
    public void onUserInfoUpdated(long uid, String userAccount) {
        System.out.println("onUserInfoUpdated: " + uid + " => " + userAccount);
        if (subscribedVideoUserAccount.contains(userAccount)) {
            subscribedVideoUids.add(uid);
        }
        SetVideoMixingLayout();
    }

    @Override
    public void onRemoteVideoStreamStateChanged(long uid, Common.REMOTE_STREAM_STATE state, Common.REMOTE_STREAM_STATE_CHANGED_REASON reason) {
        //System.out.println("OnRemoteVideoStreamState changed, state " + state + ", reason :" + reason);
    }

    @Override
    public void onRemoteAudioStreamStateChanged(long uid, Common.REMOTE_STREAM_STATE state, Common.REMOTE_STREAM_STATE_CHANGED_REASON reason) {
        //System.out.println("OnRemoteAudioStreamState changed, state " + state + ", reason :" + reason);
    }

    @Override
    public void onActiveSpeaker(long uid) {
        System.out.println("User:" + uid + "is speaking");
    }

    @Override
    public void onReceivingStreamStatusChanged(boolean receivingAudio, boolean receivingVideo) {
        System.out.println("pre receiving audio status is " + m_receivingAudio + ", now receiving audio status is " + receivingAudio);
        System.out.println("pre receiving video status is " + m_receivingVideo + ", now receiving video  status is " + receivingVideo);
        m_receivingAudio = receivingAudio;
        m_receivingVideo = receivingVideo;
    }

    @Override
    public void onConnectionLost() {
        System.out.println("connection is lost");
    }

    @Override
    public void onConnectionInterrupted() {
        System.out.println("connection is interrupted");
    }

    @Override
    public void onAudioVolumeIndication(Common.AudioVolumeInfo[] infos) {
        if (infos.length == 0) {
            return;
        }

        for (int i = 0; i < infos.length; i++) {
            System.out.println("User:" + Long.toString(infos[i].uid) + ", audio volume:" + infos[i].volume);
        }
    }

    @Override
    public void onFirstRemoteVideoDecoded(long uid, int width, int height, int elapsed) {
        System.out.println("onFirstRemoteVideoDecoded User:" + Long.toString(uid) + ", width:" + width
                + ", height:" + height + ", elapsed:" + elapsed);
    }

    @Override
    public void onFirstRemoteAudioFrame(long uid, int elapsed) {
        System.out.println("onFirstRemoteAudioFrame User:" + Long.toString(uid) + ", elapsed:" + elapsed);
    }

    @Override
    public void audioFrameReceived(long uid, Common.AudioFrame frame) {
        // System.out.println("java demo
        // audioFrameReceived,uid:"+uid+",type:"+type);
        byte[] buf = null;
        long size = 0;
        checkUser(uid, true);
        if (frame.type == Common.AUDIO_FRAME_TYPE.AUDIO_FRAME_RAW_PCM) {
            // pcm
            buf = frame.pcm.pcmBuf;
            size = frame.pcm.pcmBufSize;
        } else {
            // aac
            buf = frame.aac.aacBuf;
            size = frame.aac.aacBufSize;
        }
        WriteBytesToFileClassic(uid, buf, size, true);
    }

    @Override
    public void videoFrameReceived(long uid, int type, Common.VideoFrame frame, int rotation)// rotation:0,90,180,270
    {
        byte[] buf = null;
        long size = 0;
        checkUser(uid, false);
        // System.out.println("java demovideoFrameReceived,uid:"+uid+",type:"+type);

        if (type == 0) {
            // yuv
            buf = frame.yuv.buf;
            size = frame.yuv.bufSize;
            if (buf == null) {
                System.out.println("java demo videoFrameReceived null");
            }
        } else if (type == 1) {
            // h264
            buf = frame.h264.buf;
            size = frame.h264.bufSize;
        } else if (type == 2) {
            // h265
            buf = frame.h265.buf;
            size = frame.h265.bufSize;
        } else if (type == 3) {
            // jpg
            String path = storageDir + Long.toString(uid) + System.currentTimeMillis() + ".jpg";
            buf = frame.jpg.buf;
            size = frame.jpg.bufSize;
            try {
                FileOutputStream channel = new FileOutputStream(path, true);
                channel.write(buf, 0, (int) size);
                channel.close();
            } catch (Exception e) {
                System.out.println("Error write to " + path);
            }
            return;
        }
        WriteBytesToFileClassic(uid, buf, size, false);
    }

    /*
     * Brief: Callback when call createChannel successfully
     *
     * @param path recording file directory
     */
    @Override
    public void recordingPathCallBack(String path) {
        storageDir = path;
    }


    public void clean() {
        synchronized (this) {
            log.info("执行清理任务");
            long now = System.currentTimeMillis();
            Iterator<Map.Entry<String, UserInfo>> audio_it = audioChannels.entrySet().iterator();
            while (audio_it.hasNext()) {
                Map.Entry<String, UserInfo> entry = audio_it.next();
                UserInfo info = entry.getValue();
                if (now - info.last_receive_time > 3000) {
                    try {
                        info.channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    audio_it.remove();
                }
            }
            Iterator<Map.Entry<String, UserInfo>> video_it = videoChannels.entrySet().iterator();
            while (video_it.hasNext()) {
                Map.Entry<String, UserInfo> entry = video_it.next();
                UserInfo info = entry.getValue();
                if (now - info.last_receive_time > 3000) {
                    try {
                        info.channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    video_it.remove();
                }
            }
        }
        cleanTimer.schedule(new RecordingCleanTimer(this), 10000);
    }


    private int SetVideoMixingLayout() {
        Common ei = new Common();
        Common.VideoMixingLayout layout = ei.new VideoMixingLayout();
        layout.keepLastFrame = this.keepLastFrame;
        int max_peers = profile_type == Common.CHANNEL_PROFILE_TYPE.CHANNEL_PROFILE_COMMUNICATION ? 7 : 17;
        if (m_peers.size() > max_peers) {
            System.out.println("peers size is bigger than max m_peers:" + m_peers.size());
            return -1;
        }

        if (!isMixingEnabled()) {
            return -1;
        }

        long maxuid = 0;
        if (userAccount.length() > 0) {
            maxuid = RecordingSDKInstance.getUidByUserAccount(maxResolutionUserAccount);
        } else {
            maxuid = maxResolutionUid;
        }

        Vector<Long> videoUids = new Vector<Long>();
        Iterator it = m_peers.iterator();
        while (it.hasNext()) {
            Long uid = (Long) it.next();
            if (!config.autoSubscribe && !subscribedVideoUids.contains(uid)) {
                continue;
            }
            if (layoutMode == VERTICALPRESENTATION_LAYOUT) {
                String uc = RecordingSDKInstance.getUserAccountByUid((int) (long) uid);
                if (uc.length() > 0 || maxuid != 0) {
                    videoUids.add(uid);
                }
            } else {
                videoUids.add(uid);
            }
        }

        layout.canvasHeight = height;
        layout.canvasWidth = width;
        layout.backgroundColor = "#23b9dc";
        layout.regionCount = (int) (videoUids.size());

        if (!videoUids.isEmpty()) {
            System.out.println("java setVideoMixingLayout videoUids is not empty, start layout");
            Common.VideoMixingLayout.Region[] regionList = new Common.VideoMixingLayout.Region[videoUids.size()];
            System.out.println("mixing layout mode:" + layoutMode);
            if (layoutMode == BESTFIT_LAYOUT) {
                adjustBestFitVideoLayout(regionList, layout, videoUids);
            } else if (layoutMode == VERTICALPRESENTATION_LAYOUT) {
                adjustVerticalPresentationLayout(maxuid, regionList, layout, videoUids);
            } else {
                adjustDefaultVideoLayout(regionList, layout, videoUids);
            }

            layout.regions = regionList;

        } else {
            layout.regions = null;
        }

        return RecordingSDKInstance.setVideoMixingLayout(layout);
    }

    private void checkUser(long uid, boolean isAudio) {
        String path = storageDir + Long.toString(uid);
        String key = Long.toString(uid);
        synchronized (this) {
            if (isAudio && !audioChannels.containsKey(key)) {
                if (config.decodeAudio == Common.AUDIO_FORMAT_TYPE.AUDIO_FORMAT_AAC_FRAME_TYPE ||
                        config.decodeAudio == Common.AUDIO_FORMAT_TYPE.AUDIO_FORMAT_PCM_FRAME_TYPE ||
                        config.decodeAudio == Common.AUDIO_FORMAT_TYPE.AUDIO_FORMAT_MIXED_PCM_FRAME_TYPE) {
                    String audioPath;
                    if (config.decodeAudio == Common.AUDIO_FORMAT_TYPE.AUDIO_FORMAT_AAC_FRAME_TYPE) {
                        audioPath = path + ".aac";
                    } else {
                        audioPath = path + ".pcm";
                    }
                    try {
                        UserInfo info = new UserInfo();
                        info.fileName = audioPath;
                        info.channel = new FileOutputStream(audioPath, true);
                        info.last_receive_time = System.currentTimeMillis();
                        audioChannels.put(key, info);
                    } catch (FileNotFoundException e) {
                        System.out.println("Can't find file : " + audioPath);
                    }
                }
            }

            if (!isAudio && !videoChannels.containsKey(key)) {
                if (config.decodeVideo == Common.VIDEO_FORMAT_TYPE.VIDEO_FORMAT_YUV_FRAME_TYPE
                        || config.decodeVideo == Common.VIDEO_FORMAT_TYPE.VIDEO_FORMAT_H264_FRAME_TYPE
                        || config.decodeVideo == Common.VIDEO_FORMAT_TYPE.VIDEO_FORMAT_ENCODED_FRAME_TYPE) {
                    String videoPath;
                    if (config.decodeVideo == Common.VIDEO_FORMAT_TYPE.VIDEO_FORMAT_ENCODED_FRAME_TYPE
                            || config.decodeVideo == Common.VIDEO_FORMAT_TYPE.VIDEO_FORMAT_H264_FRAME_TYPE) {
                        videoPath = path + ".h264";
                    } else {
                        videoPath = path + ".yuv";
                    }
                    try {
                        UserInfo info = new UserInfo();
                        info.fileName = videoPath;
                        info.channel = new FileOutputStream(videoPath, true);
                        info.last_receive_time = System.currentTimeMillis();
                        videoChannels.put(key, info);
                    } catch (FileNotFoundException e) {
                        System.out.println("Can't find file : " + videoPath);
                    }
                }
            }
        }
    }


    private boolean isMixingEnabled() {
        return this.isMixingEnabled;
    }

    private void adjustVerticalPresentationLayout(long maxResolutionUid, Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        System.out.println("begin adjust vertical presentation layout,peers size:" + videoUids.size() + ", maxResolutionUid:" + maxResolutionUid);
        if (videoUids.size() <= 5) {
            adjustVideo5Layout(maxResolutionUid, regionList, layout, videoUids);
        } else if (videoUids.size() <= 7) {
            adjustVideo7Layout(maxResolutionUid, regionList, layout, videoUids);
        } else if (videoUids.size() <= 9) {
            adjustVideo9Layout(maxResolutionUid, regionList, layout, videoUids);
        } else {
            adjustVideo17Layout(maxResolutionUid, regionList, layout, videoUids);
        }
    }

    private void adjustBestFitVideoLayout(Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        if (videoUids.size() == 1) {
            adjustBestFitLayout_Square(regionList, 1, layout, videoUids);
        } else if (videoUids.size() == 2) {
            adjustBestFitLayout_2(regionList, layout, videoUids);
        } else if (2 < videoUids.size() && videoUids.size() <= 4) {
            adjustBestFitLayout_Square(regionList, 2, layout, videoUids);
        } else if (5 <= videoUids.size() && videoUids.size() <= 9) {
            adjustBestFitLayout_Square(regionList, 3, layout, videoUids);
        } else if (10 <= videoUids.size() && videoUids.size() <= 16) {
            adjustBestFitLayout_Square(regionList, 4, layout, videoUids);
        } else if (videoUids.size() == 17) {
            adjustBestFitLayout_17(regionList, layout, videoUids);
        } else {
            System.out.println("adjustBestFitVideoLayout is more than 17 users");
        }
    }

    private void adjustBestFitLayout_2(Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        float canvasWidth = (float) width;
        float canvasHeight = (float) height;
        float viewWidth = 0.235f;
        float viewHEdge = 0.012f;
        float viewHeight = viewWidth * (canvasWidth / canvasHeight);
        float viewVEdge = viewHEdge * (canvasWidth / canvasHeight);
        int peersCount = videoUids.size();
        for (int i = 0; i < peersCount; i++) {
            regionList[i] = layout.new Region();
            regionList[i].uid = videoUids.get(i);
            regionList[i].x = (((i + 1) % 2) == 0) ? 0 : 0.5;
            regionList[i].y = 0.f;
            regionList[i].width = 0.5f;
            regionList[i].height = 1.f;
            regionList[i].alpha = i + 1;
            regionList[i].renderMode = 0;
        }
    }

    private void adjustDefaultVideoLayout(Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        regionList[0] = layout.new Region();
        regionList[0].uid = videoUids.get(0);
        regionList[0].x = 0.f;
        regionList[0].y = 0.f;
        regionList[0].width = 1.f;
        regionList[0].height = 1.f;
        regionList[0].alpha = 1.f;
        regionList[0].renderMode = 0;
        float f_width = width;
        float f_height = height;
        float canvasWidth = f_width;
        float canvasHeight = f_height;
        float viewWidth = 0.235f;
        float viewHEdge = 0.012f;
        float viewHeight = viewWidth * (canvasWidth / canvasHeight);
        float viewVEdge = viewHEdge * (canvasWidth / canvasHeight);
        for (int i = 1; i < videoUids.size(); i++) {
            regionList[i] = layout.new Region();

            regionList[i].uid = videoUids.get(i);
            float f_x = (i - 1) % 4;
            float f_y = (i - 1) / 4;
            float xIndex = f_x;
            float yIndex = f_y;
            regionList[i].x = xIndex * (viewWidth + viewHEdge) + viewHEdge;
            regionList[i].y = 1 - (yIndex + 1) * (viewHeight + viewVEdge);
            regionList[i].width = viewWidth;
            regionList[i].height = viewHeight;
            regionList[i].alpha = (i + 1);
            regionList[i].renderMode = 0;
        }
        layout.regions = regionList;
    }

    private void setMaxResolutionUid(int number, long maxResolutionUid, Common.VideoMixingLayout.Region[] regionList, double weight_ratio) {
        regionList[number].uid = maxResolutionUid;
        regionList[number].x = 0.f;
        regionList[number].y = 0.f;
        regionList[number].width = 1.f * weight_ratio;
        regionList[number].height = 1.f;
        regionList[number].alpha = 1.f;
        regionList[number].renderMode = 1;
    }

    private void changeToVideo7Layout(long maxResolutionUid, Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        System.out.println("changeToVideo7Layout");
        adjustVideo7Layout(maxResolutionUid, regionList, layout, videoUids);
    }

    private void changeToVideo9Layout(long maxResolutionUid, Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        System.out.println("changeToVideo9Layout");
        adjustVideo9Layout(maxResolutionUid, regionList, layout, videoUids);
    }

    private void changeToVideo17Layout(long maxResolutionUid, Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        System.out.println("changeToVideo17Layout");
        adjustVideo17Layout(maxResolutionUid, regionList, layout, videoUids);
    }

    private void adjustBestFitLayout_Square(Common.VideoMixingLayout.Region[] regionList, int nSquare, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        float canvasWidth = (float) width;
        float canvasHeight = (float) height;
        float viewWidth = (float) (1.f * 1.0 / nSquare);
        float viewHEdge = (float) (1.f * 1.0 / nSquare);
        float viewHeight = viewWidth * (canvasWidth / canvasHeight);
        float viewVEdge = viewHEdge * (canvasWidth / canvasHeight);
        int peersCount = videoUids.size();
        for (int i = 0; i < peersCount; i++) {
            regionList[i] = layout.new Region();
            float xIndex = (float) (i % nSquare);
            float yIndex = (float) (i / nSquare);
            regionList[i].uid = videoUids.get(i);
            regionList[i].x = 1.f * 1.0 / nSquare * xIndex;
            regionList[i].y = 1.f * 1.0 / nSquare * yIndex;
            regionList[i].width = viewWidth;
            regionList[i].height = viewHEdge;
            regionList[i].alpha = (double) (i + 1);
            regionList[i].renderMode = 0;
        }
    }

    private void adjustBestFitLayout_17(Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        float canvasWidth = (float) width;
        float canvasHeight = (float) height;
        int n = 5;
        float viewWidth = (float) (1.f * 1.0 / n);
        float viewHEdge = (float) (1.f * 1.0 / n);
        float totalWidth = (float) (1.f - viewWidth);
        float viewHeight = viewWidth * (canvasWidth / canvasHeight);
        float viewVEdge = viewHEdge * (canvasWidth / canvasHeight);
        int peersCount = videoUids.size();
        for (int i = 0; i < peersCount; i++) {
            regionList[i] = layout.new Region();
            float xIndex = (float) (i % (n - 1));
            float yIndex = (float) (i / (n - 1));
            regionList[i].uid = videoUids.get(i);
            regionList[i].width = viewWidth;
            regionList[i].height = viewHEdge;
            regionList[i].alpha = i + 1;
            regionList[i].renderMode = 0;
            if (i == 16) {
                regionList[i].x = (1 - viewWidth) * (1.f / 2) * 1.f;
                System.out.println("special layout for 17 x is:" + regionList[i].x);
            } else {
                regionList[i].x = 0.5f * viewWidth + viewWidth * xIndex;
            }
            regionList[i].y = (1.0 / n) * yIndex;
        }
    }

    private void adjustVideo5Layout(long maxResolutionUid, Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        boolean flag = false;

        float canvasWidth = (float) width;
        float canvasHeight = (float) height;

        float viewWidth = 0.235f;
        float viewHEdge = 0.012f;
        float viewHeight = viewWidth * (canvasWidth / canvasHeight);
        float viewVEdge = viewHEdge * (canvasWidth / canvasHeight);
        int number = 0;

        int i = 0;
        for (; i < videoUids.size(); i++) {
            regionList[i] = layout.new Region();
            if (maxResolutionUid == videoUids.get(i)) {
                System.out.println("adjustVideo5Layout equal with configured user uid:" + maxResolutionUid);
                flag = true;
                setMaxResolutionUid(number, maxResolutionUid, regionList, 0.8);
                number++;
                continue;
            }
            regionList[number].uid = videoUids.get(i);
            //float xIndex = ;
            float yIndex = flag ? ((float) (number - 1 % 4)) : ((float) (number % 4));
            regionList[number].x = 1.f * 0.8;
            regionList[number].y = (0.25) * yIndex;
            regionList[number].width = 1.f * (1 - 0.8);
            regionList[number].height = 1.f * (0.25);
            regionList[number].alpha = (double) number;
            regionList[number].renderMode = 0;
            number++;
            if (i == 4 && !flag) {
                changeToVideo7Layout(maxResolutionUid, regionList, layout, videoUids);
            }
        }
    }


    private void adjustVideo7Layout(long maxResolutionUid, Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        boolean flag = false;
        float canvasWidth = (float) width;
        float canvasHeight = (float) height;

        float viewWidth = 0.235f;
        float viewHEdge = 0.012f;
        float viewHeight = viewWidth * (canvasWidth / canvasHeight);
        float viewVEdge = viewHEdge * (canvasWidth / canvasHeight);
        int number = 0;

        int i = 0;
        for (; i < videoUids.size(); i++) {
            regionList[i] = layout.new Region();
            if (maxResolutionUid == videoUids.get(i)) {
                System.out.println("adjustVideo7Layout equal with configured user uid:" + maxResolutionUid);
                flag = true;
                setMaxResolutionUid(number, maxResolutionUid, regionList, 6.f / 7);
                number++;
                continue;
            }
            regionList[number].uid = videoUids.get(i);
            float yIndex = flag ? ((float) number - 1 % 6) : ((float) (number % 6));
            regionList[number].x = 6.f / 7;
            regionList[number].y = (1.f / 6) * yIndex;
            regionList[number].width = (1.f / 7);
            regionList[number].height = (1.f / 6);
            regionList[number].alpha = (double) number;
            regionList[number].renderMode = 0;
            number++;
            if (i == 6 && !flag) {
                changeToVideo9Layout(maxResolutionUid, regionList, layout, videoUids);
            }
        }

    }

    private void adjustVideo9Layout(long maxResolutionUid, Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        boolean flag = false;

        float canvasWidth = (float) width;
        float canvasHeight = (float) height;

        float viewWidth = 0.235f;
        float viewHEdge = 0.012f;
        float viewHeight = viewWidth * (canvasWidth / canvasHeight);
        float viewVEdge = viewHEdge * (canvasWidth / canvasHeight);
        int number = 0;

        int i = 0;
        for (; i < videoUids.size(); i++) {
            regionList[i] = layout.new Region();
            if (maxResolutionUid == videoUids.get(i)) {
                System.out.println("adjustVideo9Layout equal with configured user uid:" + maxResolutionUid);
                flag = true;
                setMaxResolutionUid(number, maxResolutionUid, regionList, 9.f / 5);
                number++;
                continue;
            }
            regionList[number].uid = videoUids.get(i);
            float yIndex = flag ? ((float) (number - 1 % 8)) : ((float) (number % 8));
            regionList[number].x = 8.f / 9;
            regionList[number].y = (1.f / 8) * yIndex;
            regionList[number].width = 1.f / 9;
            regionList[number].height = 1.f / 8;
            regionList[number].alpha = (double) number;
            regionList[number].renderMode = 0;
            number++;
            if (i == 8 && !flag) {
                changeToVideo17Layout(maxResolutionUid, regionList, layout, videoUids);
            }
        }
    }

    private void adjustVideo17Layout(long maxResolutionUid, Common.VideoMixingLayout.Region[] regionList, Common.VideoMixingLayout layout, Vector<Long> videoUids) {
        boolean flag = false;
        float canvasWidth = (float) width;
        float canvasHeight = (float) height;

        float viewWidth = 0.235f;
        float viewHEdge = 0.012f;
        float viewHeight = viewWidth * (canvasWidth / canvasHeight);
        float viewVEdge = viewHEdge * (canvasWidth / canvasHeight);
        int number = 0;
        System.out.println("adjustVideo17Layoutenter videoUids size is:" + videoUids.size() + ", maxResolutionUid:" + maxResolutionUid);
        for (int i = 0; i < videoUids.size(); i++) {
            regionList[i] = layout.new Region();
            if (maxResolutionUid == videoUids.get(i)) {
                flag = true;
                setMaxResolutionUid(number, maxResolutionUid, regionList, 0.8);
                number++;
                continue;
            }
            if (!flag && i == 16) {
                System.out.println("Not the configured uid, and small regions is sixteen, so ignore this user:" + videoUids.get(i));
                break;
            }

            regionList[number].uid = videoUids.get(i);
            //float xIndex = 0.833f;
            float yIndex = flag ? ((float) ((number - 1) % 8)) : ((float) (number % 8));
            regionList[number].x = ((flag && i > 8) || (!flag && i >= 8)) ? (9.f / 10) : (8.f / 10);
            regionList[number].y = (1.f / 8) * yIndex;
            regionList[number].width = 1.f / 10;
            regionList[number].height = 1.f / 8;
            regionList[number].alpha = (double) number;
            regionList[number].renderMode = 0;
            number++;
        }
    }

    private void WriteBytesToFileClassic(long uid, byte[] byteBuffer, long size, boolean isAudio) {
        if (byteBuffer == null) {
            System.out.println("WriteBytesToFileClassic but byte buffer is null!");
            return;
        }
        synchronized (this) {
            try {
                UserInfo info = isAudio ? audioChannels.get(Long.toString(uid)) : videoChannels.get(Long.toString(uid));
                if (info != null) {
                    long curTs = System.currentTimeMillis();
                    if (isAudio) {
                        if (keepMediaTime > 0 && (curTs - lastKeepAudioTime) / 1000 >= keepMediaTime) {
                            // System.out.printf("rewrite audio file:%s\n", info.fileName);
                            info.channel.close();
                            info.channel = new FileOutputStream(info.fileName, false);
                            lastKeepAudioTime = curTs;
                        }
                    } else {
                        if (keepMediaTime > 0 && (curTs - lastKeepVideoTime) / 1000 >= keepMediaTime) {
                            // System.out.printf("rewrite video file:%s\n", info.fileName);
                            info.channel.close();
                            info.channel = new FileOutputStream(info.fileName, false);
                            lastKeepVideoTime = curTs;
                        }
                    }
                    info.channel.write(byteBuffer, 0, (int) size);
                    info.channel.flush();
                    info.last_receive_time = System.currentTimeMillis();
                } else {
                    System.out.println("Channel is null");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
