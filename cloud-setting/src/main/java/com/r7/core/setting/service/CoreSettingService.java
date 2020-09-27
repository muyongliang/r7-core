package com.r7.core.setting.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.setting.dto.CoreSettingDto;
import com.r7.core.setting.model.CoreSetting;
import com.r7.core.setting.vo.CoreSettingVo;

/**
 * @Author muyongliang
 * @Date 2020/9/27 17:18
 * @Description 公共配置模块服务
 */
public interface CoreSettingService extends IService<CoreSetting> {

    /**
     * @param coreSettingDto 新增配置详情
     * @return
     */
    CoreSettingVo saveSetting(CoreSettingDto coreSettingDto, Long userId);

    /**
     * @param id 配置项id
     * @return CoreSettingVo 配置详情
     */
    CoreSettingVo findSettingById(Long id);

    /**
     * @param pageSize 分页大小
     * @param pageNum  当前页
     * @return
     */
    Page<CoreSettingVo> pageSetting(Integer pageSize, Integer pageNum);

    /**
     * @param id             配置项id
     * @param coreSettingDto 需要更新的配置
     * @return
     */
    CoreSettingVo updateSettingById(Long id, CoreSettingDto coreSettingDto, Long userId);
}


