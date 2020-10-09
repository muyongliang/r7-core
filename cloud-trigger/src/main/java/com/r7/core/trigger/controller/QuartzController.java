package com.r7.core.trigger.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.trigger.dto.CoreQuartzJobDTO;
import com.r7.core.trigger.model.CoreQuartzJob;
import com.r7.core.trigger.service.CoreQuartzJobService;
import com.r7.core.trigger.vo.CoreQuartzJobVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 定时任务对外接口
 *
 * @author wutao
 * @date 2020/9/24
 */
@Slf4j
@Api(tags = "api/trigger",description = "定时任务")
@RestController
@RequestMapping("/trigger")
public class QuartzController {
    @Autowired
    CoreQuartzJobService coreQuartzJobService;

    @ApiOperation(value = "添加定时任务",
            response = CoreQuartzJobVO.class)
    @PostMapping("/")
    public ResponseEntity saveCoreQuartzJob(@Valid @RequestBody CoreQuartzJobDTO coreQuartzJobDto) {
        return ResponseEntity.success(coreQuartzJobService.saveCoreQuartzJob(coreQuartzJobDto,0L));
    }

    @ApiOperation(value = "根据id查询单个定时任务", response = CoreQuartzJobVO.class)
    @GetMapping("/{id}")
    public ResponseEntity findCoreQuartzJobById(@PathVariable Long id) {
        return ResponseEntity.success(coreQuartzJobService.findCoreQuartzJobById(id));
    }

    @ApiOperation(
            value = "根据定时任务ID修改定时任务的信息也就是执行规则",
            notes = "定时任务ID必须存在",
            response = CoreQuartzJobVO.class)
    @PutMapping("/{id}")
    public ResponseEntity updateRoleById(@PathVariable("id") Long id,
                                         @RequestParam String ruleExpression) {
        return ResponseEntity.success(coreQuartzJobService.updateCoreQuartzJobRuleExpressionById
                (id, ruleExpression, 5L));
    }
    @ApiOperation(value = "根据动态条件分页查询", response = CoreQuartzJobVO.class)
    @GetMapping("/page")
    public ResponseEntity pageCoreQuartzJob(@RequestParam( value = "status",required = false)Integer status,
                                            @RequestParam( value = "jobName",required = false) String jobName,
                                            @RequestParam( value = "jobGroup",required = false) String jobGroup,
                                            @RequestParam( value = "jobClass",required = false) String jobClass,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "15") Integer pageSize) {
        return ResponseEntity.success(coreQuartzJobService.pageCoreQuartzJob
                (status,jobName,jobGroup,jobClass,pageNum,pageSize));
    }

    @ApiOperation(value = "根据定d时任务id修改定时任务的状态", response = CoreQuartzJobVO.class)
    @PutMapping("/status")
    public ResponseEntity updateStatusById(@PathVariable Long id,
                                           @RequestParam Integer status){
        return ResponseEntity.success(coreQuartzJobService.updateStatusById(id,status,1L));
    }

    @ApiOperation(value = "修改全部状态", response = CoreQuartzJobVO.class)
    @PutMapping("/status/all")
    public ResponseEntity updateAllCoreQuartzJobStatus (@RequestParam Integer status){
        return ResponseEntity.success(coreQuartzJobService.updateAllCoreQuartzJobStatus(status,6L));
    }

    @ApiOperation(value = "修改描述", response = CoreQuartzJobVO.class)
    @PutMapping("/description")
    public ResponseEntity updateCoreQuartzJobDescriptionById(@PathVariable Long id,
                                                             @RequestParam String description){
        return ResponseEntity.success(coreQuartzJobService.updateCoreQuartzJobDescriptionById(id,
                description,4L));
    }
    @ApiOperation(value = "修改定时任务信息", response = CoreQuartzJobVO.class)
    @PutMapping("/")
    public ResponseEntity updateCoreQuartzJobDescriptionAndRuleExpressionById(@PathVariable Long id,
                                                                              @RequestParam String description,
                                                                              @RequestParam String ruleExpression){
       return ResponseEntity.success(coreQuartzJobService
                .updateCoreQuartzJobDescriptionAndRuleExpressionById(id,description,ruleExpression,6L));
    }


//    @ApiOperation(value = "根据定时任务名称查询", response = CoreQuartzJobVO.class)
//    @GetMapping("/page{jobName}")
//    @ResponseBody
//    public ResponseEntity getCoreQuartzJobByJobName(@RequestParam String jobName) {
//        return ResponseEntity.success(coreQuartzJobService.getCoreQuartzJobByJobName(jobName));
//    }
    //    @ApiOperation(value = "根据定时任务分组查询", response = CoreQuartzJobVO.class)
//    @GetMapping("/page{jobGroup}")
//    @ResponseBody
//    public ResponseEntity getCoreQuartzJobByJobGroup(@RequestParam String jobGroup) {
//        return ResponseEntity.success(coreQuartzJobService.getCoreQuartzJobByJobGroup(jobGroup));
//    }
//    @ApiOperation(value = "根据定时任务路径查询", response = CoreQuartzJobVO.class)
//    @GetMapping("/page{jobClass}")
//    @ResponseBody
//    public ResponseEntity getCoreQuartzJobByJobClass(@RequestParam String jobClass) {
//        return ResponseEntity.success(coreQuartzJobService.getCoreQuartzJobByJobClass(jobClass));
//    }
//    @ApiOperation(value = "查询全部", response = CoreQuartzJob.class)
//    @GetMapping("/find")
//    public ResponseEntity findAllCoreQuartzJob(Long appId){
//        return ResponseEntity.success(coreQuartzJobService.findAllCoreQuartzJob(2L));
//    }
}
