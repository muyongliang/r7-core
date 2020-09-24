package com.r7.core.setting.controller;

import com.r7.core.setting.common.annotation.CommonRestResponse;
import com.r7.core.setting.common.domain.Result;
import com.r7.core.setting.common.domain.ResultUtil;
import com.r7.core.setting.common.util.ValidatorUtil;
import com.r7.core.setting.dto.CoreSettingDTO;
import com.r7.core.setting.service.CoreSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import java.util.Set;


@RestController
@RequestMapping("/coreSetting")
@CommonRestResponse
public class CoreSettingController {

    @Autowired
    private CoreSettingService coreSettingService;


    @RequestMapping("/insert")
    public Result insert(CoreSettingDTO coreSettingDTO) {
        Set<ConstraintViolation<CoreSettingDTO>> violationSet = ValidatorUtil.validate(coreSettingDTO);
        if (violationSet.size() != 0) {
            return ResultUtil.systemError(violationSet.iterator().next().getMessage());
        }
        return ResultUtil.success(coreSettingService.insert(coreSettingDTO));
    }


}
