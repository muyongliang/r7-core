package com.r7.core.job.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.job.dto.CoreJobProgressDto;
import com.r7.core.job.service.CoreJobProgressService;
import com.r7.core.job.vo.CoreJobProgressVo;
import com.r7.core.job.vo.CoreJobVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zs
 * @description: 任务进度
 * @date : 2020-09-28
 */
@Api(value = "/api/core/job/progress", tags = "任务进度接口")
@RestController
@RequestMapping("/api/core/job/progress")
public class CoreJobProgressController {
    @Resource
    private CoreJobProgressService coreJobProgressService;

    @ApiOperation(value = "插入任务进度")
    @PostMapping("/")
    public ResponseEntity saveJobProgress(@RequestBody CoreJobProgressDto coreJobProgressDto) {
        return ResponseEntity.success(coreJobProgressService.saveJobProgress(coreJobProgressDto, 0L));
    }

    @ApiOperation(value = "修改任务进度")
    @PutMapping("/{id}")
    public ResponseEntity updateJobProgressById(
            @PathVariable("id") Long id, CoreJobProgressDto coreJobProgressDto) {
        return ResponseEntity.success(coreJobProgressService.updateJobById(id, coreJobProgressDto, 0L));
    }

    @ApiOperation(value = "查询用户任务进度", response = CoreJobProgressVo.class)
    @GetMapping("/{id}")
    public ResponseEntity findJobProgressByUserId(@PathVariable("id")Long userId) {
        return ResponseEntity.success(coreJobProgressService.getJobProgressByUserId(userId));
    }

}
