package com.r7.core.integral.controller;

import cn.hutool.core.codec.Base64;
import com.r7.core.integral.service.FileUploadService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.ServerSideEncryptionCustomerKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther muyongliang
 * @Date 2020/10/9
 * @Description FileUploadController
 */
@Slf4j
@RequestMapping("/upload")
@RestController
public class FileUploadController {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping
    public void upload(HttpServletRequest request) throws Exception {
        ServletInputStream inputStream = request.getInputStream();
        try {
            long start = System.currentTimeMillis();
            //生成256位AES key.
            KeyGenerator aes = KeyGenerator.getInstance("AES");
            aes.init(256);
            SecretKey aesKey = aes.generateKey();
            String encode = Base64.encode(aesKey.getEncoded());
            log.info("生成的aesKey为：{}", encode);
            ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey = new ServerSideEncryptionCustomerKey(aesKey);
// Upload input stream with server-side encryption.
            minioClient.putObject(
                    PutObjectArgs.builder().bucket("mybucket").object("美女").stream(
                            inputStream, -1, 10 * 1024 * 1024)
                            .sse(serverSideEncryptionCustomerKey)
                            .build());
            long end = System.currentTimeMillis();
            long usedTime = end - start;
            log.info("上传文件用时：{}毫秒", usedTime);
        } finally {
            inputStream.close();
        }


    }

}
