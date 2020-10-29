package com.r7.core.stand.video.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zs
 * @description: AgoraProperties
 * @date : 2020-10-21
 */
@Component
@ConfigurationProperties("agora")
@Data
public class AgoraProperties {
    /**
     * 声网平台id
     */
    private String appId;
    /**
     * 服务端录制uid
     * 录制使用的用户 UID。同一个频道里不能出现两个相同的 UID，否则会产生未定义行为。有两种设置方法：
     * 设置为 0，系统将自动分配一个 UID。
     * 设置一个唯一的 UID，不能与现在频道内的任何 UID 重复。取值范围：1 到 (232-1)。
     */
    private Integer serverUid = 0;
    /**
     * 证书
     */
    private String appCertificate;
    /**
     * token到期时间
     */
    private Integer expirationTimeInSeconds;
    /**
     * 启用音视频混合
     * 单流录制模式：（ 默认录制模式）分开录制频道内每个 UID 的音频流和视频流。每个 UID 均有其对应的音频文件和视频文件。
     * 合流录制模式： 频道内所有或指定 UID 的音视频混合录制为一个音视频文件；或频道内所有或指定 UID 的音频混合录制为一个纯音频文件，所有或指定 UID 的视频混合录制为一个纯视频文件。
     */
    private boolean isMixingEnabled = true;
    /**
     * 混合音视频类型
     * 0：（默认）不混合音频和视频。
     * 1：音频和视频混合成一个文件，录制文件格式为 MP4，但播放器支持有限。
     * 2：音频和视频混合成一个文件，录制文件格式为 MP4，支持更多播放器。
     */
    private String mixedVideoAudio;
    /**
     * 如果你启用了合流模式，可以通过该参数设置录制文件的视频属性，
     * 格式为：width，hight，fps，kbps，分别对应合流的宽、高、帧率和码率。
     * 默认设置为 360 x 640, 15 fps, 500 Kbps。
     */
    private String mixResolution;
    /**
     * 录制指定 UID 的音视频流。UID 组成的数组，为用逗号隔开的字符串，例如 "1","2","3"。
     * 当 autoSubscribe 为 false时，你可设置此参数来指定 UID 进行录制。该参数设为 NULL 则表示录制所有发流用户的视频。
     */
    private boolean autoSubscribe;
    /**
     * 订阅音频uids
     */
    private String subscribeAudioUids;
    /**
     * 订阅视频uids
     */
    private String subscribeVideoUids;
    /**
     * 最低的UDP端口
     */
    private Integer lowUdpPort;
    /**
     * 最高的UDP端口
     */
    private Integer highUdpPort;
    /**
     * 设置AgoraCoreService的路径
     */
    private String appliteDir;
    /**
     * 设置agora动态链接库地址
     */
    private String agoraLib;
    /**
     * 设置录制文件路径
     */
    private String recordFileRootDir;
    /**
     * 日志级别
     * 1: 日志等级为 Fatal。
     * 2: 日志等级为 Error。
     * 3: 日志等级为 Warning。
     * 4: 日志等级为 Notice。
     * 5: 日志等级为 Info。
     * 6: 日志等级为 Debug。
     */
    private int logLevel;
}
