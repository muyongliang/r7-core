package com.r7.core.profit.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.profit.dto.CoreProfitDTO;
import com.r7.core.profit.model.CoreProfit;
import com.r7.core.profit.service.CoreProfitService;
import com.r7.core.profit.vo.CoreProfitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author wutao
 * @Description 分润明细接口
 *
 */
@Slf4j
@Api(value = "/api/profit", tags = {"分润明细接口"})
@RestController
@RequestMapping("/profit")
public class CoreProfitController {

    @Autowired
    private CoreProfitService coreProfitService;


    @ApiOperation(value = "根据分润id查详情", response = CoreProfitVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getCoreProfitById(@PathVariable("id") Long id,
                                            @RequestParam Long appId) {
        return ResponseEntity.success(coreProfitService.getCoreProfitById(id,appId));
    }


    @ApiOperation(value = "根据条件分页查询", response = CoreProfitVO.class)
    @GetMapping("/page")
    public ResponseEntity pageCoreProfit(@RequestParam(required = false) Long userId,
                                         @RequestParam(required = false) Long orderId,
                                         @RequestParam(required = false) Long appId,
                                         @RequestParam(required = false) Integer type,
                                         @RequestParam(defaultValue = "1",required = false) Integer pageNum,
                                         @RequestParam(defaultValue = "2",required = false) Integer pageSize) {
        return ResponseEntity.success(coreProfitService.pageCoreProfit(userId,orderId,appId,type,
                pageNum,pageSize));
    }



    //====================以下测试用============================

    @ApiOperation(value = "添加分润明细", response = CoreProfitVO.class)
    @PostMapping("")
    public ResponseEntity saveUimResource(@Valid @RequestBody CoreProfitDTO coreProfitDTO) {
        return ResponseEntity.success(coreProfitService.saveCoreProfit(coreProfitDTO,2L));
    }

    @ApiOperation(value = "核算待发放金额", response = Integer.class)
    @PostMapping("/amount")
    public ResponseEntity settlementAmount(@RequestParam Long userId,
                                           @RequestParam Long appId) {
        return ResponseEntity.success(coreProfitService.settlementAmount(userId,appId,1,10L,
                LocalDateTime.now()));
    }


    @ApiOperation(value = "核算待发放积分", response = Integer.class)
    @PostMapping("/integral")
    public ResponseEntity settlementIntegral(@RequestParam Long userId,
                                             @RequestParam Long appId) {
        return ResponseEntity.success(coreProfitService.settlementIntegral(userId,appId,1
                ,10L,LocalDateTime.now()
        ));
    }

    //=========核算调用测试,不对外提供=======

    @ApiOperation(value = "核算调用", response = CoreProfit.class)
    @GetMapping("/status")
    public ResponseEntity getAllCoreProfitByStatus(@RequestParam Integer status) {
        return ResponseEntity.success(coreProfitService.getAllCoreProfitByStatus(status,LocalDateTime.now()));
    }

}
