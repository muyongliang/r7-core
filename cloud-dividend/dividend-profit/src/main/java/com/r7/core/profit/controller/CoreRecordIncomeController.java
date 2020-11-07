package com.r7.core.profit.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.profit.constant.IncomeEnum;
import com.r7.core.profit.dto.CoreRecordIncomeDTO;
import com.r7.core.profit.model.CoreRecordIncome;
import com.r7.core.profit.service.CoreRecordIncomeService;
import com.r7.core.profit.vo.CoreRecordIncomeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * @author wutao
 * @Description 发放记录明细接口
 */
@Slf4j
@Api(value = "/api/record/income", tags = {"发放记录明细接口"})
@RestController
@RequestMapping("/record/income")
public class CoreRecordIncomeController {

    @Resource
    private CoreRecordIncomeService coreRecordIncomeService;


    @ApiOperation(value = "根据id查询发放记录明细", response = CoreRecordIncomeVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getCoreRecordIncomeById(@PathVariable("id") Long id,
                                                  @RequestParam Long appId) {
        return ResponseEntity.success(coreRecordIncomeService.getCoreRecordIncomeById(id, appId));
    }


    @ApiOperation(value = "根据条件分页查询", response = CoreRecordIncomeVO.class)
    @GetMapping("/page")
    public ResponseEntity pageCoreProfit(
            @RequestParam(required = false) IncomeEnum status,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long appId,
            @RequestParam(defaultValue = "1", required = false) Integer pageNum,
            @RequestParam(defaultValue = "2", required = false) Integer pageSize) {
        return ResponseEntity.success(coreRecordIncomeService.pageCoreRecordIncome(status,
                userId, appId, pageNum, pageSize));

    }

    @ApiOperation(value = "查询各个平台成功发放的总金额", response = Integer.class)
    @GetMapping("/total/amount")
    public ResponseEntity countTotalAmountByAppId(
            @RequestParam Long appId) {
        return ResponseEntity.success(coreRecordIncomeService.countTotalAmountByAppId(appId));

    }

    @ApiOperation(value = "查询各个平台成功发放的总积分", response = Integer.class)
    @GetMapping("/total/integral")
    public ResponseEntity countTotalIntegralByAppId(
            @RequestParam Long appId) {
        return ResponseEntity.success(coreRecordIncomeService.countTotalIntegralByAppId(appId));

    }

    //  ======以下接口是测试用============

    @ApiOperation(value = "添加发放明细", response = CoreRecordIncomeVO.class)
    @PostMapping("/{id}")
    public ResponseEntity saveUimResource(@PathVariable("id") Long id,
                                          @Valid @RequestBody CoreRecordIncomeDTO coreRecordIncomeDTO) {
        return ResponseEntity.success(coreRecordIncomeService.saveCoreRecordIncome(id, coreRecordIncomeDTO,
                2L));
    }

    @ApiOperation(value = "根据发放记录ID修改发放记录明细的状态为已发放", response = CoreRecordIncomeVO.class)
    @PutMapping("/status")
    public ResponseEntity updateCoreRecordIncomeStatusById(@RequestParam Long id,
                                                           @RequestParam Long appId,
                                                           @RequestParam IncomeEnum status) {
        return ResponseEntity.success(coreRecordIncomeService.updateCoreRecordIncomeStatusById(
                id, appId, status, LocalDateTime.now(), "已经发放",
                Integer.valueOf(new SimpleDateFormat("yyyyMMdd")
                        .format(System.currentTimeMillis())), 9L
        ));
    }

    @ApiOperation(value = "根据发放状态把发放记录明细查询出来", response = CoreRecordIncome.class)
    @GetMapping("/status")
    public ResponseEntity getAllCoreRecordIncomeByStatus(@RequestParam IncomeEnum status) {
        return ResponseEntity.success(coreRecordIncomeService.getAllCoreRecordIncomeByStatus(status));
    }

//  ======以下接口提供给前端用============

    @ApiOperation(value = "统计用户发放的总金额", response = Integer.class)
    @GetMapping("/count/amount")
    public ResponseEntity countTotalAmountByUserId(@RequestParam Long userId) {
        return ResponseEntity.success(coreRecordIncomeService.countTotalAmountByUserId(userId));

    }


    @ApiOperation(value = "统计用户发放的总积分", response = Integer.class)
    @GetMapping("/count/integral")
    public ResponseEntity countTotalIntegralByUserId(@RequestParam Long userId) {
        return ResponseEntity.success(coreRecordIncomeService.countTotalIntegralByUserId(userId));

    }

    @ApiOperation(value = "分页展示用户成功发放积分的发放记录", response = CoreRecordIncomeVO.class)
    @GetMapping("/page/integral")
    public ResponseEntity getCoreRecordIncomeIntegralByUserId(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1", required = false) Integer pageNum,
            @RequestParam(defaultValue = "2", required = false) Integer pageSize) {
        return ResponseEntity.success(coreRecordIncomeService.getCoreRecordIncomeIntegralByUserId(userId,
                pageNum, pageSize));

    }

    @ApiOperation(value = "分页展示用户成功发放金额的发放记录", response = CoreRecordIncomeVO.class)
    @GetMapping("/page/amount")
    public ResponseEntity getCoreRecordIncomeAmountByUserId(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1", required = false) Integer pageNum,
            @RequestParam(defaultValue = "2", required = false) Integer pageSize) {
        return ResponseEntity.success(coreRecordIncomeService.getCoreRecordIncomeAmountByUserId(userId,
                pageNum, pageSize));

    }


}
