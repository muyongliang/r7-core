package com.yym.setting.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.yym.setting.common.util.YmIdUtils;
import com.yym.setting.dto.CoreSettingDTO;
import com.yym.setting.mapper.CoreSettingEntityMapper;
import com.yym.setting.model.CoreSettingEntity;
import com.yym.setting.service.CoreSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CoreSettingServiceImpl implements CoreSettingService {

    @Autowired
    private CoreSettingEntityMapper coreSettingEntityMapper;

    @Override
    public Integer insert(CoreSettingDTO coreSettingDTO) {
        Long snowflakeId = YmIdUtils.getSnowflakeId();
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(coreSettingDTO);
        CoreSettingEntity coreSettingEntity = BeanUtil.mapToBean(stringObjectMap, CoreSettingEntity.class, true, new CopyOptions());
        coreSettingEntity.setId(snowflakeId);
        return coreSettingEntityMapper.insertSelective(coreSettingEntity);
    }

}