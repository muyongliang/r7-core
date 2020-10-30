package com.r7.core.sms.service;

/**
 * 短信服务
 *
 * @author zhongpingli
 */
public interface SmsService {


    /**
     * 发送短信
     *
     * @param phoneNumber   接手的手机号
     * @param templateCode  短信模板CODE
     * @param templateParam 变量替换JSON串
     */
    void sendSms(String phoneNumber, String templateCode, String templateParam);

}
