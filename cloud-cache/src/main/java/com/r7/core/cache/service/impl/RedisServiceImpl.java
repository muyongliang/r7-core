package com.r7.core.cache.service.impl;

import cn.hutool.json.JSONUtil;
import com.r7.core.cache.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redis普通服务实现层
 *
 * @author zhongpingli
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public void addValue(String key, String value) {
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value));
    }

    @Override
    public void addValue(String key, String value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, timeUnit);
    }

    @Override
    public String getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public String updateValueByKey(String key, String newValue) {
        return redisTemplate.opsForValue().getAndSet(key, newValue);
    }

    @Override
    public void removeByKey(String key) {
        redisTemplate.delete(key);
    }
}
