package com.r7.core.setting.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.setting.common.enums.SettingErrorEnum;
import com.r7.core.setting.common.util.ValidatorUtil;
import com.r7.core.setting.common.util.YmIdUtils;
import com.r7.core.setting.dto.CoreSettingDto;
import com.r7.core.setting.mapper.CoreSettingMapper;
import com.r7.core.setting.model.CoreSetting;
import com.r7.core.setting.service.CoreSettingService;
import com.r7.core.setting.vo.CoreSettingVo;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Service
public class CoreSettingServiceImpl implements CoreSettingService {

    @Autowired
    private CoreSettingMapper coreSettingMapper;

    @Override
    public Integer addSetting(CoreSettingDto coreSettingDto) {
        //       todo 参数校验
        Set<ConstraintViolation<CoreSettingDto>> violationSet = ValidatorUtil.validate(coreSettingDto);
        Long snowflakeId = YmIdUtils.getSnowflakeId();
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(coreSettingDto);
        CoreSetting coreSetting = BeanUtil.mapToBean(stringObjectMap, CoreSetting.class, true, new CopyOptions());
        coreSetting.setId(snowflakeId);
        coreSetting.setCreatedAt(new Date());
        coreSetting.setUpdatedAt(new Date());
        return coreSettingMapper.insert(coreSetting);
    }

    @Override
    public CoreSettingVo qrySetting(Long id) {
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(SettingErrorEnum.SETTING_ID_IS_NULL));
        CoreSetting coreSetting = coreSettingMapper.selectById(id);
        if (coreSetting==null) {
            throw new BusinessException(SettingErrorEnum.SETTING_IS_NOT_EXISTS);
        }
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(coreSetting);
        CoreSettingVo coreSettingVO = BeanUtil.mapToBean(stringObjectMap, CoreSettingVo.class, true, new CopyOptions());
        return coreSettingVO;
    }

    @Override
    public Page<CoreSettingVo> qrySetting(Integer pageSize,Integer pageNum) {
        pageSize = Option.of(pageSize)
                .getOrElseThrow(() -> new BusinessException(SettingErrorEnum.PAGE_SIZE_IS_NULL));
        pageNum = Option.of(pageNum)
                .getOrElseThrow(() -> new BusinessException(SettingErrorEnum.PAGE_NUM_IS_NULL));
        QueryWrapper<CoreSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(new CoreSetting());
        queryWrapper.orderByDesc("id");
        Page<CoreSetting> coreSettingPage = new Page<>();
        coreSettingPage.setSize(pageSize);
        coreSettingPage.setCurrent(pageNum);
        Page selectPage = coreSettingMapper.selectPage(coreSettingPage, queryWrapper);
        return selectPage;
    }

    @Override
    public Integer updateSettingById(Long id,CoreSettingDto coreSettingDto) {
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(SettingErrorEnum.SETTING_ID_IS_NULL));
        //       todo 参数校验
        Set<ConstraintViolation<CoreSettingDto>> violationSet = ValidatorUtil.validate(coreSettingDto);
        CoreSetting qry = coreSettingMapper.selectById(id);
        if (qry==null) {
            throw new BusinessException(SettingErrorEnum.SETTING_IS_NOT_EXISTS);
        }
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(coreSettingDto);
        CoreSetting coreSetting = BeanUtil.mapToBean(stringObjectMap, CoreSetting.class, true, new CopyOptions());
        coreSetting.setId(id);
        coreSetting.setUpdatedAt(new Date());
        return coreSettingMapper.updateById(coreSetting);
    }

}
