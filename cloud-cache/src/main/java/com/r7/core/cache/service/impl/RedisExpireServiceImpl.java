package com.r7.core.cache.service.impl;

import com.r7.core.cache.service.RedisExpireService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author wt
 * @Description redis缓存有效期实现类
 */
@Service
public class RedisExpireServiceImpl implements RedisExpireService {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setExpire(String key, long time,TimeUnit type) {
        redisTemplate.expire(key,time, type);
    }
}
