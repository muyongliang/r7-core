package com.r7.core.integral.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.integral.constant.BucketNameEnum;
import com.r7.core.integral.constant.FileErrorEnum;
import com.r7.core.integral.dto.CoreFileDTO;
import com.r7.core.integral.model.CoreFileDO;
import com.r7.core.integral.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
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
     * @Description 文件上传接口，目前仅支持单文件上传，如果使用form-data提交文件（推荐，可以保留原始文件名方便下载浏览），文件名请使用"file"；也可以使用binary提交
     * @Description 当encrypted为true时使用服务端加密，false不使用服务端加密，默认为true。
     */
    @PostMapping
    @ApiOperation(
            value = "文件上传接口，大小限制1G，目前仅支持单文件上传，如果使用form-data提交文件（推荐，可以保留原始文件名方便下载浏览），" +
                    "文件名请使用\"file\"；也可以使用binary提交"
            , notes = "encrypted:是否加密上传"
            , response = CoreFileDTO.class)
    public ResponseEntity upload(
            @RequestParam(value = "encrypted", defaultValue = "true") boolean encrypted
            , @RequestParam(value = "file", required = false) MultipartFile file
            , HttpServletRequest request) throws Exception {
        log.info("是否使用服务端加密：{}", encrypted);
        String aesKey = "";
        String originalFileName = "";
        String bucketName;
        CoreFileDTO coreFileDTO;
        InputStream inputStream;
        if (file != null) {
            originalFileName = file.getOriginalFilename();
            inputStream = file.getInputStream();
        } else {
            inputStream = request.getInputStream();
        }
        if (inputStream.available() == 0) {
            return ResponseEntity.failure(FileErrorEnum.FILE_IS_EMPTY);
        } else if (inputStream.available() < 1024 * 1024) {
            bucketName = BucketNameEnum.SMALL.name();
        } else if (inputStream.available() < 10 * 1024 * 1024) {
            bucketName = BucketNameEnum.MIDDLE.name();
        } else if (inputStream.available() < 1024 * 1024 * 1024) {
            bucketName = BucketNameEnum.LARGE.name();
        } else {
            bucketName = BucketNameEnum.HUGE.name();
        }
        coreFileDTO = fileUploadService.uploadStream(inputStream, bucketName, encrypted, aesKey, originalFileName);
        return ResponseEntity.success(coreFileDTO);
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
        response.setContentType("application/octet-stream");
        String originalFileName = coreFileDO.getOriginalFileName();
        if (StringUtils.isNotBlank(originalFileName)) {
            if (inline) {
                // 在浏览器中打开
                response.addHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(originalFileName, "UTF-8"));
            } else {
                // 直接下载
                response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(originalFileName, "UTF-8"));
            }
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

}
