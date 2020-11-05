package com.r7.core.resource;

import cn.hutool.core.net.multipart.UploadFile;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import org.junit.Test;

/**
 * @author muyongliang
 * @date 2020/11/2
 * @description HuToolTest
 */
public class HuToolTest {
    @Test
    public void test1() {
        HttpUtil.createServer(8888)
                .addAction("/file", (request, response) -> {
                            final UploadFile file = request.getMultipart().getFile("file");
                            // 传入目录，默认读取HTTP头中的文件名然后创建文件
                            file.write("d:/test/");
                            response.write("OK!", ContentType.TEXT_PLAIN.toString());
                        }
                )
                .start();
    }
}
