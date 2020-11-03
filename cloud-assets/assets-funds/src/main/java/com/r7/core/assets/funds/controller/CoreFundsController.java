package com.r7.core.assets.funds.controller;

import com.r7.core.assets.funds.dto.CoreFundsDTO;
import com.r7.core.assets.funds.service.CoreFundsService;
import com.r7.core.assets.funds.vo.CoreFundsPageVO;
import com.r7.core.assets.funds.vo.CoreFundsVO;
import com.r7.core.common.web.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zs
 * @description: 核心资金接口
 * @date : 2020-10-28
 */
@Slf4j
@Api(value = "/api/funds", tags = {"核心资金接口"})
@RestController
@RequestMapping("/funds")
public class CoreFundsController {

    @Resource
    private CoreFundsService coreFundsService;

    @ApiOperation(value = "新增资金记录", response = Boolean.class)
    @PostMapping("")
    public ResponseEntity saveFunds(@Valid @RequestBody CoreFundsDTO coreFundsDto) {
        return ResponseEntity.success(coreFundsService.saveFunds(coreFundsDto, 0L, 0L, 0L));
    }

    @ApiOperation(value = "根据id查询资金流动记录", response = CoreFundsVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getFundsById(@PathVariable("id") Long id) {
        return ResponseEntity.success(coreFundsService.getFundsById(id));
    }

    @ApiOperation(value = "根据用户id分页查询资金流动情况", response = CoreFundsPageVO.class)
    @GetMapping("/page/{userId}")
    public ResponseEntity pageFundsByUserId(@PathVariable(value = "userId") Long userId,
                                            @RequestParam(value = "status", required = false) Integer status,
                                            @RequestParam(value = "transactionStatus", required = false) Integer transactionStatus,
                                            @RequestParam(value = "channel", required = false) Integer channel,
                                            @RequestParam(value = "startDate", required = false) String startDate,
                                            @RequestParam(value = "endDate", required = false) String endDate,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreFundsService.pageFundsByUserId(userId, status, transactionStatus, channel, startDate, endDate, pageNum, pageSize));
    }

    @ApiOperation(value = "根据条件分页查询平台用户全部资金流动记录", response = CoreFundsPageVO.class)
    @GetMapping("/page")
    public ResponseEntity pageFundsAll(@RequestParam(value = "status", required = false) Integer status,
                                       @RequestParam(value = "transactionStatus", required = false) Integer transactionStatus,
                                       @RequestParam(value = "channel", required = false) Integer channel,
                                       @RequestParam(value = "startDate", required = false) String startDate,
                                       @RequestParam(value = "endDate", required = false) String endDate,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreFundsService.pageFundsByAppId(0L, status, transactionStatus, channel, startDate, endDate, pageNum, pageSize));
    }

}
