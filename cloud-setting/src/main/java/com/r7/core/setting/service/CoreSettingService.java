package com.r7.core.setting.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.setting.dto.CoreSettingDto;
import com.r7.core.setting.vo.CoreSettingVo;

public interface CoreSettingService {


    Integer addSetting(CoreSettingDto coreSettingDto);

    CoreSettingVo qrySetting(Long id);

    Page<CoreSettingVo> qrySetting(Integer pageSize,Integer pageNum);

    Integer updateSettingById(Long id,CoreSettingDto coreSettingDto);
}


