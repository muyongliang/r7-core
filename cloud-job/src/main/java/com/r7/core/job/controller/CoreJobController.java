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
    public ResponseEntity saveJob(
            @PathVariable("id") Long appId, @RequestBody CoreJobDto coreJobDto) {
        return ResponseEntity.success(coreJobService.saveJob(appId, coreJobDto, 0L));
    }

//    @ApiOperation(
//            value = "删除任务",
//            notes = "任务id")
//    @DeleteMapping("/remove/{id}")
//    public ResponseEntity removeJob(@PathVariable Long id) {
//        boolean whether = coreJobService.removeJobById(id, 0L);
//        return ResponseEntity.success(whether);
//    }

    @ApiOperation(
            value = "修改任务",
            notes = "任务的id")
    @PutMapping("/update/{id}")
    public ResponseEntity updateJobById(@PathVariable("id") Long id, @RequestBody CoreJobDto coreJobDto) {
        return ResponseEntity.success(coreJobService.updateJobById(id, coreJobDto, 0L));
    }

    @ApiOperation(
            value = "查询任务信息",
            notes = "任务id",
            response = CoreJobVo.class)
    @GetMapping("/{id}")
    public ResponseEntity findJobById(@PathVariable("id") Long id) {
        return ResponseEntity.success(coreJobService.findJobById(id));
    }

    @ApiOperation(value = "分页查询信息", response = CoreJobVo.class)
    @GetMapping("/page")
    public ResponseEntity pageJob(
            Integer status, String JobName,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreJobService.pageJob(status, JobName, pageNum, pageSize));
    }

    @ApiOperation(value = "发布/下架任务", response = CoreJobVo.class)
    @PutMapping("/on/off/{id}")
    public ResponseEntity onOffShelf(@PathVariable("id")Long id, CoreJobDto coreJobDto) {
        return ResponseEntity.success(coreJobService.updateJobById(id, coreJobDto, 0L));
    }

    @ApiOperation(value = "查询当前模块所有任务", response = CoreJobVo.class)
    @GetMapping("/page/current")
    public ResponseEntity pageCurrentJob(
            Long appId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreJobService.pageCurrentJob(appId, pageNum, pageSize));
    }

}
