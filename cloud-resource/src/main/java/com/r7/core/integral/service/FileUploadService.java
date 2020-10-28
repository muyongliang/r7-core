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
    /**
     * @Author muyongliang
     * @Date 2020/10/12 17:44
     * @Description 文件下载业务
     */

    InputStream download(String fileName) throws Exception;

    /**
     * @Author muyongliang
     * @Date 2020/10/13 17:13
     * @Description 用filename查询文件信息
     */
    CoreFileDO getCoreFileByFileName(String fileName);

    /**
     * @Author muyongliang
     * @Date 2020/10/12 16:22
     * @Description 使用md5值作为对象名，用于去重，和防止文件覆盖
     * 优化方案：对小文件去重，大文件不去重，因为大文件太占内存，做md5需要复制流,对象名直接使用originalFileName
     */
    FileUploadVO uploadStream(InputStream inputStream, boolean encrypted, String aesKey, String originalFileName) throws Exception;

    /**
     * @Author muyongliang
     * @Date 2020/10/14 14:07
     * @Description 根据文件名删除文件，包括minIO服务器和本地数据库数据
     */
    boolean deleteByBucketNameAndfileName(String bucketName, String fileName) throws Exception;
}
