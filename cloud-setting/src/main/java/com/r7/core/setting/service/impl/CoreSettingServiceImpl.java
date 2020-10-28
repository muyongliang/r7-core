package com.r7.core.setting.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.setting.constant.SettingErrorEnum;
import com.r7.core.setting.dto.CoreSettingDTO;
import com.r7.core.setting.mapper.CoreSettingMapper;
import com.r7.core.setting.model.CoreSettingDO;
import com.r7.core.setting.service.CoreSettingService;
import com.r7.core.setting.vo.CoreSettingVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author muyongliang
 * @date 2020/9/27 17:27
 * @description 公共配置服务实现类
 */
@Slf4j
@Service
public class CoreSettingServiceImpl extends ServiceImpl<CoreSettingMapper, CoreSettingDO> implements CoreSettingService {

    @Autowired
    private CoreSettingMapper coreSettingMapper;

    @Override
    public CoreSettingVO saveSetting(CoreSettingDTO coreSettingDto, Long userId) {
        Long snowflakeId = SnowflakeUtil.getSnowflakeId();
        CoreSettingDO coreSettingDO = BeanUtil
                .mapToBean(BeanUtil.beanToMap(coreSettingDto), CoreSettingDO.class, true, new CopyOptions());
        coreSettingDO.setId(snowflakeId);
        coreSettingDO.setCreatedAt(new Date());
        coreSettingDO.setUpdatedAt(new Date());
        coreSettingMapper.insert(coreSettingDO);
        CoreSettingVO coreSettingVo = BeanUtil
                .mapToBean(BeanUtil.beanToMap(coreSettingDO), CoreSettingVO.class, true, new CopyOptions());
        return coreSettingVo;
    }

    @Override
    public CoreSettingVO findSettingById(Long id) {
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(SettingErrorEnum.SETTING_ID_IS_NULL));
        CoreSettingDO coreSettingDO = coreSettingMapper.selectById(id);
        if (coreSettingDO == null) {
            throw new BusinessException(SettingErrorEnum.SETTING_IS_NOT_EXISTS);
        }
        CoreSettingVO coreSettingVO = BeanUtil
                .mapToBean(BeanUtil.beanToMap(coreSettingDO), CoreSettingVO.class, true, new CopyOptions());
        return coreSettingVO;
    }

    @Override
    public Page<CoreSettingVO> pageSettings(Integer pageSize, Integer pageNum) {
        pageSize = Option.of(pageSize)
                .getOrElseThrow(() -> new BusinessException(SettingErrorEnum.PAGE_SIZE_IS_NULL));
        pageNum = Option.of(pageNum)
                .getOrElseThrow(() -> new BusinessException(SettingErrorEnum.PAGE_NUM_IS_NULL));
        QueryWrapper<CoreSettingDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(new CoreSettingDO());
        queryWrapper.orderByDesc("id");
        Page<CoreSettingDO> coreSettingPage = new Page<>();
        coreSettingPage.setSize(pageSize);
        coreSettingPage.setCurrent(pageNum);
        Page selectPage = coreSettingMapper.selectPage(coreSettingPage, queryWrapper);
        return selectPage;
    }

    @Override
    public CoreSettingVO updateSettingById(Long id, CoreSettingDTO coreSettingDto, Long userId) {
        Option.of(id)
                .getOrElseThrow(() -> new BusinessException(SettingErrorEnum.SETTING_ID_IS_NULL));
        CoreSettingDO findById = coreSettingMapper.selectById(id);
        if (findById == null) {
            throw new BusinessException(SettingErrorEnum.SETTING_IS_NOT_EXISTS);
        }
        CoreSettingDO coreSettingDO = BeanUtil
                .mapToBean(BeanUtil.beanToMap(coreSettingDto), CoreSettingDO.class, true, new CopyOptions());
        coreSettingDO.setId(id);
        coreSettingDO.setUpdatedAt(new Date());
        coreSettingMapper.updateById(coreSettingDO);
        CoreSettingVO coreSettingVo = BeanUtil
                .mapToBean(BeanUtil.beanToMap(coreSettingMapper.selectById(id)), CoreSettingVO.class, true, new CopyOptions());
        return coreSettingVo;
    }

}
