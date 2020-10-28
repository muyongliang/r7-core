package com.r7.core.cache.service;

import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;

/**
 * redis zSet类型数据存储服务
 *
 * @author wt
 */
public interface RedisZSetService {

    /**
     * 增加缓存
     * @param key 缓存的key
     * @param value 缓存值
     * @param score 排序字段即分数
     */
     void add(String key, Object value, Double score);

    /**
     * 增加缓存
     * @param key 缓存的key
     * @param value 缓存值
     * @param score 排序字段即分数
     * @param time 有效时间
     * @param timeUnit 时间单位
     */
    void add(String key, Object value, Double score,long time, TimeUnit timeUnit);



    /**
     * 删除指定的值
     * @param key 缓存的key
     * @param value 缓存的值
     */
     void removeValueByKey(String key, Object value);

    /**
     * 根据key删除缓存
     * @param key 缓存key
     */
    void removeAllByKey(String key);

    /**
     * 查询缓存
     * @param key 缓存的key
     * @return 缓存信息
     */
    LinkedHashSet<Object> getAllByKey(String key);


    /**
     * 查询指定排序值之间的缓存数据
     * @param key 缓存的key
     * @param scoreFrom 开始排序值
     * @param scoreTo 结束排序值
     * @return 缓存的信息
     */
     LinkedHashSet<Object> getByScore(String key, Double scoreFrom, Double scoreTo);

    /**
     * 查询缓存的排序值score
     *
     * @param key 缓存的key
     * @param value 缓存的值
     * @return score
     */
    Double rankByKeyAndValue(String key, Object value);

    /**
     * 根据下标获取指定范围的元素值
     * @param key 缓存的key
     * @param start 开始下标
     * @param end 结束下标
     * @return 缓存的信息
     */
     LinkedHashSet<Object> getValuesByKey(String key,Long start, Long end);
}
