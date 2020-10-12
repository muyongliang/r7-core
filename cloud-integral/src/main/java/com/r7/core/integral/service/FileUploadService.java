package com.r7.core.integral.service;

import com.r7.core.integral.dto.FileDataDTO;

import java.io.InputStream;

/**
 * @Author muyongliang
 * @Date 2020/10/9 17:02
 * @Description FileUploadService
 */
public interface FileUploadService {
    InputStream download(FileDataDTO fileDataDTO) throws Exception;

    FileDataDTO uploadStreamUseSSE(InputStream inputStream, String bucketName, boolean encrypted, String aesKey) throws Exception;

}
