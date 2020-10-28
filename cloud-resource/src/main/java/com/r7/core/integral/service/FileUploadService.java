package com.r7.core.integral.service;

import com.r7.core.integral.model.CoreFileDO;
import com.r7.core.integral.vo.FileUploadVO;

import java.io.InputStream;

/**
 * @author muyongliang
 * @date 2020/10/9 17:02
 * @description FileUploadService
 */
public interface FileUploadService {

    /**
     * description:文件下载业务
     *
     * @param fileName
     * @return 文件名
     * @throws Exception
     * @author muyongliang
     * @date 2020/10/12 17:44
     */

    InputStream download(String fileName) throws Exception;

    /**
     * description 文件下载业务
     * @param fileName 文件名
     * @return CoreFileDO
     */
    CoreFileDO getCoreFileByFileName(String fileName);


    /**
     * 使用md5值作为对象名，用于去重，和防止文件覆盖
     * 优化方案：对小文件去重，大文件不去重，因为大文件太占内存，做md5需要复制流,对象名直接使用originalFileName
     *
     * @param inputStream      输入流
     * @param encrypted        是否加密
     * @param aesKey           秘钥
     * @param originalFileName 原始文件名
     * @return
     * @throws Exception
     * @author muyongliang
     * @date 2020/10/12 16:22
     */
    FileUploadVO uploadStream(InputStream inputStream, boolean encrypted, String aesKey, String originalFileName) throws Exception;

    /**
     * @author muyongliang
     * @date 2020/10/14 14:07
     * @description 根据文件名删除文件，包括minIO服务器和本地数据库数据
     */
    /**
     * 根据文件名删除文件，包括minIO服务器和本地数据库数据
     *
     * @param bucketName 桶名
     * @param fileName   对象名
     * @return 是否删除成功
     * @throws Exception
     */
    boolean deleteByBucketNameAndfileName(String bucketName, String fileName) throws Exception;
}
