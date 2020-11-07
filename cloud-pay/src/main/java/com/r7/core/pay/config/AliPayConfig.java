package com.r7.core.pay.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.r7.core.pay.properties.AliPayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author muyongliang
 * @date 2020/11/6
 * @description AliPayConfig 配置阿里支付客户端,统一使用证书模式
 */
@Configuration
@Slf4j
public class AliPayConfig {
    @Resource
    private AliPayProperties aliPayProperties;

    @Bean
    public AlipayClient alipayClient() throws AlipayApiException {
        //  创建AlipayClient实例,使用证书模式
        AlipayClient alipayClient = new DefaultAlipayClient(getClientParams());
        return alipayClient;
    }

    private CertAlipayRequest getClientParams() {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        //设置网关地址
        certAlipayRequest.setServerUrl(aliPayProperties.getServerUrl());
        //请更换为您的AppId
        certAlipayRequest.setAppId(aliPayProperties.getAppId());
        //请更换为您的PKCS8格式的应用私钥
        certAlipayRequest.setPrivateKey(aliPayProperties.getPrivateKey());
        //请更换为您使用的字符集编码，推荐采用utf-8
        certAlipayRequest.setCharset(aliPayProperties.getCharset());
        //设置请求格式，固定值json
        certAlipayRequest.setFormat(aliPayProperties.getFormat());
        //设置签名类型
        certAlipayRequest.setSignType(aliPayProperties.getSigntype());
        //请更换为您的应用公钥证书文件路径
        certAlipayRequest.setCertPath(aliPayProperties.getAppCertPath());
        //请更换您的支付宝公钥证书文件路径
        certAlipayRequest.setAlipayPublicCertPath(aliPayProperties.getAliPayCertPath());
        //更换为支付宝根证书文件路径
        certAlipayRequest.setRootCertPath(aliPayProperties.getAliPayRootCertPath());
        return certAlipayRequest;
    }
}
