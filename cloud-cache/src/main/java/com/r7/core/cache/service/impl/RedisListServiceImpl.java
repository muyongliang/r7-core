package com.r7.core.cache.service.impl;

import com.r7.core.cache.constant.PushType;
import com.r7.core.cache.service.RedisListService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static io.vavr.API.*;

/**
 * redis list结构crud服务实现层
 *
 * @author zhongpingli
 */
@Service
public class RedisListServiceImpl implements RedisListService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public <T> void addListValue(String key, List<T> listValue, PushType type) {


        Match(type).of(
                Case($(PushType.RIGHT),
                        o -> run(() ->
                                redisTemplate.opsForList().rightPushAll(key, listValue))),
                Case($(PushType.LEFT),
                        o -> run(() ->
                                redisTemplate.opsForList().leftPushAll(key, listValue))),
                Case($(), new RuntimeException("type不能为null"))
        );
    }

    @Override
    public void addValue(String key, Object value, PushType type) {
        Match(type).of(
                Case($(PushType.RIGHT), redisTemplate.opsForList().rightPush(key, value)),
                Case($(PushType.LEFT), redisTemplate.opsForList().leftPush(key, value)),
                Case($(), new RuntimeException("type不能为null"))
        );
    }

    @Override
    public <T> List<T> getKey(String key, Class<T> t) {
        return (List<T>) redisTemplate.opsForList().range(key, 0, -1);
    }

    @Override
    public <T> T updateValueByKey(String key, T newValue, Integer index, Class<T> t) {

        redisTemplate.opsForList().set(key, index, newValue);
        return (T) redisTemplate.opsForList().index(key, index);
    }

    @Override
    public void removeByKey(String key) {
        redisTemplate.delete(key);
    }
}
