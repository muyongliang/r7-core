package com.r7.core.cache.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r7.core.cache.service.RedisExpireService;
import com.r7.core.cache.service.RedisHashService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis Hash crud 实现层
 *
 * @author zhongpingli
 */
@Service
public class RedisHashServiceImpl implements RedisHashService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedisExpireService redisExpireService;



    @Override
    public void addHashAll(String key, Map<String, Object> value) {

        redisTemplate.opsForHash().putAll(key, value);

    }

    @Override
    public void addHashValue(String redisKey, String key, Object value) {
        redisTemplate.opsForHash().put(redisKey,key,value);
    }

    @Override
    public void addHashValue(String redisKey, String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(redisKey,key,value);
        redisExpireService.setExpire(redisKey,time,timeUnit);
    }

    @Override
    public Object getByKeyAndRedisKey(String redisKey, String key) {

      Object value =  redisTemplate.opsForHash().get(redisKey,key);
        if (value == null) {
            return  null;
        }

      return value;

    }

    @Override
    public Object updateValueByKeyAndRedisKey(String redisKey, String key, Object value) {

        Object obj = this.getByKeyAndRedisKey(redisKey,key);
        this.removeByKeyAndRedisKey(redisKey,key);
        redisTemplate.opsForHash().put(redisKey,key,value);

        return getByKeyAndRedisKey(redisKey,key);
    }

    @Override
    public void removeByKeyAndRedisKey(String redisKey, String key) {
            redisTemplate.opsForHash().delete(redisKey,key);
    }

    @Override
    public void removeAll(String redisKey) {
            redisTemplate.delete(redisKey);
    }
}
