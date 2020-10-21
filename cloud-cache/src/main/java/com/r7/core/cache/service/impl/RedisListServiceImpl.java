package com.r7.core.cache.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.r7.core.cache.constant.PushType;
import com.r7.core.cache.service.RedisListService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

        Object[] objects = listValue.stream().map(JSONUtil::toJsonStr).toArray();

        Match(type).of(
                Case($(PushType.RIGHT),
                        o -> run(() ->
                                redisTemplate.opsForList().rightPushAll(key, objects))),
                Case($(PushType.LEFT),
                        o -> run(() ->
                                redisTemplate.opsForList().leftPushAll(key, objects))),
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
        List<Object> values = redisTemplate.opsForList().range(key, 0, -1);
        if (values == null || values.size() == 0) {
            return null;
        }
        return values.stream().map(x -> JSONUtil.toBean(x.toString(), t)).collect(Collectors.toList());
    }

    @Override
    public <T> T updateValueByKey(String key, T newValue, Integer index, Class<T> t) {

        redisTemplate.opsForList().set(key, index, newValue);
        Object value = redisTemplate.opsForList().index(key, index);
        if (ObjectUtil.isNotEmpty(value)) {
            return JSONUtil.toBean(value.toString(), t);

        }
        return null;
    }

    @Override
    public void removeByKey(String key) {
        redisTemplate.delete(key);
    }
}
