package com.yym.setting.rocketmq.consumer;

import cn.hutool.json.JSONUtil;
import com.yym.setting.common.dto.PhoneDataDTO;
import com.yym.setting.common.enums.MQEnum;
import com.yym.setting.common.util.MQResult;
import com.yym.setting.common.util.MQResultUtil;
import com.yym.setting.service.CoreSettingService;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jinghan
 * @title: Touch2phoneDataTopicConsumer
 * @projectName mobilepay
 * @description: mq 消费组
 * RocketMQ在设计时就不希望一个消费者同时处理多个类型的消息，
 * 因此同一个consumerGroup下的consumer职责应该是一样的，
 * 不要干不同的事情（即消费多个topic）。建议consumerGroup与topic一一对应。
 * @date 2020/7/14 16:55
 */
@Service
//@RocketMQMessageListener(consumerGroup = "service-phone-data-group", topic = "touch2phoneData-topic")
public class Touch2phoneDataTopicConsumer implements RocketMQListener<String> {

    private static final Logger logger = LoggerFactory.getLogger(Touch2phoneDataTopicConsumer.class);

    @Autowired
    private CoreSettingService cloudSettingService;

    @Override
    public void onMessage(String message) {
        try {
            MQResult mqResult = MQResultUtil.strToResult(message);
//            判断 此消息 是否有时间 限制
            if (isTimeOut(message, mqResult)) return;

            //判断消息类型
            if (MQEnum.PHONE_DATA_ADD.toString().equals(mqResult.getMqType())) {
                PhoneDataDTO dataDTO = JSONUtil.toBean(JSONUtil.parseObj(mqResult.getResult()), PhoneDataDTO.class);
//                cloudSettingService.insert(dataDTO);
            }
        } catch (Exception e) {
            logger.error("号码服务消费者异常", e);
        }
    }

    private boolean isTimeOut(String message, MQResult mqResult) {
        if (mqResult.isTimeLimit()) {
            Long timeOutMillis = mqResult.getTimeOutMillis();
            if (System.currentTimeMillis() > timeOutMillis) {
                //超时不处理
                logger.info("号码服务消费者 超时不处理 {}", message);
                return true;
            }
        }
        return false;
    }
}
