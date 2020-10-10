package com.r7.core.integral.service;

import com.r7.core.integral.dto.FileDataDTO;

import java.io.InputStream;

/**
 * @Author muyongliang
 * @Date 2020/10/9 17:02
 * @Description FileUploadService
 */
public interface FileUploadService {

    FileDataDTO upload(InputStream inputStream, String bucketName) throws Exception;

    InputStream download(FileDataDTO fileDataDTO) throws Exception;
}
