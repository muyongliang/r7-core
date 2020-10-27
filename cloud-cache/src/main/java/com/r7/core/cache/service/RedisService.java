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
     * @param value 缓存valud
     */
    void addValue(String key, Object value);

    /**
     *
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    void addValue(String key, Object value, long time, TimeUnit timeUnit);

    /**
     * 根据key获取缓存信息
     *
     * @param key key
     * @param t   反序列化对象
     * @param <T> 范型
     * @return 返回缓存信息
     */
    <T> T getKey(String key, Class<T> t);

    /**
     * 根据key修改缓存信息
     *
     * @param key      缓存可以
     * @param newValue 修改的缓存信息
     * @param <T>      范型
     * @return 返回修改后的信息
     */
    <T> T updateValueByKey(String key, Object newValue, Class<T> t);

    /**
     * 根据key删除缓存
     *
     * @param key 缓存key
     */
    void removeByKey(String key);

}
