package com.r7.core.integral.service;

import io.minio.MinioClient;

import java.io.InputStream;

/**
 * @author zs
 */
public interface FileAction {
    /**
     *
     * @param client
     * @param bucket
     */
    void makeBucket(MinioClient client, String bucket);

    /**
     *
     * @param client
     * @param bucket
     * @param policy
     */
    void setBucketPolicy(MinioClient client, String bucket, String policy);

    /**
     *
     * @param client
     * @param bucket
     * @param objectKey
     * @param filePath
     * @param contentType
     * @return
     */
    String uploadFile(MinioClient client, String bucket, String objectKey, String filePath, String contentType);

    /**
     *
     * @param client
     * @param bucket
     * @param objectKey
     * @param inputStream
     * @param contentType
     * @return
     */
    String uploadInputStream(MinioClient client, String bucket, String objectKey, InputStream inputStream, String contentType);

    /**
     *
     * @param client
     * @param bucket
     * @param objectKey
     * @return
     */
    InputStream download(MinioClient client, String bucket, String objectKey);

    /**
     *
     * @param client
     * @param sourceBucket
     * @param sourceObjectKey
     * @param bucket
     * @param objectKey
     * @return
     */
    String copyFile(MinioClient client, String sourceBucket, String sourceObjectKey, String bucket, String objectKey);

    /**
     *
     * @param client
     * @param bucket
     * @param objectKey
     */
    void deleteFile(MinioClient client, String bucket, String objectKey);

    /**
     *
     * @param client
     * @param bucket
     * @param objectKey
     * @param expires
     * @return
     */
    String getSignedUrl(MinioClient client, String bucket, String objectKey, int expires);
}
