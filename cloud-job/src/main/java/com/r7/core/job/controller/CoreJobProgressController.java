package com.r7.core.job.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.job.dto.CoreJobProgressDTO;
import com.r7.core.job.service.CoreJobProgressService;
import com.r7.core.job.vo.CoreJobProgressVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zs
 * @description: 任务进度对外接口
 * @date : 2020-09-28
 */
@Api(value = "/api/job/progress", tags = "任务进度接口")
@RestController
@RequestMapping("/job/progress")
public class CoreJobProgressController {

    @Resource
    private CoreJobProgressService coreJobProgressService;

    @ApiOperation(
            value = "新增任务进度",
            response = CoreJobProgressVO.class)
    @PostMapping("/")
    public ResponseEntity saveJobProgress(@Valid @RequestBody CoreJobProgressDTO coreJobProgressDto) {
        return ResponseEntity.success(coreJobProgressService.saveJobProgress(coreJobProgressDto, 0L));
    }

    @ApiOperation(
            value = "根据任务进度id修改任务进度",
            notes = "任务进度id必须存在",
            response = CoreJobProgressVO.class)
    @PutMapping("/{id}")
    public ResponseEntity updateJobProgressById(
            @PathVariable("id") Long id,
            @Valid @RequestBody CoreJobProgressDTO coreJobProgressDto) {
        return ResponseEntity.success(coreJobProgressService.updateJobById(id, coreJobProgressDto, 0L));
    }

    @ApiOperation(
            value = "根据用户id查询用户完成任务进度",
            response = CoreJobProgressVO.class)
    @GetMapping("/{id}")
    public ResponseEntity findJobProgressByUserId(@PathVariable("id") Long userId) {
        return ResponseEntity.success(coreJobProgressService.getJobProgressByUserId(userId));
    }

}
