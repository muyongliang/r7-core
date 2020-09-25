package com.r7.core.setting.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.setting.common.enums.CommitErrorEnum;
import com.r7.core.setting.common.util.ValidatorUtil;
import com.r7.core.setting.vo.CoreSettingVO;
import com.r7.core.setting.service.CoreSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import java.util.Set;


@RestController
@RequestMapping("/setting")
public class CoreSettingController {

    @Autowired
    private CoreSettingService coreSettingService;


    @PostMapping
    public ResponseEntity addSetting(@RequestBody CoreSettingVO coreSettingVO) {
        Set<ConstraintViolation<CoreSettingVO>> violationSet = ValidatorUtil.validate(coreSettingVO);
        if (violationSet.size() != 0) {
            return ResponseEntity.failure(CommitErrorEnum.BAD_REQUEST.getCode(), violationSet.iterator().next().getMessage());
        }
        return ResponseEntity.success(coreSettingService.addSetting(coreSettingVO));
    }

    @PutMapping
    public ResponseEntity updateSetting(@RequestBody CoreSettingVO coreSettingVO) {
        Set<ConstraintViolation<CoreSettingVO>> violationSet = ValidatorUtil.validate(coreSettingVO);
        if (violationSet.size() != 0) {
            return ResponseEntity.failure(CommitErrorEnum.BAD_REQUEST.getCode(), violationSet.iterator().next().getMessage());
        }
        return ResponseEntity.success(coreSettingService.updateSetting(coreSettingVO));

    }

    @GetMapping("/setting/{id}")
    public ResponseEntity qrySetting(@PathVariable Long id) {
        return ResponseEntity.success(coreSettingService.qrySetting(id));
    }

    @GetMapping("/settings")
    public ResponseEntity qrySetting(Page page) {
        return ResponseEntity.success(coreSettingService.qrySetting(page));

    }
}
