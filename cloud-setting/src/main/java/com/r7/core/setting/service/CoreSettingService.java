package com.r7.core.setting.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.setting.vo.CoreSettingVO;

public interface CoreSettingService {


    Integer addSetting(CoreSettingVO coreSettingVO);

    CoreSettingVO qrySetting(Long id);

    Page<CoreSettingVO> qrySetting(Page page);

    Integer updateSetting(CoreSettingVO coreSettingVO);
}


