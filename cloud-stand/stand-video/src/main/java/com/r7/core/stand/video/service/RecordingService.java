package com.r7.core.stand.video.service;

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
    public boolean createChannel(String appId, String channel, String channelKey, Integer... uids);

}
