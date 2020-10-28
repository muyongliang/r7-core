package com.r7.core.assets.controller;

import com.r7.core.assets.dto.CoreWalletBillDTO;
import com.r7.core.assets.service.CoreWalletBillService;
import com.r7.core.common.web.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zs
 * @description: 钱包账单接口
 * @date : 2020-10-26
 */
@Api(value = "/api/wallet/bill", tags = {"钱包账单接口"})
@Slf4j
@RestController
@RequestMapping("/wallet/bill")
public class CoreWalletBillController {

    @Resource
    private CoreWalletBillService coreWalletBillService;

    @ApiOperation(value = "新增钱包账单")
    @PostMapping("")
    public ResponseEntity saveWalletBill(@Valid @RequestBody CoreWalletBillDTO coreWalletBillDto) {
        return ResponseEntity.success(coreWalletBillService.saveWalletBill(coreWalletBillDto,
                0L, 0L, 0L));
    }

    @ApiOperation(value = "修改钱包账单")
    @PutMapping("/{id}")
    public ResponseEntity updateWalletBill(@PathVariable(value = "id") Long id,
                                           @RequestBody CoreWalletBillDTO coreWalletBillDto) {
        return ResponseEntity.success(coreWalletBillService.updateWalletBillById(id, coreWalletBillDto,
                0L, 0L, 0L));
    }

    @ApiOperation(value = "根据id查询钱包账单详情")
    @GetMapping("/{id}")
    public ResponseEntity getWalletBill(@PathVariable(value = "id") Long id) {
        return ResponseEntity.success(coreWalletBillService.getWalletBillById(id));
    }

    @ApiOperation(value = "根据用户id分页展示钱包账单")
    @GetMapping("/page/{userId}")
    public ResponseEntity pageWalletBill(@PathVariable(value = "userId") Long userId,
                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreWalletBillService.pageWalletBill(userId, pageNum, pageSize));
    }

}
