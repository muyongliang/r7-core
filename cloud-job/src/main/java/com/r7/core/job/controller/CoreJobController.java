package com.r7.core.job.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.job.dto.CoreJobDto;
import com.r7.core.job.model.CoreJob;
import com.r7.core.job.service.CoreJobService;
import com.r7.core.job.vo.CoreJobVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zs
 */
@Api(value = "/api/core/job", tags = {"任务详情接口"})
@RestController
@RequestMapping("/api/core/job")
public class CoreJobController {

    @Resource
    private CoreJobService coreJobService;

    @ApiOperation(value = "添加任务详情信息")
    @PostMapping("/")
    public ResponseEntity saveJob(@RequestBody CoreJobDto coreJobDto) {
        return ResponseEntity.success();
    }

    @ApiOperation(
            value = "删除任务",
            notes = "任务id")
    @DeleteMapping("/remove/{id}")
    public ResponseEntity removeJob(@ApiParam(name = "任务id", required = true) Long id) {
        return ResponseEntity.success("删除成功");
    }

    @ApiOperation(
            value = "修改任务",
            notes = "任务的id")
    @PutMapping("/update/{id}")
    public ResponseEntity updateJobById(@PathVariable("id") Long id, @RequestBody CoreJob coreJob) {
        return ResponseEntity.success("任务修改成功");
    }

    @ApiOperation(
            value = "查询任务信息",
            notes = "任务id",
            response = CoreJobVo.class)
    @GetMapping("/{id}")
    public ResponseEntity findJobById(@ApiParam(name = "任务id", required = true) Long id) {
        return ResponseEntity.success();
    }

    @ApiOperation(
            value = "查询任务列表",
            response = CoreJobVo.class)
    @GetMapping("/list")
    public ResponseEntity listJob() {
        return ResponseEntity.success();
    }

    @ApiOperation(value = "分页查询信息", response = CoreJobVo.class)
    @GetMapping("/page")
    public ResponseEntity pageJob(String condition, Integer pageNum, Integer pageSize) {
        return ResponseEntity.success();
    }

}
