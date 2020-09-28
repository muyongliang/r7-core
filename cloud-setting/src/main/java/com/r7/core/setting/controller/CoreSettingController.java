package com.r7.core.setting.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.setting.dto.CoreSettingDto;
import com.r7.core.setting.service.CoreSettingService;
import com.r7.core.setting.vo.CoreSettingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author liang
 * @Date 2020/9/27 11:26
 * @Description 公共配置接口
 */
@Api(value = "/api/setting", tags = {"公共配置接口"})
@RestController
@RequestMapping("/setting")
public class CoreSettingController {

    @Autowired
    private CoreSettingService coreSettingService;

    @ApiOperation(
            value = "根据配置id查询配置信息",
            notes = "配置id是必须的",
            response = CoreSettingVo.class)
    @GetMapping("/{id}")
    public ResponseEntity findSettingById(@PathVariable("id") Long id) {
        return ResponseEntity.success(coreSettingService.findSettingById(id));
    }

    @ApiOperation(
            value = "新增配置信息",
            response = Integer.class)
    @PostMapping
    public ResponseEntity saveSetting(@RequestBody @Valid CoreSettingDto coreSettingDto) {

        return ResponseEntity.success(coreSettingService.saveSetting(coreSettingDto, 0L));
    }

    @ApiOperation(
            value = "根据id更新配置信息",
            notes = "配置id是必须的",
            response = Integer.class)
    @PutMapping("/{id}")
    public ResponseEntity updateSettingById(@PathVariable("id") Long id,
                                            @RequestBody @Valid CoreSettingDto coreSettingDto) {
        return ResponseEntity.success(coreSettingService.updateSettingById(id, coreSettingDto, 0L));
    }

    @ApiOperation(
            value = "分页查询所有配置信息",
            notes = "",
            response = CoreSettingVo.class)
    @GetMapping("/page")
    public ResponseEntity pageSetting(@RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return ResponseEntity.success(coreSettingService.pageSetting(pageSize, pageNum));

    }
}
