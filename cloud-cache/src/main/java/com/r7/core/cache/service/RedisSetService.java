package com.r7.core.cache.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis set类型数据存储服务
 *
 * @author wt
 */
public interface RedisSetService {

    /**
     * 增加单个缓存数据
     *
     * @param key:  缓存key
     * @param value 缓存值
     */
    void addValue(String key, Object value);

    /**
     * 增加单个缓存数据
     *
     * @param key:     缓存key
     * @param value    缓存值
     * @param time     缓存有效时间
     * @param timeUnit 有效时间单位
     */
    void addValue(String key, Object value, long time, TimeUnit timeUnit);


    /**
     * 批量添加缓存数据
     *
     * @param key:   缓存的key
     * @param set；参数
     */
    <T> void addAll(String key, Set<T> set);

    /**
     * 删除单个缓存值
     *
     * @param key:   缓存的key
     * @param value: 缓存的值
     */
    void removeValueByKey(String key, Object value);

    /**
     * 根据key删除缓存
     *
     * @param key：缓存的key
     */
    void removeAllByKey(String key);

    /**
     * 查询该key查询全部缓存
     *
     * @param key 缓存的key
     * @return 缓存的数据
     */
    Set<Object> getAll(String key);

}
