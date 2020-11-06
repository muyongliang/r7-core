package com.r7.core.integral.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.integral.constant.OperateTypeEnum;
import com.r7.core.integral.constant.SourceTypeEnum;
import com.r7.core.integral.dto.CoreIntegralDetailDTO;
import com.r7.core.integral.service.CoreIntegralDetailService;
import com.r7.core.integral.vo.CoreIntegralDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author wt
 * @Description 积分详情接口
 */
@Slf4j
@Api(value = "/api/integral", tags = {"积分详情接口"})
@RestController
@RequestMapping("/integral/detail")
public class CoreIntegralDetailController {

    @Resource
    private CoreIntegralDetailService coreIntegralDetailService;

    @ApiOperation(value = "积分详情新增", response = CoreIntegralDetailVO.class)
    @PostMapping("")
    public ResponseEntity saveCoreIntegralDetail(@Valid @RequestBody CoreIntegralDetailDTO coreIntegralDetailDTO) {
        return ResponseEntity.success(coreIntegralDetailService.saveCoreIntegralDetail(coreIntegralDetailDTO,
                0L, 1L));
    }

    @ApiOperation(value = "根据id查询积分详情信息", response = CoreIntegralDetailVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getCoreIntegralDetailById(@PathVariable("id") Long id) {
        return ResponseEntity.success(coreIntegralDetailService.getCoreIntegralDetailById(id,
                0L));
    }

    @ApiOperation(value = "分页查询所有用户的积分详情记录信息", response = CoreIntegralDetailVO.class)
    @GetMapping("/page")
    public ResponseEntity pageCoreIntegralDetailAll(
            @RequestParam(value = "businessCode", required = false) String businessCode,
            @RequestParam(value = "sourceType", required = false) SourceTypeEnum sourceType,
            @RequestParam(value = "appId", required = false) Long appId,
            @RequestParam(value = "operateType", required = false) OperateTypeEnum operateType,
            @RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
            @RequestParam(value = "pageSize", defaultValue = "2") Long pageSize) {
        return ResponseEntity.success(coreIntegralDetailService.pageCoreIntegralDetailAll(
                businessCode, sourceType, appId, operateType, pageNum, pageSize
        ));
    }

    @ApiOperation(value = "分页查询所有用户的积分详情记录信息", response = CoreIntegralDetailVO.class)
    @GetMapping("/page/{userId}")
    public ResponseEntity pageCoreIntegralDetailByUserId(

            @PathVariable("userId") Long userId,
            @RequestParam(value = "sourceType", required = false) SourceTypeEnum sourceType,
            @RequestParam(value = "appId", required = false) Long appId,
            @RequestParam(value = "operateType", required = false) OperateTypeEnum operateType,
            @RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
            @RequestParam(value = "pageSize", defaultValue = "2") Long pageSize) {
        return ResponseEntity.success(coreIntegralDetailService.pageCoreIntegralDetailByUserId(
                userId, appId, operateType, sourceType, pageNum, pageSize
        ));
    }


}
