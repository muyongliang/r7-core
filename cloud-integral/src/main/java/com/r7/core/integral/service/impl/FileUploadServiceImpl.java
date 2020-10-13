package com.r7.core.integral.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.integral.dto.CoreFileDTO;
import com.r7.core.integral.mapper.CoreFileMapper;
import com.r7.core.integral.model.CoreFileDO;
import com.r7.core.integral.service.FileUploadService;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

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
    @Autowired
    private CoreFileMapper coreFileMapper;

    /**
     * @Author muyongliang
     * @Date 2020/10/12 16:22
     * @Description 使用md5值作为对象名，用于去重，和防止文件覆盖
     */
    @Override
    public CoreFileDTO uploadStream(InputStream inputStream, String bucketName, boolean encrypted, String aesKey, String originalFileName) throws Exception {
        CoreFileDTO coreFileDTO = new CoreFileDTO();
        CoreFileDO coreFileDO = new CoreFileDO();
        if (StringUtils.isBlank(bucketName)) {
            bucketName = "defaultbucket";
        }
        if (encrypted && StringUtils.isBlank(aesKey)) {
            //生成256位AES key.
            KeyGenerator aes = KeyGenerator.getInstance("AES");
            aes.init(256);
            aesKey = Base64.encode(aes.generateKey().getEncoded());
            log.info("生成的aesKey为：{}", aesKey);
        }
        SecretKeySpec aes = new SecretKeySpec(Base64.decode(aesKey), "AES");
        ServerSideEncryptionCustomerKey ssc = new ServerSideEncryptionCustomerKey(aes);

//        转换成数组，流被使用一次后会清空
        byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
//        md5校验和
        long MD5start = System.currentTimeMillis();
        String digestHex = MD5.create().digestHex(bytes);
        long MD5end = System.currentTimeMillis();
        long MD5usedTime = MD5end - MD5start;
        log.info("MD5校验值为：{}", digestHex);
        log.info("MD5校验用时：{}毫秒", MD5usedTime);

        QueryWrapper<CoreFileDO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("fileName", digestHex);
        Integer count = coreFileMapper.selectCount(queryWrapper);
        if (count > 0) {
            log.info("文件{}已经上传过", digestHex);
            coreFileDTO.setExist(true);
            return coreFileDTO;
        }
//            创建桶
        boolean isExist =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (isExist) {
            log.info("Bucket:{} already exists.", bucketName);
        } else {
            // Make a new bucket
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        ByteArrayInputStream byteArrayInputStream;
        byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            long start = System.currentTimeMillis();
// Upload input stream with server-side encryption.
            if (encrypted) {
                minioClient.putObject(
                        PutObjectArgs.builder().bucket(bucketName).object(digestHex).stream(
                                byteArrayInputStream, -1, 10 * 1024 * 1024)
                                .sse(ssc)
                                .build());
            } else {
                minioClient.putObject(
                        PutObjectArgs.builder().bucket(bucketName).object(digestHex).stream(byteArrayInputStream
                                , -1, 10 * 1024 * 1024)
                                .build());
            }
            long end = System.currentTimeMillis();
            long usedTime = end - start;
            log.info("上传文件用时：{}毫秒", usedTime);
            coreFileDO.setAesKey(aesKey);
            coreFileDO.setBucketName(bucketName);
            coreFileDO.setFileName(digestHex);
            coreFileDO.setCreatedAt(new Date());
            coreFileDO.setId(SnowflakeUtil.getSnowflakeId());
            coreFileDO.setStatus(1);
            coreFileDO.setOriginalFileName(originalFileName);
            coreFileMapper.insert(coreFileDO);
        } finally {
            byteArrayInputStream.close();
        }
        coreFileDTO.setFileName(digestHex);
        return coreFileDTO;
    }

    /**
     * @Author muyongliang
     * @Date 2020/10/12 17:44
     * @Description 文件下载业务
     */

    @Override
    public InputStream download(String fileName) throws Exception {
        QueryWrapper<CoreFileDO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("fileName", fileName);
        CoreFileDO coreFileDO = coreFileMapper.selectOne(queryWrapper);
        String bucketName = coreFileDO.getBucketName();
        if (StringUtils.isBlank(bucketName)) {
            bucketName = "defaultBucket";
        }
        InputStream inputStream;
        long start = System.currentTimeMillis();


        // get object given the bucket and object name
        String aesKey = coreFileDO.getAesKey();
        if (StringUtils.isBlank(aesKey)) {
            inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(coreFileDO.getFileName())
                            .build());
        } else {
            SecretKeySpec aes = new SecretKeySpec(Base64.decode(aesKey), "AES");
            ServerSideEncryptionCustomerKey serverSideEncryptionCustomerKey = new ServerSideEncryptionCustomerKey(aes);
            inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(coreFileDO.getFileName())
                            .ssec(serverSideEncryptionCustomerKey)
                            .build());
        }
        long end = System.currentTimeMillis();
        long usedTime = end - start;
        log.info("用时：{}毫秒", usedTime);
        return inputStream;
    }
}
