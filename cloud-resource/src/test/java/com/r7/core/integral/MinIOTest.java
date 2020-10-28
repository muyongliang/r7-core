package com.r7.core.integral;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.AES;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

/**
 * @author muyongliang
 * @date 2020/9/28
 * @description minIO测试类
 */
@Slf4j
public class MinIOTest {
    private MinioClient minioClient;

    /**
     * @author muyongliang
     * @date 2020/9/29 15:35
     * @description 初始化客户端, client 的putObject处理流数据，uploadObject处理本地文件
     */
    @Before
    public void init() throws Exception {
        String file = Thread.currentThread().getContextClassLoader().getResource("minIO.keystore").getFile();
        System.setProperty("javax.net.ssl.trustStore", file);
        minioClient =
                MinioClient.builder()
                        .endpoint("https://192.168.1.49:9000")
                        .credentials("admin", "admin123456")
                        .build();
        boolean isExist =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket("mybucket").build());
        if (isExist) {
            System.out.println("Bucket already exists.");
        } else {
            // Make a new bucket called asiatrip to hold a zip file of photos.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("mybucket").build());
        }
    }


    /**
     * @author muyongliang
     * @date 2020/9/29 15:35
     * @description 上传本地文件，不进行服务端加密
     */
    @Test
    public void minIOTest1() throws Exception {

        // Upload the zip file to the bucket with putObject
        minioClient.putObject("mybucket", "美女.jpg", "C:\\Users\\liang\\Pictures\\Camera Roll\\美女.jpg", null);
//        存在覆盖问题
//        minioClient.putObject("mybucket", "美女.jpg", "C:\\Users\\liang\\Pictures\\Camera Roll\\美女1.jpg", null);
        System.out.println("文件上传成功");

// Get information of an object.
        ObjectStat objectStat =
                minioClient.statObject(
                        StatObjectArgs.builder().bucket("mybucket").object("美女.jpg").build());
        // Download object given the bucket, object name and output file name
        File file = new File("C:\\Users\\liang\\Desktop\\美女原始下载.jpg");
        if (file.exists()) {
            file.delete();
        }
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket("mybucket")
                        .object("美女.jpg")
                        .filename("C:\\Users\\liang\\Desktop\\美女原始下载.jpg")
                        .build());
        String url = minioClient.getObjectUrl("mybucket", "美女.jpg");
        System.out.println("can be downloaded by " + url);

    }

    /**
     * @author muyongliang
     * @date 2020/9/29 15:36
     * @description 不知道长度的流，进行服务端加密
     */
    @Test
    public void minIOTest2() {
        try {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                builder.append("a");
                builder.append(i);
                builder.append("b");
                builder.append("---\n");
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));
            //生成256位AES key.
            KeyGenerator aes = KeyGenerator.getInstance("AES");
            aes.init(256);
            SecretKey aesKey = aes.generateKey();
            ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey = new ServerSideEncryptionCustomerKey(aesKey);
