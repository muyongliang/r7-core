package com.r7.core.setting.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.setting.dto.CoreSettingDto;
import com.r7.core.setting.service.CoreSettingService;
import com.r7.core.setting.vo.CoreSettingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author liang
 * @Date 2020/9/27 11:26
 * @Description 公共配置接口
 */
@Api(value = "/setting", tags = {"公共配置接口"})
@RestController
@RequestMapping
public class CoreSettingController {

    @Autowired
    private CoreSettingService coreSettingService;

    @ApiOperation(
            value = "根据配置id查询配置信息",
            notes = "配置id是必须的",
            response = CoreSettingVo.class)
    @GetMapping("/setting/{id}")
    public ResponseEntity qrySetting(@PathVariable("id") Long id) {
        return ResponseEntity.success(coreSettingService.qrySetting(id));
    }

    @ApiOperation(
            value = "新增配置信息",
            notes = "",
            response = Integer.class)
    @PostMapping("/setting")
    public ResponseEntity addSetting(@RequestBody CoreSettingDto coreSettingDto) {

        return ResponseEntity.success(coreSettingService.addSetting(coreSettingDto));
    }

    @ApiOperation(
            value = "更新配置信息",
            notes = "",
            response = Integer.class)
    @PutMapping("/setting/{id}")
    public ResponseEntity updateSetting(@PathVariable("id") Long id, @RequestBody CoreSettingDto coreSettingDto) {
        return ResponseEntity.success(coreSettingService.updateSettingById(id, coreSettingDto));
    }

    @ApiOperation(
            value = "分页查询所有配置信息",
            notes = "",
            response = IPage.class)
    @GetMapping("/settings")
    public ResponseEntity qrySetting(Integer pageSize, Integer pageNum) {
        return ResponseEntity.success(coreSettingService.qrySetting(pageSize, pageNum));

    }
}
