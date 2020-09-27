package com.r7.core.job.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.job.model.CoreJob;
import com.r7.core.job.service.JobInfoService;
import com.r7.core.job.vo.CoreJobVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zs
 */
@Api(value = "/jobInfo", tags = {"任务详情接口"})
@RestController
@RequestMapping("/jobInfo")
public class JobInfoController {

    @Resource
    private JobInfoService jobInfoService;

    @ApiOperation(value = "添加任务详情信息")
    @PostMapping("/addJob")
    public ResponseEntity addJob(@RequestBody CoreJob coreJob) {
        coreJob.setCreatedAt(new Date());
        coreJob.setUpdatedAt(new Date());
        coreJob.setOnShelf(new Date());
        jobInfoService.add(coreJob);
        return ResponseEntity.success("添加成功");
    }

    @ApiOperation(
            value = "删除任务",
            notes = "任务id")
    @PostMapping("/deleteJob")
    public ResponseEntity deleteJob(@ApiParam(name = "任务id", required = true) Long id) {
        jobInfoService.delete(id);
        return ResponseEntity.success("删除成功");
    }

    @ApiOperation(
            value = "修改任务",
            notes = "任务的id")
    @PutMapping("/updateJob")
    public ResponseEntity updateJob(@PathVariable("id") Long id, @RequestBody CoreJob coreJob) {
        jobInfoService.update(coreJob);
        return ResponseEntity.success("任务修改成功");
    }

    @ApiOperation(
            value = "查询任务信息",
            notes = "任务id",
            response = CoreJobVo.class)
    @PostMapping("/findJob")
    public ResponseEntity findJob(@ApiParam(name = "任务id", required = true) Long id) {
        CoreJob job = jobInfoService.findById(id);
        return ResponseEntity.success(job);
    }

    @ApiOperation(
            value = "查询任务列表",
            response = CoreJobVo.class)
    @GetMapping("/findAllJob")
    public ResponseEntity findAllJob() {
        List<CoreJob> jobList = jobInfoService.findAll();
        return ResponseEntity.success(jobList);
    }

}
