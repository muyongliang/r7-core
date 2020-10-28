package com.r7.core.assets.wallet.controller;

import com.r7.core.assets.wallet.dto.CoreWalletDTO;
import com.r7.core.assets.wallet.dto.CoreWalletUpdateDTO;
import com.r7.core.assets.wallet.service.CoreWalletService;
import com.r7.core.assets.wallet.vo.CoreWalletVO;
import com.r7.core.common.holder.RequestHolder;
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
        return ResponseEntity.success(coreWalletService.saveWallet(coreWalletDto,
                RequestHolder.getAppId(), RequestHolder.getOrganId(), RequestHolder.getUserId()));
    }

    @ApiOperation(
            value = "根据用户id修改钱包数据",
            response = Boolean.class)
    @PutMapping("/{userId}")
    public ResponseEntity updateWalletByUserId(@PathVariable("userId") Long userId,
                                               @Valid @RequestBody CoreWalletUpdateDTO coreWalletUpdateDto) {
        return ResponseEntity.success(coreWalletService.updateWalletByUserId(userId, coreWalletUpdateDto,
                RequestHolder.getAppId(), RequestHolder.getOrganId()));
    }

    @ApiOperation(
            value = "根据用户ID查询用户钱包详情",
            response = CoreWalletVO.class)
    @GetMapping("/{userId}")
    public ResponseEntity getWalletByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.success(coreWalletService.getWalletByUserId(userId));
    }

    @ApiOperation(
            value = "分页查询所有用户钱包信息",
            response = CoreWalletVO.class)
    @GetMapping("/page")
    public ResponseEntity pageWallet(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreWalletService.pageWallet(pageNum, pageSize));
    }

    @ApiOperation(
            value = "查询钱包总余额",
            response = CoreWalletVO.class)
    @GetMapping("/total")
    public ResponseEntity getTotalBalance() {
        return ResponseEntity.success(coreWalletService.getTotalBalance());
    }
}
