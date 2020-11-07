package com.r7.core.stand.video.service;

import io.agora.media.RtcTokenBuilder;

/**
 * @author zs
 * @description: 录制服务层
 * @date : 2020-10-20
 */
public interface RecordingService {

    /**
     * 创建频道并加入
     *
     * @param appId      应用id
     * @param channel    频道名
     * @param channelKey 频道token
     * @param uids       用户ids
     * @return 返回是否成功
     */
    public boolean createChannel(String fileName, String channel, Integer... uids) throws Exception;

    /**
     * @param channel 频道名
     * @param Role    角色
     * @return
     */
    public String generateToken(String channel, RtcTokenBuilder.Role Role);

}
