package com.r7.core.integral.service.impl;

import com.r7.core.integral.config.MinIOConfig;
import com.r7.core.integral.service.FileAction;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.r7.core.integral.config.MinIOConfig.getObjectPrefixUrl;

/**
 * @author zs
 */
@Service
public class FileActionImpl implements FileAction {


    @Override
    public void makeBucket(MinioClient client, String bucket) {
        // 判断桶是否存在
        boolean isExist = false;
        try {
            isExist = client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!isExist) {
                // 新建桶
                client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (RegionConflictException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setBucketPolicy(MinioClient client, String bucket, String policy) {
        switch (policy) {
            case "read-only":
                try {
                    client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(MinIOConfig.READ_ONLY.replace(MinIOConfig.BUCKET_PARAM, bucket)).build());
                } catch (ErrorResponseException e) {
                    e.printStackTrace();
                } catch (InsufficientDataException e) {
                    e.printStackTrace();
                } catch (InternalException e) {
                    e.printStackTrace();
                } catch (InvalidBucketNameException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidResponseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (ServerException e) {
                    e.printStackTrace();
                } catch (XmlParserException e) {
                    e.printStackTrace();
                }
                break;
            case "write-only":
                try {
                    client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(MinIOConfig.WRITE_ONLY.replace(MinIOConfig.BUCKET_PARAM, bucket)).build());
                } catch (ErrorResponseException e) {
                    e.printStackTrace();
                } catch (InsufficientDataException e) {
                    e.printStackTrace();
                } catch (InternalException e) {
                    e.printStackTrace();
                } catch (InvalidBucketNameException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidResponseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (ServerException e) {
                    e.printStackTrace();
                } catch (XmlParserException e) {
                    e.printStackTrace();
                }
                break;
            case "read-write":
                try {
                    client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(MinIOConfig.READ_WRITE.replace(MinIOConfig.BUCKET_PARAM, bucket)).build());
                } catch (ErrorResponseException e) {
                    e.printStackTrace();
                } catch (InsufficientDataException e) {
                    e.printStackTrace();
                } catch (InternalException e) {
                    e.printStackTrace();
                } catch (InvalidBucketNameException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidResponseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (ServerException e) {
                    e.printStackTrace();
                } catch (XmlParserException e) {
                    e.printStackTrace();
                }
                break;
            case "none":
            default:
                break;
        }
    }

    @Override
    public String uploadFile(MinioClient client, String bucket, String objectKey, String filePath, String contentType) {
        try {
            client.uploadObject(UploadObjectArgs.builder().bucket(bucket).object(objectKey).filename(filePath).contentType(contentType).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return getObjectPrefixUrl(bucket) + objectKey;
    }

    @Override
    public String uploadInputStream(MinioClient client, String bucket, String objectKey, InputStream inputStream, String contentType) {
        try {
            client.putObject(
                    PutObjectArgs.builder().bucket(bucket).object(objectKey)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(contentType).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return getObjectPrefixUrl(bucket) + objectKey;
    }

    @Override
    public InputStream download(MinioClient client, String bucket, String objectKey) {
        InputStream inputStream = null;
        try {
            inputStream = client.getObject(GetObjectArgs.builder().bucket(bucket).object(objectKey).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    @Override
    public String copyFile(MinioClient client, String sourceBucket, String sourceObjectKey, String bucket, String objectKey) {
        CopySource source = CopySource.builder().bucket(sourceBucket).object(sourceObjectKey).build();
        try {
            client.copyObject(CopyObjectArgs.builder().bucket(bucket).object(objectKey).source(source).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return getObjectPrefixUrl(bucket) + objectKey;
    }

    @Override
    public void deleteFile(MinioClient client, String bucket, String objectKey) {
        try {
            client.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectKey).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getSignedUrl(MinioClient client, String bucket, String objectKey, int expires) {
        String url = null;
        try {
            url = client.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder().method(Method.GET)
                            .bucket(bucket).object(objectKey).expiry(expires).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidExpiresRangeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        }
        return url;
    }
}
