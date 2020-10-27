package com.r7.core.sms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 短信配置
 *
 * @author zhongpingli
 */
@Component
public class SmsConfig {


    @Value("${sms.config.access.key.id}")
    private String accessKeyId;

    @Value("${sms.config.access.key.secret}")
    private String accessKeySecret;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

}
