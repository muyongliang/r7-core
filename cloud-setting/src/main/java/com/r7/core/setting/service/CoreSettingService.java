package com.r7.core.setting.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.setting.dto.CoreSettingDTO;
import com.r7.core.setting.model.CoreSettingDO;
import com.r7.core.setting.vo.CoreSettingVO;

/**
 * @Author muyongliang
 * @Date 2020/9/27 17:18
 * @Description 公共配置模块服务接口
 */
public interface CoreSettingService extends IService<CoreSettingDO> {

    /**
     * @param coreSettingDto 新增配置详情
     * @param userId         操作人id
     * @return 插入后的配置信息
     * @Description 保存配置信息
     */
    CoreSettingVO saveSetting(CoreSettingDTO coreSettingDto, Long userId);

    /**
     * @param id 配置项id
     * @return CoreSettingVo 配置详情
     * @Description 根据id查询配置信息
     */
    CoreSettingVO findSettingById(Long id);

    /**
     * @param pageSize 分页大小
     * @param pageNum  当前页
     * @return 分页查询配置列表
     * @Description 分页查询配置信息
     */
    Page<CoreSettingVO> pageSettings(Integer pageSize, Integer pageNum);

    /**
     * @param id             配置项id
     * @param coreSettingDto 需要更新的配置
     * @param userId         操作人id
     * @return 更新后的配置信息
     * @Description 根据id更新配置信息
     */
    CoreSettingVO updateSettingById(Long id, CoreSettingDTO coreSettingDto, Long userId);
}


