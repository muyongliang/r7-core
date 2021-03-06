package com.r7.core.resource.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.resource.constant.BucketNameEnum;
import com.r7.core.resource.constant.FileErrorEnum;
import com.r7.core.resource.mapper.CoreFileMapper;
import com.r7.core.resource.model.CoreFileDO;
import com.r7.core.resource.service.FileUploadService;
import com.r7.core.resource.vo.FileUploadVO;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * @author muyongliang
 * @date 2020/10/9
 * @description FileUploadServiceImpl
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Resource
    private MinioClient minioClient;
    @Resource
    private CoreFileMapper coreFileMapper;

    /**
     * @author muyongliang
     * @date 2020/10/12 16:22
     * @description 使用md5值作为对象名，用于去重，和防止文件覆盖
     * 优化方案：对小文件去重，大文件不去重，因为大文件太占内存，做md5需要复制流,对象名直接使用originalFileName
     */
    @Override
    public FileUploadVO uploadStream(InputStream inputStream, boolean encrypted, String aesKey, String originalFileName) throws Exception {
        FileUploadVO fileUploadVO = new FileUploadVO();
        CoreFileDO coreFileDO = new CoreFileDO();
        String bucketName;
        String objectName;
        int available = inputStream.available();
//            0-1mb为small
        if (available < 1024 * 1024) {
            bucketName = BucketNameEnum.SMALL.name().toLowerCase();
//            1-10mb为middle
        } else if (available < 10 * 1024 * 1024) {
            bucketName = BucketNameEnum.MIDDLE.name().toLowerCase();
//            10mb-1gb为large
        } else if (available < 1024 * 1024 * 1024) {
            bucketName = BucketNameEnum.LARGE.name().toLowerCase();
//            超过1gb为huge
        } else {
            bucketName = BucketNameEnum.HUGE.name().toLowerCase();
        }
        log.info("上传文件大小为：{}，匹配桶名为：{}", available, bucketName);


        if ("small".equals(bucketName)) {
//        转换成数组，流被使用一次后会清空,文件太大会导致java.lang.OutOfMemoryError: Java heap space
            byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
            inputStream = new ByteArrayInputStream(bytes);
//        md5校验和
            long MD5start = System.currentTimeMillis();
            String digestHex = MD5.create().digestHex(bytes);
            long MD5end = System.currentTimeMillis();
            long MD5usedTime = MD5end - MD5start;
            log.info("MD5校验值为：{}", digestHex);
            log.info("MD5校验用时：{}毫秒", MD5usedTime);
            objectName = digestHex;
        } else {
            objectName = originalFileName;
        }
        QueryWrapper<CoreFileDO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("file_name", objectName);
        Integer count = coreFileMapper.selectCount(queryWrapper);
        if (count > 0) {
            log.info("文件{}已经上传过", objectName);
            fileUploadVO.setFileName(objectName);
            fileUploadVO.setExist(true);
            return fileUploadVO;
        }
//        如果需要服务端加密则生成秘钥
        ServerSideEncryptionCustomerKey ssc = null;
        if (encrypted) {
            if (StringUtils.isBlank(aesKey)) {
                //生成256位AES key.
                KeyGenerator aes = KeyGenerator.getInstance("AES");
                aes.init(256);
                aesKey = Base64.encode(aes.generateKey().getEncoded());
                log.info("生成的aesKey为：{}", aesKey);
            }
            SecretKeySpec aes = new SecretKeySpec(Base64.decode(aesKey), "AES");
            ssc = new ServerSideEncryptionCustomerKey(aes);
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

        try {
            long start = System.currentTimeMillis();
// Upload input stream with server-side encryption.
            if (encrypted) {
                minioClient.putObject(
                        PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                                inputStream, -1, 10 * 1024 * 1024)
                                .sse(ssc)
                                .build());
            } else {
                minioClient.putObject(
                        PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(inputStream
                                , -1, 10 * 1024 * 1024)
                                .build());
            }
            long end = System.currentTimeMillis();
            long usedTime = end - start;
            log.info("上传文件用时：{}毫秒", usedTime);
            coreFileDO.setAesKey(aesKey);
            coreFileDO.setBucketName(bucketName);
            coreFileDO.setFileName(objectName);
            coreFileDO.setCreatedAt(new Date());
            coreFileDO.setId(SnowflakeUtil.getSnowflakeId());
            coreFileDO.setStatus(1);
            coreFileDO.setOriginalFileName(originalFileName);
            coreFileDO.setExtension(FilenameUtils.getExtension(originalFileName));
            coreFileMapper.insert(coreFileDO);
        } finally {
            inputStream.close();
        }
        fileUploadVO.setFileName(objectName);
        return fileUploadVO;
    }

    /**
     * @author muyongliang
     * @date 2020/10/14 14:07
     * @description 根据文件名删除文件，包括minIO服务器和本地数据库数据
     */
    @Override
    public boolean deleteByBucketNameAndfileName(String bucketName, String fileName) throws Exception {
        QueryWrapper<CoreFileDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_name", fileName);
        int delete = coreFileMapper.delete(queryWrapper);
        // Remove object.
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
        return true;
    }

    /**
     * @author muyongliang
     * @date 2020/10/12 17:44
     * @description 文件下载业务
     */

    @Override
    public InputStream download(String fileName) throws Exception {
        CoreFileDO coreFileDO = getCoreFileByFileName(fileName);
        if (coreFileDO == null) {
            throw new BusinessException(FileErrorEnum.FILE_IS_NOT_EXIST);
        }
        String bucketName = coreFileDO.getBucketName();
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
        log.info("下载文件：{}用时：{}毫秒", fileName, usedTime);
        return inputStream;
    }

    /**
     * @author muyongliang
     * @date 2020/10/13 17:13
     * @description 用filename查询文件信息
     */
    @Override
    public CoreFileDO getCoreFileByFileName(String fileName) {
        QueryWrapper<CoreFileDO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("file_name", fileName.trim());
        CoreFileDO coreFileDO = coreFileMapper.selectOne(queryWrapper);
        return coreFileDO;
    }

    /**
     * @author muyongliang
     * @date 2020/10/13 17:13
     * @description 获取对象信息
     */
    public ObjectStat statObject(String bucketName, String objectName, boolean encrypted, String aesKey) {
        try {
            // Get information of an object.
            ObjectStat objectStat;
            if (encrypted) {
                SecretKeySpec aes = new SecretKeySpec(Base64.decode(aesKey), "AES");
                ServerSideEncryptionCustomerKey ssc = new ServerSideEncryptionCustomerKey(aes);
                objectStat =
                        minioClient.statObject(
                                StatObjectArgs.builder().bucket(bucketName).object(objectName).ssec(ssc).build());
            } else {
                objectStat =
                        minioClient.statObject(
                                StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            }
            return objectStat;
        } catch (Exception e) {
            log.info("查询对象是否存在时发生异常：{}", e.getMessage());
            return null;
        }
    }

}
