package com.r7.core.pay.properties;

import lombok.Data;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author muyongliang
 * @date 2020/10/16
 * @description MinIOProperties
 */
@Component
@PropertySource("classpath:/alipay.properties")
@Data
public class AliPayProperties {
    /**
     * 应用id
     */
    private String appId;
    /**
     * 应用私钥
     */
    private String privateKey;
    /**
     * 应用公钥
     */
    private String publicKey;
    /**
     * 应用证书
     */
    private String appCertPath;
    /**
     * 阿里证书
     */
    private String aliPayCertPath;
    /**
     * 阿里根证书
     */
    private String aliPayRootCertPath;
    /**
     * 阿里支付宝网关
     */
    private String serverUrl;
    /**
     * 字符集
     */
    private String charset = "utf-8";
    /**
     * 设置请求格式，固定值json
     */
    private String format = "json";
    /**
     * 设置签名类型
     */
    private String signtype = "RSA2";
    /**
     *
     */
    private String domain;
}
