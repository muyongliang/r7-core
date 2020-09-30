package com.r7.core.integral;

import com.google.common.net.MediaType;
import io.minio.*;
import io.minio.errors.MinioException;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther muyongliang
 * @Date 2020/9/28
 * @Description minIO测试类
 */
public class MinIOTest {
    private MinioClient minioClient;

    /**
     * @Author muyongliang
     * @Date 2020/9/29 15:35
     * @Description 初始化客户端
     */
    @Before
    public void init() {
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
    public void minIOTest1() throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        try {
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
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
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
                builder.append("第");
                builder.append(i);
                builder.append("行");
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
                    PutObjectArgs.builder().bucket("mybucket").object("my-objectname").stream(
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
    public void minIOTest3() {
        try {
            //生成256位AES key.
            KeyGenerator aes = KeyGenerator.getInstance("AES");
            aes.init(256);
            SecretKey aesKey = aes.generateKey();
            ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey = new ServerSideEncryptionCustomerKey(aesKey);
// Upload a video file.
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("my-bucketname")
                            .object("my-pic")
                            .filename("C:\\Users\\liang\\Pictures\\Camera Roll\\美女.jpg")
                            .contentType(MediaType.ANY_IMAGE_TYPE.toString())
                            .build());
            System.out.println("myobject is uploaded successfully");
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }
}
