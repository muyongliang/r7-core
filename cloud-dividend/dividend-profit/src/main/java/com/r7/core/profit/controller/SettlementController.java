package com.r7.core.profit.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.profit.service.SettlementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author wt
 * @Description 定时核算测试接口
 */
@Slf4j
@Api(value = "/api/settlement", tags = {"定时核算测试接口，不对外提供"})
@RestController
@RequestMapping("/settlement")
public class SettlementController {

    @Autowired
    private SettlementService settlementService;

    @ApiOperation(value = "核算指定时间以前的所有未计算的分润明细", response = Integer.class)
    @PostMapping("/")
    public ResponseEntity getAllCoreProfitByStatus() {
        return ResponseEntity.success(settlementService.settlementAll(LocalDateTime.now()));
    }

}
