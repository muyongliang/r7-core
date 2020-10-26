package com.r7.core.integral.service;

import com.r7.core.integral.model.CoreFileDO;
import com.r7.core.integral.vo.FileUploadVO;

import java.io.InputStream;

/**
 * @Author muyongliang
 * @Date 2020/10/9 17:02
 * @Description FileUploadService
 */
public interface FileUploadService {
    InputStream download(String fileName) throws Exception;

    CoreFileDO getCoreFileByFileName(String fileName);

    FileUploadVO uploadStream(InputStream inputStream, boolean encrypted, String aesKey, String originalFileName) throws Exception;

    boolean deleteByBucketNameAndfileName(String bucketName, String fileName) throws Exception;
}
