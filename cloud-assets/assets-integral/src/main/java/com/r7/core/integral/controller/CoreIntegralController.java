package com.r7.core.integral.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.integral.dto.CoreIntegralDTO;
import com.r7.core.integral.service.CoreIntegralService;
import com.r7.core.integral.vo.CoreIntegralVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author wt
 * @Description 当前用户积分接口
 */
@Slf4j
@Api(value = "/api/integral/user", tags = {"当前用户积分接口"})
@RestController
@RequestMapping("/integral/user")
public class CoreIntegralController {

    @Resource
    private CoreIntegralService coreIntegralService;

    @ApiOperation(value = "积分详情新增", response = CoreIntegralVO.class)
    @PostMapping("")
    public ResponseEntity saveCoreIntegralDetail(@Valid @RequestBody CoreIntegralDTO coreIntegralDTO) {
        return ResponseEntity.success(coreIntegralService.saveCoreIntegral(coreIntegralDTO,0L));
    }

    @ApiOperation(value = "增加积分", response = CoreIntegralVO.class)
    @PutMapping("/add")
    public ResponseEntity updateCoreIntegralAddTotal(@RequestParam String businessCode,
                                                     @RequestParam Integer addInteger,
                                                     @RequestParam Long userId,
                                                     @RequestParam Integer sourceType ) {
        return ResponseEntity.success(coreIntegralService
                .updateCoreIntegralAddTotal(businessCode,addInteger,userId,sourceType,0L,2L));
    }
    @ApiOperation(value = "增加积分", response = CoreIntegralVO.class)
    @PutMapping("/reduce")
    public ResponseEntity updateCoreIntegralReduceTotal(@RequestParam String businessCode,
                                                        @RequestParam Integer reduceInteger,
                                                        @RequestParam Long userId,
                                                        @RequestParam Integer sourceType) {

        return ResponseEntity.success(coreIntegralService
                .updateCoreIntegralReduceTotal(businessCode,reduceInteger,userId,sourceType,
                        0L,2L));
    }


    @ApiOperation(value = "根据用户id查询积分信息", response = CoreIntegralVO.class)
    @GetMapping("/current/{userId}")
    public ResponseEntity getCoreIntegralByUserId( @PathVariable("userId") Long userId) {
        return ResponseEntity.success(coreIntegralService
                .getCoreIntegralByUserId(userId));
    }

    @ApiOperation(value = "根据用户积分信息id查询积分信息", response = CoreIntegralVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getCoreIntegralById( @PathVariable("id") Long id) {
        return ResponseEntity.success(coreIntegralService
                .getCoreIntegralById(id));
    }


    @ApiOperation(value = "根据用户积分信息id查询积分信息", response = CoreIntegralVO.class)
    @GetMapping("/page")
    public ResponseEntity pageCoreIntegralPage(
            @RequestParam(value = "userId",required = false) Long userId,
            @RequestParam(value = "pageNum",defaultValue = "1") Long pageNum,
            @RequestParam(value = "pageSize",defaultValue = "2") Long pageSize) {

        return ResponseEntity.success(coreIntegralService
                .pageCoreIntegralPage(userId,pageNum,pageSize));
    }
}
