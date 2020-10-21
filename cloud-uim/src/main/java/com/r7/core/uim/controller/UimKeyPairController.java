package com.r7.core.uim.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * rsa 公钥提供接口
 *
 * @author zhongpingli
 */
@Api(value = "/api/rsa", tags = {"RSA公钥提供接口"})
@RestController
@RequestMapping("/rsa")
public class UimKeyPairController {

    @Resource
    private KeyPair keyPair;

    @ApiOperation(value = "获取rsa公钥", response = Map.class)
    @GetMapping("/public/key")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}
