package com.r7.core.integral.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.integral.dto.FileDataDTO;
import com.r7.core.integral.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
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
@RequestMapping("/upload")
@RestController
@Api(value = "/api/upload", tags = {"文件上传接口"})
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/{bucketName}")
    @ApiOperation(
            value = "文件上传接口，大小限制1G"
            , response = FileDataDTO.class)
    public ResponseEntity upload(@PathVariable("bucketName") String bucketName, HttpServletRequest request) throws Exception {
        ServletInputStream inputStream = request.getInputStream();
        return ResponseEntity.success(fileUploadService.upload(inputStream, bucketName));
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
        download.close();
    }

}
