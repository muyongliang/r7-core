package com.r7.core.common.fegin;

import com.r7.core.common.web.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author muyongliang
 * @date 2020/11/7 14:38
 * @description 资源模块调用
 */
@Component
@FeignClient("cloud-resource")
public interface ResourceFeign {


    @PostMapping(value = "/api/file",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity upload(@RequestBody MultipartFile file,
                          @RequestParam(value = "encrypted") boolean encrypted);


}
