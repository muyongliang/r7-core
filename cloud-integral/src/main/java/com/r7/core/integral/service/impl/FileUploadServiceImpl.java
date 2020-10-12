package com.r7.core.integral.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.MD5;
import com.r7.core.integral.dto.FileDataDTO;
import com.r7.core.integral.service.FileUploadService;
import io.minio.*;
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
            bucketName = "defaultbucket";
        }
        long MD5start = System.currentTimeMillis();
        String digestHex = MD5.create().digestHex(inputStream);
        long MD5end = System.currentTimeMillis();
        long MD5usedTime = MD5end - MD5start;
        log.info("MD5校验值为：{}", digestHex);
        log.info("MD5校验用时：{}毫秒", MD5usedTime);
        FileDataDTO fileDataDTO = new FileDataDTO();
        fileDataDTO.setBucketName(bucketName);
        fileDataDTO.setMD5(digestHex);
        try {
            // Get information of an object.
            ObjectStat objectStat =
                    minioClient.statObject(
                            StatObjectArgs.builder().bucket(bucketName).object(digestHex).build());
            fileDataDTO.setExist(true);
        } catch (Exception e) {
            log.info("查询对象是否存在时发生异常：{}", e.getMessage());
//            创建桶
            boolean isExist =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (isExist) {
                log.info("Bucket:{} already exists.", bucketName);
            } else {
                // Make a new bucket
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
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
            fileDataDTO.setUsedTime(usedTime);
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
