package com.r7.core.sms.service.impl;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r7.core.sms.config.SmsConfig;
import com.r7.core.sms.service.SmsService;
import com.r7.core.common.exception.BusinessException;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * 短信服务层
 *
 * @author zhongpingli
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    private SmsConfig smsConfig;

    @Resource
    private ObjectMapper objectMapper;

    private IAcsClient defaultAcsClient;

    @PostConstruct
    public void init() {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsConfig.getAccessKeyId(),
                smsConfig.getAccessKeySecret());
        defaultAcsClient = new DefaultAcsClient(profile);
    }

    @Override
    public void sendSms(String phoneNumber, String signName, String templateCode, String templateParam) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        CommonResponse response = Try.of(() -> defaultAcsClient.getCommonResponse(request))
                .getOrElseThrow(() -> new BusinessException("", ""));
        String data = response.getData();
        Map map = JSONUtil.toBean(data, Map.class);
        String code = map.get("Code").toString();
        if (!"OK".equals(code)) {
            throw new BusinessException(code, map.get("Message").toString());
        }
    }
}
