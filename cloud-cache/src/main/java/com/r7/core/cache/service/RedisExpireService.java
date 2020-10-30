package com.r7.core.cache.service;

import java.util.concurrent.TimeUnit;

/**
 * @author wt
 * @Description redis 缓存有效期设置服务
 */
public interface RedisExpireService {

    /**
     * 设置有效期
     *
     * @param key  缓存的key
     * @param time 有效时间
     * @param type 时间类型
     */
    void setExpire(String key, long time, TimeUnit type);
}
