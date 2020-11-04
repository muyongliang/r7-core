package com.r7.core.cache.service.impl;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r7.core.cache.service.RedisExpireService;
import com.r7.core.cache.service.RedisZSetService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redis zset crud 服务实现层
 *
 * @author zhongpingli
 */
@Service
public class RedisZSetServiceImpl implements RedisZSetService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Resource
    private RedisExpireService redisExpireService;


    @Override
    public void add(String key, Object value, Double score) {

        redisTemplate.opsForZSet().add(key,JSONUtil.toJsonStr(value),score);
    }


    @Override
    public void add(String key, Object value, Double score, long time, TimeUnit timeUnit) {
        redisTemplate.opsForZSet().add(key,JSONUtil.toJsonStr(value),score);
        redisExpireService.setExpire(key,time, timeUnit);
    }

    @Override
    public void removeValueByKey(String key, Object value) {
        redisTemplate.opsForZSet().remove(key,value);
    }

    @Override
    public void removeAllByKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public  LinkedHashSet<Object> getAllByKey(String key) {

        Set<Object> values  =  redisTemplate.opsForZSet().range(key,0,-1);
        if (values == null || values.size() == 0) {
            return null;
        }
        return (LinkedHashSet<Object>) values;
    }

    @Override
    public   LinkedHashSet<Object> getByScore(String key, Double scoreFrom, Double scoreTo) {

        Set<Object> objects  =  redisTemplate.opsForZSet()
                .rangeByScore(key,scoreFrom,scoreTo);

        if (objects == null || objects.size() == 0) {
            return null;
        }
        return (LinkedHashSet<Object>) objects;
    }

    @Override
    public Double   rankByKeyAndValue(String key, Object value) {
        return redisTemplate.opsForZSet().score(key,value);
    }

    @Override
    public  LinkedHashSet<Object> getValuesByKey(String key, Long start, Long end) {

        Set<Object> objects  =  redisTemplate.opsForZSet().range(key, start, end);
        if (objects == null || objects.size() == 0) {
            return null;
        }
        return (LinkedHashSet<Object>) objects;
    }
}
