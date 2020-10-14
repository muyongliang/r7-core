package com.r7.core.integral.controller;

import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.integral.constant.FileErrorEnum;
import com.r7.core.integral.model.CoreFileDO;
import com.r7.core.integral.service.FileUploadService;
import com.r7.core.integral.vo.FileUploadVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

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
    private FileUploadService fileUploadService;

    /**
     * @Author muyongliang
     * @Date 2020/10/12 15:53
     * @Description 文件上传接口，目前仅支持multipart/form-data，单文件上传，超过1mb文件不提供去重功能和防止覆盖，由客户端负责
     * @Description 当encrypted为true时使用服务端加密，false不使用服务端加密，默认为true。
     */
    @PostMapping
    @ApiOperation(
            value = "文件上传接口，目前仅支持multipart/form-data，单文件上传，超过1mb文件不提供去重功能和防止覆盖，由客户端负责"
            , notes = "encrypted:是否加密上传"
            , response = FileUploadVO.class)
    public ResponseEntity upload(
            @RequestParam(value = "encrypted", defaultValue = "true") boolean encrypted
            , @RequestParam(value = "file") MultipartFile file
    ) throws Exception {
        log.info("是否使用服务端加密：{}", encrypted);
        String aesKey = "";
        InputStream inputStream = file.getInputStream();
        if (file == null || inputStream.available() == 0) {
            return ResponseEntity.failure(FileErrorEnum.FILE_IS_EMPTY);
        }
        String originalFileName = file.getOriginalFilename();
        FileUploadVO fileUploadVO = fileUploadService.uploadStream(inputStream, encrypted, aesKey, originalFileName);
        return ResponseEntity.success(fileUploadVO);
    }

    @GetMapping
    @ApiOperation(
            value = "文件下载接口，大小限制1G"
            , notes = "fileName:上传时返回的fileName;inline:是否在浏览器中直接展示，否则下载到本地"
    )
    public void download(@RequestParam(value = "fileName") String fileName
            , @RequestParam(value = "inline", defaultValue = "false") boolean inline
            , HttpServletResponse response) throws Exception {
        CoreFileDO coreFileDO = fileUploadService.getCoreFileByFileName(fileName);
        if (coreFileDO == null) {
            throw new BusinessException(FileErrorEnum.FILE_IS_NOT_EXIST);
        }
        String originalFileName = coreFileDO.getOriginalFileName();
        if (StringUtils.isBlank(originalFileName)) {
            originalFileName = coreFileDO.getFileName();
        }
        if (!inline) {
            // 在浏览器中手动选择下载位置
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(originalFileName, "UTF-8"));
        }
        InputStream download = fileUploadService.download(fileName);
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

    @DeleteMapping
    @ApiOperation(
            value = "文件删除接口",
            response = boolean.class
    )
    public ResponseEntity delete(@RequestParam(value = "fileName") String fileName
    ) throws Exception {
        CoreFileDO coreFileDO = fileUploadService.getCoreFileByFileName(fileName);
        if (coreFileDO == null) {
            throw new BusinessException(FileErrorEnum.FILE_IS_NOT_EXIST);
        }
        String bucketName = coreFileDO.getBucketName();
        return ResponseEntity.success(fileUploadService.deleteByBucketNameAndfileName(bucketName, fileName));
    }

}
