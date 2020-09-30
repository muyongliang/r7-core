package com.r7.core.integral;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.AES;
import com.google.common.net.MediaType;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;

/**
 * @Auther muyongliang
 * @Date 2020/9/28
 * @Description minIO测试类
 */
@Slf4j
public class MinIOTest {
    private MinioClient minioClient;

    /**
     * @Author muyongliang
     * @Date 2020/9/29 15:35
     * @Description 初始化客户端, client 的putObject处理流数据，uploadObject处理本地文件
     */
    @Before
    public void init() throws Exception {
        System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\liang\\Desktop\\minIO.keystore");
//        Map<String, String> getenv = System.getenv();
//        System.out.println();
//        System.setProperty("javax.net.ssl.trustStore", "minIO.keystore");
//        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("minIO.keystore");
//        byte[] bytes = new byte[1024];
//        resourceAsStream.read(bytes);
//        String s = new String(bytes);
//        log.info("读取的内容为：{}"+s);
        minioClient =
                MinioClient.builder()
                        .endpoint("https://192.168.1.49:9000")
                        .credentials("admin", "admin123456")
                        .build();
    }

    /**
     * @Author muyongliang
     * @Date 2020/9/29 15:35
     * @Description 上传本地文件，不进行服务端加密
     */
    @Test
    public void minIOTest1() throws Exception {

        boolean isExist =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket("mybucket").build());
        if (isExist) {
            System.out.println("Bucket already exists.");
        } else {
            // Make a new bucket called asiatrip to hold a zip file of photos.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("mybucket").build());
        }
        // Upload the zip file to the bucket with putObject
        minioClient.putObject("mybucket", "美女.jpg", "C:\\Users\\liang\\Pictures\\Camera Roll\\美女.jpg", null);
        System.out.println("文件上传成功");
        // Download object given the bucket, object name and output file name
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket("mybucket")
                        .object("美女.jpg")
                        .filename("C:\\Users\\liang\\Desktop\\美女原始下载.jpg")
                        .build());
    }

    /**
     * @Author muyongliang
     * @Date 2020/9/29 15:36
     * @Description 不知道长度的流，进行服务端加密
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
     * @Author muyongliang
     * @Date 2020/9/29 15:36
     * @Description 本地文件进行服务端加密
     */
    @Test
    public void minIOTest3() throws Exception {
        //生成256位AES key.
        KeyGenerator aes = KeyGenerator.getInstance("AES");
        aes.init(256);
        SecretKey aesKey = aes.generateKey();
        ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey = new ServerSideEncryptionCustomerKey(aesKey);
// Upload a video file.
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("mybucket")
                        .object("美女服务端加密")
                        .filename("C:\\Users\\liang\\Pictures\\Camera Roll\\美女.jpg")
                        .sse(serverSideEncryptionCustomerKey)
                        .contentType(MediaType.ANY_IMAGE_TYPE.toString())
                        .build());
        System.out.println("myobject is uploaded successfully");
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket("mybucket")
                        .object("美女服务端加密")
                        .ssec(serverSideEncryptionCustomerKey)
                        .filename("C:\\Users\\liang\\Desktop\\美女服务端加密下载.jpg")
                        .build());
    }

    /**
     * @Author muyongliang
     * @Date 2020/9/29 15:35
     * @Description 上传本地文件，不进行服务端加密,本地进行加密
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
}
