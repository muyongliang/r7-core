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
     * @param channel 频道名
     * @param uid     用户id
     * @return 返回是否成功
     */
    public boolean createChannel(String appId, String channel, String channelKey, Integer... uids);

}
