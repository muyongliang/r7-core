package com.r7.core.assets.wallet.controller;

import com.r7.core.assets.wallet.constant.WalletBillStatusEnum;
import com.r7.core.assets.wallet.constant.WalletBillTypeEnum;
import com.r7.core.assets.wallet.dto.CoreWalletBillDTO;
import com.r7.core.assets.wallet.service.CoreWalletBillService;
import com.r7.core.assets.wallet.vo.CoreWalletBillPageVO;
import com.r7.core.assets.wallet.vo.CoreWalletBillVO;
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
                RequestHolder.getAppId(), RequestHolder.getOrganId(), RequestHolder.getUserId()));
    }

    @ApiOperation(value = "修改钱包账单", response = CoreWalletBillVO.class)
    @PutMapping("/{id}")
    public ResponseEntity updateWalletBill(@PathVariable(value = "id") Long id,
                                           @RequestBody CoreWalletBillDTO coreWalletBillDto) {
        return ResponseEntity.success(coreWalletBillService.updateWalletBillById(id, coreWalletBillDto,
                RequestHolder.getAppId(), RequestHolder.getOrganId(), RequestHolder.getUserId()));
    }

    @ApiOperation(value = "根据id查询钱包账单详情", response = CoreWalletBillVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getWalletBill(@PathVariable(value = "id") Long id) {
        return ResponseEntity.success(coreWalletBillService.getWalletBillById(id));
    }

    @ApiOperation(value = "根据用户id和条件分页展示钱包账单", response = CoreWalletBillPageVO.class)
    @GetMapping("/page/{userId}")
    public ResponseEntity pageWalletBillByUserId(@PathVariable(value = "userId") Long userId,
                                                 @RequestParam(value = "type", required = false) WalletBillTypeEnum type,
                                                 @RequestParam(value = "source", required = false) String source,
                                                 @RequestParam(value = "status", required = false) WalletBillStatusEnum status,
                                                 @RequestParam(value = "startDate", required = false) String startDate,
                                                 @RequestParam(value = "endDate", required = false) String endDate,
                                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreWalletBillService.pageWalletBillByUserId(
                userId, type, source, status, startDate, endDate, pageNum, pageSize));
    }


    @ApiOperation(value = "根据平台id和交易来源分页展示钱包账单", response = CoreWalletBillPageVO.class)
    @GetMapping("/page")
    public ResponseEntity pageAppIdWalletBillByType(@RequestParam(value = "type", required = false) WalletBillTypeEnum type,
                                                    @RequestParam(value = "source", required = false) String source,
                                                    @RequestParam(value = "status", required = false) WalletBillStatusEnum status,
                                                    @RequestParam(value = "startDate", required = false) String startDate,
                                                    @RequestParam(value = "endDate", required = false) String endDate,
                                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.success(coreWalletBillService.pageWalletBillByAppId(
                RequestHolder.getAppId(), type, source, status, startDate, endDate, pageNum, pageSize));
    }

    @ApiOperation(value = "查询某段时间平台的用户消费总余额", response = Integer.class)
    @GetMapping("/total")
    public ResponseEntity getTotalConsumptionBalance(@RequestParam(value = "startDate", required = false) String startDate,
                                                     @RequestParam(value = "endDate", required = false) String endDate) {
        return ResponseEntity.success(coreWalletBillService.getTotalConsumptionBalance(RequestHolder.getAppId(), startDate, endDate));
    }
}
