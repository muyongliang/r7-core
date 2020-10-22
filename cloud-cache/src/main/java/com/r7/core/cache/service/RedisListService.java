package com.r7.core.cache.service;

import com.r7.core.cache.constant.PushType;

import java.util.List;

/**
 * redis list数据类型存储服务
 *
 * @author zhongpingli
 */
public interface RedisListService {

    /**
     * 新增缓存
     *
     * @param key       缓存key
     * @param listValue 缓存valud
     * @param type      新增方向
     */
    <T> void addListValue(String key, List<T> listValue, PushType type);


    /**
     * 根据key对单个list数据增加数据
     *
     * @param key   缓存key
     * @param value 新增缓存数据
     * @param type  新增方向
     */
    void addValue(String key, Object value, PushType type);

    /**
     * 根据key获取缓存信息
     *
     * @param key key
     * @param t   反序列化对象
     * @param <T> 范型
     * @return 返回缓存信息
     */
    <T> List<T> getKey(String key, Class<T> t);

    /**
     * 根据key修改指定缓存信息
     *
     * @param key      缓存可以
     * @param t        反序列化对象
     * @param index    修改指定value
     * @param newValue 修改的缓存信息
     * @param <T>      范型
     * @return 返回修改后的信息
     */
    <T> T updateValueByKey(String key, T newValue, Integer index, Class<T> t);

    /**
     * 根据key删除缓存
     *
     * @param key 缓存key
     */
    void removeByKey(String key);


}
