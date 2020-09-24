package com.yym.setting.rocketmq.producer;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jinghan
 * @title: TouchProducer
 * @projectName mobilepay
 * @description: 生产者
 * @date 2019/9/11 16:50
 */
@Component
public class ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);


    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 生产消息
     *
     * @param topic
     */
    public boolean send(String topic, String msg) {
        rocketMQTemplate.convertAndSend(topic, msg);
        return true;
    }
}
