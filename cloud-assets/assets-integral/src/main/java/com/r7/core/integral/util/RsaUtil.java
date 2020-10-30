package com.r7.core.integral.util;

import cn.hutool.crypto.asymmetric.RSA;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author wt
 * @Description RSA加密、解密、验证服务
 */
public class RsaUtil {
    private static RSA rsa = new RSA();

    public static String formatString(String source) {
        if (source == null) {
            return null;
        }
        return source.replaceAll("\\r", "").replaceAll("\\n", "");
    }

    /**
     * 生成私钥
     *
     * @return 私钥
     */
    public static String getPrivateKeyBase64() {
        return rsa.getPrivateKeyBase64();
    }


    /**
     * 生成公钥
     *
     * @return 公钥
     */
    public static String getPublicKeyBase64() {
        return rsa.getPublicKeyBase64();
    }


    /**
     * 根据公钥加密
     *
     * @param data      要加密的数据
     * @param publicKey 公钥
     * @return 签名
     */
    public static String encryptByPublicKey(String data, String publicKey) {

        try {

            publicKey = formatString(publicKey);
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            String outStr = Base64.encodeBase64String(cipher.doFinal(data.getBytes("utf-8")));
            return outStr;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * 根据私钥解密
     *
     * @param sign       签名
     * @param privateKey 私钥
     * @return 解密后的数据
     */
    public static String decryptByPrivateKey(String sign, String privateKey) {
        try {
            privateKey = formatString(privateKey);
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(sign.getBytes("utf-8"));
            // base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(decoded));
            // RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            String outStr = new String(cipher.doFinal(inputByte));
            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 用私钥验证
     *
     * @param data       要验证的数据
     * @param sign       签名
     * @param privateKey 私钥
     * @return 验证结果
     */
    public static boolean verify(String data, String sign, String privateKey) {
        String str = decryptByPrivateKey(sign, privateKey);
        if (str.equals(data)) {
            return true;
        }
        return false;
    }


}
