package com.r7.core.setting.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.setting.common.enums.CommitErrorEnum;
import com.r7.core.setting.common.util.ValidatorUtil;
import com.r7.core.setting.vo.CoreSettingVO;
import com.r7.core.setting.service.CoreSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import java.util.Set;


@RestController
@RequestMapping("/setting")
public class CoreSettingController {

    @Autowired
    private CoreSettingService coreSettingService;


    @PostMapping("/add")
    public ResponseEntity addSetting(CoreSettingVO coreSettingVO) {
        Set<ConstraintViolation<CoreSettingVO>> violationSet = ValidatorUtil.validate(coreSettingVO);
        if (violationSet.size() != 0) {
            return ResponseEntity.failure(CommitErrorEnum.ARGUMENT_NOT_VALID.getCode(),violationSet.iterator().next().getMessage());
        }
        return ResponseEntity.success(coreSettingService.insert(coreSettingVO));
    }


}