// Upload input stream with server-side encryption.
            minioClient.putObject(
                    PutObjectArgs.builder().bucket("mybucket").object("string1").stream(
                            bais, -1, 10 * 1024 * 1024)
                            .sse(serverSideEncryptionCustomerKey)
                            .build());
            bais.close();
            System.out.println("myobject is uploaded successfully");
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }

    /**
     * @author muyongliang
     * @date 2020/9/29 15:36
     * @description 本地文件进行服务端加密
     * md5校验会修改流
     */
    @Test
    public void minIOTest3() throws Exception {
        //生成256位AES key.
        KeyGenerator aes = KeyGenerator.getInstance("AES");
        aes.init(256);
        SecretKey aesKey = aes.generateKey();
        ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey = new ServerSideEncryptionCustomerKey(aesKey);
// Upload a video file.
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\liang\\Pictures\\Camera Roll\\美女.jpg");
//        String digestHex = MD5.create().digestHex(fileInputStream);
        String digestHex = "digestHex";
// Upload input stream with server-side encryption.
        minioClient.putObject(
                PutObjectArgs.builder().bucket("mybucket").object(digestHex).stream(
                        fileInputStream, -1, 10 * 1024 * 1024)
                        .sse(serverSideEncryptionCustomerKey)
                        .build());
        fileInputStream.close();
        System.out.println("myobject is uploaded successfully");
        File file = new File("C:\\Users\\liang\\Desktop\\美女服务端加密下载.jpg");
        if (file.exists()) {
            file.delete();
        }
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket("mybucket")
                        .object(digestHex)
                        .ssec(serverSideEncryptionCustomerKey)
                        .filename("C:\\Users\\liang\\Desktop\\美女服务端加密下载.jpg")
                        .build());
    }

    /**
     * @author muyongliang
     * @date 2020/9/29 15:35
     * @description 上传本地文件，不进行服务端加密,本地进行加密
     */
    @Test
    public void minIOTest4() throws Exception {

        boolean isExist =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket("mybucket").build());
        if (isExist) {
            System.out.println("Bucket already exists.");
        } else {
            // Make a new bucket called asiatrip to hold a zip file of photos.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("mybucket").build());
        }
        AES aes = new AES();
        String s = Base64.encode(aes.getSecretKey().getEncoded());
        log.info("生成的aes秘钥位：{}", s);
        byte[] encrypt = aes.encrypt(new FileInputStream("C:\\Users\\liang\\Pictures\\Camera Roll\\美女.jpg"));
        ByteArrayInputStream is = new ByteArrayInputStream(encrypt);
        minioClient.putObject(
                PutObjectArgs.builder().bucket("mybucket").object("美女本地加密").stream(
                        is, -1, 10 * 1024 * 1024)
                        .build());
        is.close();
        System.out.println("文件上传成功");
        // get object given the bucket and object name
        InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("mybucket")
                        .object("美女本地加密")
                        .build());
        byte[] decrypt = aes.decrypt(inputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\liang\\Desktop\\美女本地加密下载.jpg"));
        fileOutputStream.write(decrypt);
        inputStream.close();
        fileOutputStream.close();
    }

    /**
     * @author muyongliang
     * @date 2020/9/29 15:35
     * @description 利用保存的aeskey下载大文件测试
     */
    @Test
    public void minIOTest5() throws Exception {
        long start = System.currentTimeMillis();
        String encoded = "I9mArhjdOG2tfKnHl5woB8YdikF8UGpyBxkKasIB6Us=";
        SecretKeySpec aes = new SecretKeySpec(Base64.decode(encoded), "AES");
        ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey = new ServerSideEncryptionCustomerKey(aes);
        // get object given the bucket and object name
        InputStream inputStream1 = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("mybucket")
                        .object("美女")
                        .ssec(serverSideEncryptionCustomerKey)
                        .build());
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket("mybucket")
                        .object("美女")
                        .ssec(serverSideEncryptionCustomerKey)
                        .filename("C:\\Users\\liang\\Desktop\\美女服务端加密下载.flv")
                        .build());
        long end = System.currentTimeMillis();
        long usedTime = end - start;
        log.info("用时：{}毫秒", usedTime);
    }

    /**
     * @author muyongliang
     * @date 2020/9/29 15:35
     * @description minIO大文件上传测试
     */
    @Test
    public void minIOTest6() throws Exception {
        long start = System.currentTimeMillis();
// Upload a video file.
        ObjectWriteResponse objectWriteResponse = minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("mybucket")
                        .object("视频.flv")
                        .filename("C:\\Users\\liang\\Videos\\93980974-1-80.flv")
                        .build());
        long end = System.currentTimeMillis();
        long usedTime = end - start;
        log.info("用时：{}毫秒", usedTime);
    }

    /**
     * @author muyongliang
     * @date 2020/9/29 15:35
     * @description md5测试, md5会修改流
     */
    @Test
    public void MD5Test1() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\liang\\Pictures\\Camera Roll\\美女.jpg");
        log.info(Integer.toString(fileInputStream.available()));
        String digestHex1 = MD5.create().digestHex(fileInputStream);
        log.info(digestHex1);
        log.info(Integer.toString(fileInputStream.available()));
        String digestHex2 = MD5.create().digestHex(fileInputStream);
        log.info(digestHex2);
        log.info(Integer.toString(fileInputStream.available()));
    }

    /**
     * @author muyongliang
     * @date 2020/9/29 15:35
     * @description 摘要算法会修改流
     */
    @Test
    public void SHA256Test1() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\liang\\Pictures\\Camera Roll\\美女.jpg");
        log.info(Integer.toString(fileInputStream.available()));
        String sha256Hex1 = DigestUtil.sha256Hex(fileInputStream);
        log.info(sha256Hex1);
        log.info(Integer.toString(fileInputStream.available()));
        String sha256Hex2 = DigestUtil.sha256Hex(fileInputStream);
        log.info(sha256Hex2);
        log.info(Integer.toString(fileInputStream.available()));
    }

    /**
     * @author muyongliang
     * @date 2020/9/29 15:35
     * @description md5测试, md5不会修改文件
     */
    @Test
    public void MD5Test12() throws Exception {
        File file = new File("C:\\Users\\liang\\Pictures\\Camera Roll\\美女.jpg");
        log.info(Long.toString(file.length()));
        String digestHex1 = MD5.create().digestHex(file);
        log.info(digestHex1);
        log.info(Long.toString(file.length()));
        String digestHex2 = MD5.create().digestHex(file);
        log.info(digestHex2);
        log.info(Long.toString(file.length()));
    }

}
