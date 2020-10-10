package com.r7.core.trigger.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.trigger.dto.CoreQuartzJobInfoDTO;
import com.r7.core.trigger.service.CoreQuartzJobInfoService;
import com.r7.core.trigger.vo.CoreQuartzJobInfoVO;
import com.r7.core.trigger.vo.CoreQuartzJobVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author wutao
 * @Description
 * @date 2020/9/29
 */
@Api(tags = "api/record",description = "定时任务执行情况")
@RestController
@RequestMapping("/record")
public class CoreQuartzJobInfo {
    @Autowired
    CoreQuartzJobInfoService coreQuartzJobInfoService;

    @ApiOperation(value = "添加定时任务执行情况记录",
            response = CoreQuartzJobVO.class)
    @PostMapping("/")
    public ResponseEntity saveCoreQuartzJob(@Valid @RequestBody CoreQuartzJobInfoDTO coreQuartzJobInfoDto,
                                            @RequestParam LocalDateTime startAt ,
                                            @RequestParam LocalDateTime endAt) {
        return ResponseEntity.success(coreQuartzJobInfoService.saveCoreQuartzJobInfo(coreQuartzJobInfoDto,startAt,endAt));
    }

    @ApiOperation(value = "根据动态条件分页查询",
            response = CoreQuartzJobInfoVO.class)
    @GetMapping("/page")
    public ResponseEntity pageCoreQuartzJob(@RequestParam Long jobId,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "15") Integer pageSize) {
        return ResponseEntity.success(coreQuartzJobInfoService.pageCoreQuartzJob
                (jobId,pageNum,pageSize));
    }
}
