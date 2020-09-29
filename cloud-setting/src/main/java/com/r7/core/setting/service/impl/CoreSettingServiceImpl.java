package com.r7.core.setting.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.setting.constant.SettingErrorEnum;
import com.r7.core.setting.dto.CoreSettingDto;
import com.r7.core.setting.mapper.CoreSettingMapper;
import com.r7.core.setting.model.CoreSetting;
import com.r7.core.setting.service.CoreSettingService;
import com.r7.core.setting.vo.CoreSettingVo;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author muyongliang
 * @Date 2020/9/27 17:27
 * @Description 公共配置服务实现类
 */
@Slf4j
@Service
public class CoreSettingServiceImpl extends ServiceImpl<CoreSettingMapper, CoreSetting> implements CoreSettingService {

    @Autowired
    private CoreSettingMapper coreSettingMapper;

    @Override
    public CoreSettingVo saveSetting(CoreSettingDto coreSettingDto, Long userId) {
        Long snowflakeId = SnowflakeUtil.getSnowflakeId();
        CoreSetting coreSetting = BeanUtil
                .mapToBean(BeanUtil.beanToMap(coreSettingDto), CoreSetting.class, true, new CopyOptions());
        coreSetting.setId(snowflakeId);
        coreSetting.setCreatedAt(new Date());
        coreSetting.setUpdatedAt(new Date());
        coreSettingMapper.insert(coreSetting);
        CoreSettingVo coreSettingVo = BeanUtil
                .mapToBean(BeanUtil.beanToMap(coreSetting), CoreSettingVo.class, true, new CopyOptions());
        return coreSettingVo;
    }

    @Override
    public CoreSettingVo findSettingById(Long id) {
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(SettingErrorEnum.SETTING_ID_IS_NULL));
        CoreSetting coreSetting = coreSettingMapper.selectById(id);
        if (coreSetting == null) {
            throw new BusinessException(SettingErrorEnum.SETTING_IS_NOT_EXISTS);
        }
        CoreSettingVo coreSettingVO = BeanUtil
                .mapToBean(BeanUtil.beanToMap(coreSetting), CoreSettingVo.class, true, new CopyOptions());
        return coreSettingVO;
    }

    @Override
    public Page<CoreSettingVo> pageSetting(Integer pageSize, Integer pageNum) {
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
    public CoreSettingVo updateSettingById(Long id, CoreSettingDto coreSettingDto, Long userId) {
        Option.of(id)
                .getOrElseThrow(() -> new BusinessException(SettingErrorEnum.SETTING_ID_IS_NULL));
        CoreSetting findById = coreSettingMapper.selectById(id);
        if (findById == null) {
            throw new BusinessException(SettingErrorEnum.SETTING_IS_NOT_EXISTS);
        }
        CoreSetting coreSetting = BeanUtil
                .mapToBean(BeanUtil.beanToMap(coreSettingDto), CoreSetting.class, true, new CopyOptions());
        coreSetting.setId(id);
        coreSetting.setUpdatedAt(new Date());
        coreSettingMapper.updateById(coreSetting);
        CoreSetting coreSetting1 = coreSettingMapper.selectById(id);
        CoreSettingVo coreSettingVo = BeanUtil
                .mapToBean(BeanUtil.beanToMap(coreSetting1), CoreSettingVo.class, true, new CopyOptions());
        return coreSettingVo;
    }

}
