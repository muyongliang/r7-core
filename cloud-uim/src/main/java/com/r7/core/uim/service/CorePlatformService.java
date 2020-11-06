package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.uim.dto.CorePlatformDTO;
import com.r7.core.uim.model.CorePlatform;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.vo.CorePlatformVO;

/**
 * 
 * @Description 
 * @author wt
 * 
 */
public interface CorePlatformService extends IService<CorePlatform>{

        /**
         * 新增平台信息
         * @param corePlatformDTO 平台信息
         * @param userId 操作者
         * @return 返回新增后的信息
         */
        CorePlatformVO saveCorePlatform(CorePlatformDTO corePlatformDTO,Long userId);


        /**
         * 根据平台id修改平台名称
         * @param id 平台第
         * @param appName 平台新的名称
         * @param userId 操作者
         * @return 平台修改后的信息
         */
        CorePlatformVO updateCorePlatformAppName(Long id ,String appName,Long userId);



        /**
         * 根据平台id修改平台描述
         * @param id 平台id
         * @param description 平台的新描述
         * @param userId 操作者
         * @return 返回修改后的信息
         */
        CorePlatformVO updateCorePlatformDescription(Long id ,String description,Long userId);

        /**
         * 根据id查询平台详情
         * @param id 平台id
         * @return 平台信息
         */
        CorePlatformVO getCorePlatformById(Long id);

        /**
         * 根据平台名称查询平台信息
         * @param appName 平台名称
         * @return 平台信息
         */
        CorePlatformVO getCorePlatformByAppName(String appName);

        /**
         * 分页查询平台信息
         * @param appName 平台名称
         * @return 平台信息
         */
        Page<CorePlatformVO> pagePlatform(String appName,int pageNum, int pageSize);

        /**
         * 对平台id是否为空以及长度进行验证
         * @param id 平台id
         * @return 验证结果
         */
        boolean checkCorePlatformId(Long id);

}
