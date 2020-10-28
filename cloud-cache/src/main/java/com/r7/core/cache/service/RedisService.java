package com.r7.core.cache.service;


import java.util.concurrent.TimeUnit;

/**
 * redis 普通CRUD服务
 *
 * @author zhongpingli
 */
public interface RedisService {

    /**
     * 新增缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    void addValue(String key, String value);

    /**
     * 新增缓存 带时间
     *
     * @param key      缓存key
     * @param value    缓存值
     * @param time     缓存时间
     * @param timeUnit 缓存单位
     */
    void addValue(String key, String value, long time, TimeUnit timeUnit);

    /**
     * 根据key获取缓存信息
     *
     * @param key key
     * @return 返回缓存信息
     */
    String getKey(String key);

    /**
     * 根据key修改缓存信息
     *
     * @param key      缓存可以
     * @param newValue 修改的缓存信息
     * @return 返回修改后的信息
     */
    String updateValueByKey(String key, String newValue);

    /**
     * 根据key删除缓存
     *
     * @param key 缓存key
     */
    void removeByKey(String key);

}
