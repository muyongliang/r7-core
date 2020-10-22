package com.r7.core.job.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.job.dto.CoreJobDTO;
import com.r7.core.job.dto.CoreJobStatusDTO;
import com.r7.core.job.service.CoreJobService;
import com.r7.core.job.vo.CoreJobVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 任务对外接口
 *
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
    public ResponseEntity saveJob(@Valid @RequestBody CoreJobDTO coreJobDto) {
        return ResponseEntity.success(coreJobService.saveJob(coreJobDto, 0L, 0L));
    }

    @ApiOperation(
            value = "修改任务",
            notes = "任务的id",
            response = CoreJobVO.class)
    @PutMapping("/{id}")
    public ResponseEntity updateJobById(
            @PathVariable("id") Long id,
            @Valid @RequestBody CoreJobDTO coreJobDto) {
        return ResponseEntity.success(coreJobService.updateJobById(id, coreJobDto, 0L));
    }

    @ApiOperation(
            value = "查询任务信息",
            notes = "任务id",
            response = CoreJobVO.class)
    @GetMapping("/{id}")
    public ResponseEntity findJobById(@PathVariable("id") Long id) {
        return ResponseEntity.success(coreJobService.findJobById(id));
    }

    @ApiOperation(value = "分页查询信息", response = CoreJobVO.class)
    @GetMapping("/page")
    public ResponseEntity pageJob(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreJobService.pageJob(search, pageNum, pageSize));
    }

    @ApiOperation(value = "发布/下架任务", response = CoreJobVO.class)
    @PutMapping("/on/off/{id}")
    public ResponseEntity onOffShelf(
            @PathVariable("id") Long id,
            @Valid @RequestBody CoreJobStatusDTO coreJobStatusDto) {
        return ResponseEntity.success(coreJobService.updateJobStatusById(id, coreJobStatusDto, 0L));
    }

    @ApiOperation(
            value = "查询当前模块所有任务",
            response = CoreJobVO.class)
    @GetMapping("/page/current")
    public ResponseEntity pageCurrentJob(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreJobService.pageCurrentJob(0L, 0L, pageNum, pageSize));
    }

}
