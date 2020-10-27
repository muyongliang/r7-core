package com.r7.core.cache.service.impl;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void addValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value));
    }

    @Override
    public void addValue(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, timeUnit);
    }

    @Override
    public <T> T getKey(String key, Class<T> t) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return objectMapper.convertValue(value, t);
    }

    @Override
    public <T> T updateValueByKey(String key, Object newValue, Class<T> t) {
        Object value = redisTemplate.opsForValue().getAndSet(key, newValue);
        if (value == null) {
            return null;
        }
        return objectMapper.convertValue(value, t);
    }

    @Override
    public void removeByKey(String key) {
        redisTemplate.delete(key);
    }
}
