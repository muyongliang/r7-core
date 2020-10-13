package com.r7.core.integral.service;

import com.r7.core.integral.dto.CoreFileDTO;

import java.io.InputStream;

/**
 * @Author muyongliang
 * @Date 2020/10/9 17:02
 * @Description FileUploadService
 */
public interface FileUploadService {
    InputStream download(String fileName) throws Exception;

    CoreFileDTO uploadStream(InputStream inputStream, String bucketName, boolean encrypted, String aesKey, String originalFileName) throws Exception;

}
