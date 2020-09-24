package com.yym.setting.controller;

import com.yym.setting.common.annotation.CommonRestResponse;
import com.yym.setting.common.domain.Result;
import com.yym.setting.common.domain.ResultUtil;
import com.yym.setting.common.util.ValidatorUtil;
import com.yym.setting.dto.CoreSettingDTO;
import com.yym.setting.service.CoreSettingService;
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
