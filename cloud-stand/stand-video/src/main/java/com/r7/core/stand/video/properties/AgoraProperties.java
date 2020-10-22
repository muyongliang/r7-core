package com.r7.core.stand.video.properties;

import com.r7.core.stand.video.common.Common.MIXED_AV_CODEC_TYPE;
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
     * 单流录制模式：（默认录制模式）分开录制频道内每个 UID 的音频流和视频流。每个 UID 均有其对应的音频文件和视频文件。
     * 合流录制模式：频道内所有或指定 UID 的音视频混合录制为一个音视频文件；或频道内所有或指定 UID 的音频混合录制为一个纯音频文件，所有或指定 UID 的视频混合录制为一个纯视频文件。
     */
    private Boolean isMixingEnabled;
    /**
     * 混合音视频类型
     */
    private MIXED_AV_CODEC_TYPE mixedVideoAudio;
    /**
     * 开始录制时设置 --isMixingEnabled 1 使用合流模式，同时设置 --layoutMode 参数选择一种布局‘
     * 0 使用悬浮布局（默认）
     * 1 使用自适应布局
     * 2 使用垂直布局
     */
    private int layoutMode;
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
    private Integer logLevel;
}
