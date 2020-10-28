package com.r7.core.integral.util;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.r7.core.integral.constant.Md5Enum;
import org.apache.commons.codec.binary.Hex;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

/**
 * @author wt
 * @Description 用MD5加盐生成签名及验证
 */
public class Md5Util {

    /**
     * 生成普通的MD5
     * @param input 传入字符串
     * @return 普通的MD5
     */
    public static String md5(String input) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return "check jdk";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = input.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++){
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16){
                hexValue.append("0");}
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 加盐后生成MD5
     * @param str 明文即生成签名需要的字符串
     * @return 加盐后生成的MD5
     */
    public static String generate(String str) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < Md5Enum.CONSTANT16) {
            for (int i = 0; i < Md5Enum.CONSTANT16 - len; i++) {
                sb.append("0");
            }
        }

        String salt = sb.toString();
        str = md5Hex(str + salt);
        char[] cs = new char[48];
        for (int i = 0; i < Md5Enum.CONSTANT48; i += Md5Enum.CONSTANT3) {
            cs[i] = str.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = str.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 验证明文和加盐后的MD5是否一样
     * @param str 明文即生成签名需要的字符串
     * @param md5 加盐后生成的MD5
     * @return 验证结果
     */
    public static boolean verify(String str, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < Md5Enum.CONSTANT48; i += Md5Enum.CONSTANT3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(str + salt).equals(new String(cs1));
    }
}
