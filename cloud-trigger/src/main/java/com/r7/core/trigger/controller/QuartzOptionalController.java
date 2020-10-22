package com.r7.core.trigger.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.trigger.service.QuartzOptionalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wutao 定时任务的操作接口
 * @Description
 * @date 2020/9/29
 */
@Slf4j
@Api(value = "api/trigger/optional",tags = "定时任务操作")
@RestController
@RequestMapping("/trigger/optional")
public class QuartzOptionalController {

    @Autowired
    QuartzOptionalService quartzOptionalService;

    @ApiOperation(value = "定时任务根据id初始化启动")
    @PostMapping("/trigger/{id}")
    public ResponseEntity trigger(@PathVariable Long id) {
        return ResponseEntity.success(quartzOptionalService.startCoreQuartzJob(id,1L));
    }

    @ApiOperation("暂停任务")
    @PostMapping("/pause")
    public ResponseEntity pause(@RequestParam Long id) {
        return ResponseEntity.success(quartzOptionalService.pause(id,2L));
    }

    @ApiOperation("恢复任务")
    @PostMapping("/resume")
    public ResponseEntity resume(@RequestParam Long id) {
        return ResponseEntity.success(quartzOptionalService.resume(id,3L));
    }

    @ApiOperation(value = "恢复所有已经暂停的定时任务")
    @PostMapping("/resume/all")
    public ResponseEntity resumeAllJobs() {
        return ResponseEntity.success(quartzOptionalService.resumeAllJobs(0L,2L));
    }

    @ApiOperation(value = "暂停所有定时任务")
    @PostMapping("/pause/all")
    public ResponseEntity shutdownAllJobs(){
        return ResponseEntity.success(quartzOptionalService.pauseAllJobs(0L,2L));
    }

    @ApiOperation(value = "立即执行")
    @PostMapping("/immediate")
    public ResponseEntity immediatePerform(@RequestParam Long id){
        return ResponseEntity.success(quartzOptionalService.immediatePerform(id,4L));
    }

    @ApiOperation(value = "移除任务")
    @DeleteMapping("/")
    public ResponseEntity removeCoreQuartzJob(@RequestParam Long id) {
        return ResponseEntity.success(quartzOptionalService.removeCoreQuartzJob(id,0L));
    }

}
