package com.r7.core.integral.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.MD5;
import com.r7.core.integral.dto.FileDataDTO;
import com.r7.core.integral.service.FileUploadService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.ServerSideEncryptionCustomerKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;

/**
 * @Auther muyongliang
 * @Date 2020/10/9
 * @Description FileUploadServiceImpl
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private MinioClient minioClient;

    @Override
    public FileDataDTO upload(InputStream inputStream, String bucketName) throws Exception {
        if (StringUtils.isBlank(bucketName)) {
            bucketName = "defaultBucket";
        }
        String digestHex = MD5.create().digestHex(inputStream);
        FileDataDTO fileDataDTO = new FileDataDTO();
        fileDataDTO.setBucketName(bucketName);
        fileDataDTO.setMD5(digestHex);
        try {
            long start = System.currentTimeMillis();
            //生成256位AES key.
            KeyGenerator aes = KeyGenerator.getInstance("AES");
            aes.init(256);
            SecretKey aesKey = aes.generateKey();
            String encode = Base64.encode(aesKey.getEncoded());
            fileDataDTO.setAesKey(encode);
            log.info("生成的aesKey为：{}", encode);
            ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey = new ServerSideEncryptionCustomerKey(aesKey);
// Upload input stream with server-side encryption.
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(digestHex).stream(
                            inputStream, -1, 10 * 1024 * 1024)
                            .sse(serverSideEncryptionCustomerKey)
                            .build());
            long end = System.currentTimeMillis();
            long usedTime = end - start;
            log.info("上传文件用时：{}毫秒", usedTime);
        } finally {
            inputStream.close();
        }
        return fileDataDTO;
    }

    @Override
    public InputStream download(FileDataDTO fileDataDTO) throws Exception {
        String bucketName = fileDataDTO.getBucketName();
        if (StringUtils.isBlank(bucketName)) {
            bucketName = "defaultBucket";
        }
        long start = System.currentTimeMillis();
        SecretKeySpec aes = new SecretKeySpec(Base64.decode(fileDataDTO.getAesKey()), "AES");
        ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey = new ServerSideEncryptionCustomerKey(aes);
        // get object given the bucket and object name
        InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileDataDTO.getMD5())
                        .ssec(serverSideEncryptionCustomerKey)
                        .build());
        long end = System.currentTimeMillis();
        long usedTime = end - start;
        log.info("用时：{}毫秒", usedTime);
        return inputStream;
    }
}