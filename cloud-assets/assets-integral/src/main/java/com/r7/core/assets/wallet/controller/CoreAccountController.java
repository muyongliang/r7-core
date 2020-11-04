package com.r7.core.assets.wallet.controller;

import com.r7.core.assets.wallet.dto.CoreAccountDTO;
import com.r7.core.assets.wallet.service.CoreAccountService;
import com.r7.core.assets.wallet.vo.CoreAccountVO;
import com.r7.core.common.web.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zs
 * @description: 用户账户接口
 * @date : 2020-10-29
 */
@Slf4j
@Api(value = "/api/account", tags = "用户账户接口")
@RestController
@RequestMapping("/account")
public class CoreAccountController {

    @Resource
    private CoreAccountService coreAccountService;

    @ApiOperation(value = "新增用户账户", response = Boolean.class)
    @PostMapping("")
    public ResponseEntity saveAccount(@Valid @RequestBody CoreAccountDTO coreAccountDto) {
        return ResponseEntity.success(coreAccountService
                .saveCoreAccount(coreAccountDto, 0L, 0L, 0L));
    }

    @ApiOperation(value = "根据用户id查询用户账户", response = CoreAccountVO.class)
    @GetMapping("/{userId}")
    public ResponseEntity saveAccount(@PathVariable("userId") Long userId) {
        return ResponseEntity.success(coreAccountService.listAccountByUserId(userId));
    }
}
