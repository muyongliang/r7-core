package com.r7.core.integral.controller;

import com.r7.core.integral.service.FileUploadService;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

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
            //生成256位AES key.
            KeyGenerator aes = KeyGenerator.getInstance("AES");
            aes.init(256);
            SecretKey aesKey = aes.generateKey();
            ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey = new ServerSideEncryptionCustomerKey(aesKey);
// Upload input stream with server-side encryption.
            minioClient.putObject(
                    PutObjectArgs.builder().bucket("mybucket").object("美女.jpg").stream(
                            inputStream, -1, 10 * 1024 * 1024)
                            .sse(serverSideEncryptionCustomerKey)
                            .build());
            // get object given the bucket and object name
            InputStream inputStream1 = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket("mybucket")
                            .object("美女.jpg")
                            .ssec(serverSideEncryptionCustomerKey)
                            .build());
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket("mybucket")
                            .object("美女.jpg")
                            .ssec(serverSideEncryptionCustomerKey)
                            .filename("C:\\Users\\liang\\Desktop\\美女服务端加密下载.jpg")
                            .build());
        } finally {
            inputStream.close();
        }


    }

}
