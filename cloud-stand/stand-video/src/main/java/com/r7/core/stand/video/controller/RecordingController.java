package com.r7.core.stand.video.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.stand.video.service.RecordingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zs
 * @description: 视频录制接口
 * @date : 2020-10-20
 */
@Api(value = "/api/recording", tags = {"视频录制接口"})
@RestController
@RequestMapping("/recording")
public class RecordingController {

    @Resource
    private RecordingService recordingService;

    @ApiOperation(value = "开始认证录制")
    @PostMapping
    public ResponseEntity createChannel(@RequestParam("appId") String appId,
                                        @RequestParam("channel") String channel,
                                        @RequestParam(value = "channel", required = false) String userAccount,
                                        @RequestParam("channelKey") String channelKey,
                                        @RequestParam("uids") Integer... uids) {
        return ResponseEntity.success(recordingService.createChannel(appId, channel, userAccount, channelKey, uids));
    }
}
