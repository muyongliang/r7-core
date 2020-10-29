package com.r7.core.cache.service.impl;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r7.core.cache.service.RedisExpireService;
import com.r7.core.cache.service.RedisSetService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redis set crud 服务实现层
 *
 * @author zhongpingli
 */
@Service
public class RedisSetServiceImpl implements RedisSetService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedisExpireService redisExpireService;


    @Override
    public void addValue(String key, Object value) {
            redisTemplate.opsForSet().add(key,value);
    }


    @Override
    public void addValue(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForSet().add(key,value);
        redisExpireService.setExpire(key, time, timeUnit);
    }

    @Override
    public <T> void addAll(String key, Set<T> set) {

        Object[] objects = set.stream().map(JSONUtil::toJsonStr).toArray();
        redisTemplate.opsForSet().add(key,objects);

    }

    @Override
    public void removeValueByKey(String key, Object value) {
        redisTemplate.opsForSet().remove(key,value);
    }

    @Override
    public void removeAllByKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public  Set<Object> getAll(String key) {

        Set<Object> set =  redisTemplate.opsForSet().members(key);
        if (set == null || set.size() == 0) {
            return  null;
        }
        return set;
    }
}
