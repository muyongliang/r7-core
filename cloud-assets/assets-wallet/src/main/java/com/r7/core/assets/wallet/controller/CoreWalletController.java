package com.r7.core.assets.wallet.controller;

import com.r7.core.assets.wallet.dto.*;
import com.r7.core.assets.wallet.service.CoreWalletService;
import com.r7.core.assets.wallet.vo.CoreWalletVO;
import com.r7.core.common.web.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zs
 * @description: 核心钱包接口
 * @date : 2020-10-26
 */
@Api(value = "/api/wallet", tags = {"钱包接口"})
@Slf4j
@RestController
@RequestMapping("/wallet")
public class CoreWalletController {


    @Resource
    private CoreWalletService coreWalletService;

    @ApiOperation(
            value = "新增钱包数据",
            response = Boolean.class)
    @PostMapping("/")
    public ResponseEntity saveWallet(@Valid @RequestBody CoreWalletDTO coreWalletDto) {
        return ResponseEntity.success(coreWalletService.saveWallet(coreWalletDto, 0L));
    }

    @ApiOperation(
            value = "根据用户id修改钱包密码",
            response = Boolean.class)
    @PutMapping("/update/{userId}")
    public ResponseEntity updateWalletPayPassword(@PathVariable("userId") Long updateUserId,
                                                  @RequestParam String changePayPassword) {
        return ResponseEntity.success(coreWalletService.updateWalletPayPasswordById(updateUserId, changePayPassword, 0L));
    }

    @ApiOperation(
            value = "根据用户id增加钱包总余额",
            response = CoreWalletVO.class)
    @PutMapping("/add/balance/{updateUserId}")
    public ResponseEntity updateCoreWalletAddBalance(@PathVariable("updateUserId") Long updateUserId,
                                                     @Valid @RequestBody CoreWalletBalanceChangeDTO coreWalletBalanceChangeDto) {
        return ResponseEntity.success(coreWalletService.updateCoreWalletAddBalance(updateUserId, coreWalletBalanceChangeDto, 0L));
    }

    @ApiOperation(
            value = "增加钱包不可用余额",
            response = CoreWalletVO.class)
    @PutMapping("/add/locking/balance/{updateUserId}")
    public ResponseEntity updateCoreWalletAddLockingBalance(@PathVariable("updateUserId") Long updateUserId,
                                                            @Valid @RequestBody CoreWalletLockingBalanceChangeDTO coreWalletLockingBalanceChangeDto) {
        return ResponseEntity.success(coreWalletService.updateCoreWalletAddLockingBalance(updateUserId, coreWalletLockingBalanceChangeDto, 0L));
    }

    @ApiOperation(
            value = "减少钱包总余额",
            response = CoreWalletVO.class)
    @PutMapping("/reduce/balance/{updateUserId}")
    public ResponseEntity updateCoreWalletReduceBalance(@PathVariable("updateUserId") Long updateUserId,
                                                        @Valid @RequestBody CoreWalletBalanceChangeDTO coreWalletBalanceChangeDto) {
        return ResponseEntity.success(coreWalletService.updateCoreWalletReduceBalance(updateUserId, coreWalletBalanceChangeDto, 0L));
    }

    @ApiOperation(
            value = "减少钱包不可用余额",
            response = CoreWalletVO.class)
    @PutMapping("/reduce/locking/balance/{updateUserId}")
    public ResponseEntity updateCoreWalletReduceLockingBalance(@PathVariable("updateUserId") Long updateUserId,
                                                               @Valid @RequestBody CoreWalletLockingBalanceChangeDTO coreWalletLockingBalanceChangeDto) {
        return ResponseEntity.success(coreWalletService.updateCoreWalletReduceLockingBalance(updateUserId, coreWalletLockingBalanceChangeDto, 0L));
    }

    @ApiOperation(
            value = "根据用户ID查询用户钱包详情",
            response = CoreWalletVO.class)
    @GetMapping("/{userId}")
    public ResponseEntity getWalletByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.success(coreWalletService.getWalletByUserId(userId));
    }

    @ApiOperation(
            value = "查询钱包总余额",
            response = Integer.class)
    @GetMapping("/total")
    public ResponseEntity getTotalBalance() {
        return ResponseEntity.success(coreWalletService.getTotalBalance());
    }
}
