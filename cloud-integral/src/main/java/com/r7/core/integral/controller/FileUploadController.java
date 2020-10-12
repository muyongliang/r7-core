package com.r7.core.integral.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.integral.dto.FileDataDTO;
import com.r7.core.integral.service.FileUploadService;
import io.minio.MinioClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;

/**
 * @Auther muyongliang
 * @Date 2020/10/9
 * @Description FileUploadController
 */
@Slf4j
@RestController
@RequestMapping("/file")
@Api(value = "/api/upload", tags = {"文件上传接口"})
public class FileUploadController {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * @Author muyongliang
     * @Date 2020/10/12 15:53
     * @Description 文件上传接口，目前仅支持单文件上传，如果使用form-data提交文件，文件名请使用"file",也可以使用binary提交
     * @Description bucketName不存在时使用默认"",当encrypted为true时使用服务端加密，false不适用服务端加密，默认为true。
     * 当aesKey存在时使用用户提供的秘钥，可以实现去重，防止覆盖，没有则由服务端生成aesKey并返回。为256位AES key，并使用base64编码保存。
     */
    @PostMapping
    @ApiOperation(
            value = "文件上传接口，大小限制1G"
            , response = FileDataDTO.class)
    public ResponseEntity upload(@RequestParam(value = "bucketName", required = false) String bucketName
            , @RequestParam(value = "encrypted", defaultValue = "true") boolean encrypted
            , @RequestParam(value = "aesKey", required = false) String aesKey
            , @RequestParam(value = "file", required = false) MultipartFile file
            , HttpServletRequest request) throws Exception {
        log.info("是否提供桶名：{}", bucketName);
        log.info("是否使用服务端加密：{}", encrypted);
        log.info("是否提供aesKey：{}", aesKey);
        FileDataDTO fileDataDTO;
        InputStream inputStream;
        if (file != null) {
            inputStream = file.getInputStream();
        } else {
            inputStream = request.getInputStream();
        }
        if (inputStream.available() == 0) {
            fileDataDTO = new FileDataDTO();
            fileDataDTO.setMsg("上传文件内容为空");
            return ResponseEntity.success(fileDataDTO);
        }
        fileDataDTO = fileUploadService.uploadStreamUseSSE(inputStream, bucketName, encrypted, aesKey);
        return ResponseEntity.success(fileDataDTO);
    }

    @GetMapping
    @ApiOperation(
            value = "文件下载接口，大小限制1G")
    public void download(@RequestBody @Valid FileDataDTO fileDataDTO, HttpServletResponse response) throws Exception {

        InputStream download = fileUploadService.download(fileDataDTO);
        // 用response获得字节输出流
        ServletOutputStream outputStream = response.getOutputStream();
        int len;
        byte[] buffer = new byte[5 * 1024 * 1024];
        while ((len = download.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
        download.close();
    }

}
