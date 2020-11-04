package com.r7.core.cache.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * redis hash类型数据存储服务
 * @author wt
 */
public interface RedisHashService {

    /**
     * 数据批量加到缓存中
     * @param key 缓存的key
     * @param value 缓存的对象
     */
    void addHashAll(String key, Map<String, Object> value);

    /**
     * 缓存单个数据
     * @param redisKey 缓存的key
     * @param key 缓存的对象的属性名
     * @param value 缓存的对象的属性值
     */
    void addHashValue(String redisKey, String key, Object value);


    /**
     * 缓存单个数据
     * @param redisKey 缓存的key
     * @param key 缓存的对象的属性名
     * @param value 缓存的对象的属性值
     * @param time 缓存有效时间
     * @param timeUnit 有效时间单位
     */
    void addHashValue(String redisKey, String key, Object value, long time, TimeUnit timeUnit);

    /**
     * 根据缓存key获取缓存的对象
     * @param redisKey 缓存的key
     * @param key 缓存对象的属性名
     * @return 缓存的信息
     */
    Object getByKeyAndRedisKey(String redisKey, String key);

    /**
     * 修改缓存的信息
     * @param redisKey 缓存key
     * @param key 缓存对象的属性名
     * @param value 缓存对象的新属性值
     * @return 修改后的缓存信息
     */
    Object updateValueByKeyAndRedisKey(String redisKey, String key, Object value);

    /**
     * 删除缓存中指定的属性值
     * @param redisKey 缓存key
     * @param key 缓存对象的属性名
     */
     void removeByKeyAndRedisKey(String redisKey, String key);


    /**
     * 删除指定的缓存key对应的缓存
     * @param redisKey 缓存的key
     */
     void removeAll(String redisKey);




}
