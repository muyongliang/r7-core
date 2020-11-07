package com.r7.core.stand.video.controller;

import com.r7.core.common.util.SingleThreadPoolExecutor;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.stand.video.service.RecordingService;
import io.agora.media.RtcTokenBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zs
 * @description: 视频录制接口
 * @date : 2020-10-20
 */
@Api(value = "/api/recording", tags = {"视频录制接口"})
@RestController
@RequestMapping("/recording")
@Slf4j
public class RecordingController {

    @Resource
    private RecordingService recordingService;

    @ApiOperation(value = "开始认证录制", response = String.class, notes = "data的值为录制视频的文件名")
    @PostMapping
    public ResponseEntity createChannel(@RequestParam("channel") String channel,
                                        @RequestParam("uids") Integer... uids) {
//        生成唯一文件名,用于上传文件和立刻返回
        String fileName = SnowflakeUtil.getSnowflakeId().toString() + ".mp4";
        log.info("生成的文件名为:{}", fileName);
//        异步录制视频
        SingleThreadPoolExecutor.INSTANCE.execute(() -> {
            try {
                recordingService.createChannel(fileName, channel, uids);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        });

        return ResponseEntity.success(fileName);
    }

    @ApiOperation(value = "获取录制token", response = String.class, notes = "data的值为token")
    @GetMapping("/token")
    public ResponseEntity token(@RequestParam("channel") String channel) {
        return ResponseEntity.success(recordingService.generateToken(channel, RtcTokenBuilder.Role.Role_Publisher));
    }
}
