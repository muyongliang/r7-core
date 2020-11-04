package com.r7.core.assets.wallet.controller;

import com.r7.core.assets.wallet.dto.CoreWalletExtractionSaveDTO;
import com.r7.core.assets.wallet.dto.CoreWalletExtractionUpdateDTO;
import com.r7.core.assets.wallet.service.CoreWalletExtractionService;
import com.r7.core.assets.wallet.vo.CoreWalletExtractionVO;
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
 * @description: 钱包提现明细接口
 * @date : 2020-10-29
 */
@Slf4j
@Api(value = "/api/extraction", tags = "钱包提现明细接口")
@RestController
@RequestMapping("/extraction")
public class CoreWalletExtractionController {


    @Resource
    private CoreWalletExtractionService coreWalletExtractionService;

    @ApiOperation(value = "新增钱包提现明细", response = Boolean.class)
    @PostMapping("")
    public ResponseEntity saveWalletExtraction(@Valid @RequestBody CoreWalletExtractionSaveDTO coreWalletExtractionSaveDto) {
        return ResponseEntity.success(coreWalletExtractionService
                .saveWalletExtraction(coreWalletExtractionSaveDto, RequestHolder.getAppId(), RequestHolder.getOrganId(), RequestHolder.getUserId()));
    }

    @ApiOperation(value = "根据id修改钱包提现明细", response = CoreWalletExtractionVO.class)
    @PutMapping("/{id}")
    public ResponseEntity updateWalletExtraction(@PathVariable("id") Long id,
                                                 @Valid @RequestBody CoreWalletExtractionUpdateDTO coreWalletExtractionUpdateDto) {
        return ResponseEntity.success(coreWalletExtractionService
                .updateWalletExtractionById(id, coreWalletExtractionUpdateDto, RequestHolder.getAppId(), RequestHolder.getOrganId(), RequestHolder.getUserId()));
    }

    @ApiOperation(value = "根据id获取钱包提现明细", response = CoreWalletExtractionVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getWalletExtraction(@PathVariable("id") Long id) {
        return ResponseEntity.success(coreWalletExtractionService
                .getWalletExtractionById(id));
    }

}
