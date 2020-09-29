package com.r7.core.job.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.job.dto.CoreJobDto;
import com.r7.core.job.dto.CoreJobStatusDto;
import com.r7.core.job.model.CoreJob;
import com.r7.core.job.service.CoreJobService;
import com.r7.core.job.vo.CoreJobVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 任务对外接口
 * @author zs
 */
@Api(value = "/api/job", tags = {"任务详情接口"})
@RestController
@RequestMapping("/job")
public class CoreJobController {

    @Resource
    private CoreJobService coreJobService;

    @ApiOperation(value = "添加任务详情信息")
    @PostMapping("/")
    public ResponseEntity saveJob(
            @RequestBody CoreJobDto coreJobDto) {
        return ResponseEntity.success(coreJobService.saveJob(coreJobDto, 0L, 0L));
    }

    @ApiOperation(
            value = "修改任务",
            notes = "任务的id",
            response = CoreJobVo.class)
    @PutMapping("/{id}")
    public ResponseEntity updateJobById(
            @PathVariable("id") Long id,
            @Valid @RequestBody CoreJobDto coreJobDto) {
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

    //todo 查询自己写方法
    @ApiOperation(value = "分页查询信息", response = CoreJobVo.class)
    @GetMapping("/page")
    public ResponseEntity pageJob(
            String search,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreJobService.pageJob(search, pageNum, pageSize));
    }

    @ApiOperation(value = "发布/下架任务", response = CoreJobVo.class)
    @PutMapping("/on/off/{id}")
    public ResponseEntity onOffShelf(
            @PathVariable("id")Long id,
            @Valid @RequestBody CoreJobStatusDto coreJobStatusDto) {
        return ResponseEntity.success(coreJobService.updateJobStatusById(id, coreJobStatusDto, 0L));
    }

    @ApiOperation(
            value = "查询当前模块所有任务",
            response = CoreJobVo.class)
    @GetMapping("/page/current")
    public ResponseEntity pageCurrentJob(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreJobService.pageCurrentJob(0L, 0L, pageNum, pageSize));
    }

}
