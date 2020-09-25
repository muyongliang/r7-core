package com.r7.core.setting.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.setting.common.util.YmIdUtils;
import com.r7.core.setting.mapper.CoreSettingMapper;
import com.r7.core.setting.model.CoreSetting;
import com.r7.core.setting.service.CoreSettingService;
import com.r7.core.setting.vo.CoreSettingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class CoreSettingServiceImpl implements CoreSettingService {

    @Autowired
    private CoreSettingMapper coreSettingMapper;

    @Override
    public Integer addSetting(CoreSettingVO coreSettingVO) {
        Long snowflakeId = YmIdUtils.getSnowflakeId();
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(coreSettingVO);
        CoreSetting coreSetting = BeanUtil.mapToBean(stringObjectMap, CoreSetting.class, true, new CopyOptions());
        coreSetting.setId(snowflakeId);
        coreSetting.setCreatedAt(new Date());
        return coreSettingMapper.insert(coreSetting);
    }

    @Override
    public CoreSettingVO qrySetting(Long id) {
        CoreSetting coreSetting = coreSettingMapper.selectById(id);
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(coreSetting);
        CoreSettingVO coreSettingVO = BeanUtil.mapToBean(stringObjectMap, CoreSettingVO.class, true, new CopyOptions());
        return coreSettingVO;
    }

    @Override
    public Page<CoreSettingVO> qrySetting(Page page) {
        QueryWrapper<CoreSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(new CoreSetting());
        queryWrapper.orderByDesc("id");
        Page selectPage = coreSettingMapper.selectPage(page, queryWrapper);
        return selectPage;
    }

    @Override
    public Integer updateSetting(CoreSettingVO coreSettingVO) {
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(coreSettingVO);
        CoreSetting coreSetting = BeanUtil.mapToBean(stringObjectMap, CoreSetting.class, true, new CopyOptions());
        coreSetting.setUpdatedAt(new Date());
        return coreSettingMapper.updateById(coreSetting);
    }

}
