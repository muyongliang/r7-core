package com.r7.core.common.util;


import okhttp3.*;

import java.io.File;

/**
 * @author muyongliang
 * @date 2020/11/2
 * @description ClientUploadUtil
 */
public class ClientUploadUtil {
    /**
     * @param url      资源服务器url
     * @param filePath 上传文件路径
     * @param fileName 原始文件名
     * @return
     * @throws Exception
     */
    public static Response upload(String url, String filePath, String fileName) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName,
                        RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }
}
