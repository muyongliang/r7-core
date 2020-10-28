package com.r7.core.stand.video.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.stand.video.service.CoreVideoService;
import com.r7.core.stand.video.vo.CoreVideoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zs
 * @description: 认证录制对外接口
 * @date : 2020-10-10
 */

@Api(value = "/api/video", tags = {"认证录制接口"})
@RestController
@RequestMapping("/video")
public class CoreVideoController {

    @Resource
    private CoreVideoService coreVideoService;


    @ApiOperation(
            value = "查询视频信息",
            notes = "视频id",
            response = CoreVideoVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getVideoById(@PathVariable("id") Long id) {
        return ResponseEntity.success(coreVideoService.getVideoById(id));
    }

    @ApiOperation(
            value = "分页查询信息",
            response = CoreVideoVO.class)
    @GetMapping("page")
    public ResponseEntity pageVideo(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreVideoService.pageVideo(search, pageNum, pageSize));
    }
}
