package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.dto.UimResourceSaveDto;
import com.r7.core.uim.dto.UimResourceUpdateDto;
import com.r7.core.uim.model.UimResource;
import com.r7.core.uim.vo.UimResourceNodeVo;
import com.r7.core.uim.vo.UimResourceVo;

import java.util.List;

/**
 * 资源服务
 *
 * @author zhongpingli
 */
public interface UimResourceService extends IService<UimResource> {


    /**
     * 新增资源
     *
     * @param uimResourceSaveDto 新增资源信息
     * @param appId              平台ID
     * @param userId             操作用户ID
     * @return 返回结果
     */
    UimResourceVo saveUimResource(UimResourceSaveDto uimResourceSaveDto, Long appId, Long userId);


    /**
     * 修改资源
     *
     * @param resourceId         修改资源ID
     * @param uimResourceSaveDto 修改资源信息
     * @param userId             操作用户ID
     * @return 返回结果
     */
    UimResourceVo updateUimResource(Long resourceId, UimResourceUpdateDto uimResourceSaveDto, Long userId);


    /**
     * 删除资源
     *
     * @param resourceId 删除资源ID
     * @param userId     操作用户ID
     * @return 返回删除结果
     */
    boolean removeUimResource(Long resourceId, Long userId);


    /**
     * 树形展示资源
     *
     * @param appId 平台ID
     * @param pId   父ID
     * @return 返回结果
     */
    List<UimResourceNodeVo> treeUimResource(Long appId, Long pId);


    /**
     * 根据资源ID查询资源
     *
     * @param resourceId 资源ID
     * @return 返回结果
     */
    UimResourceVo getUimResourceById(Long resourceId);

}
