package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.dto.UimResourceSaveDTO;
import com.r7.core.uim.dto.UimResourceUpdateDTO;
import com.r7.core.uim.model.UimResource;
import com.r7.core.uim.vo.UimResourceNodeVO;
import com.r7.core.uim.vo.UimResourceVO;

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
    UimResourceVO saveUimResource(UimResourceSaveDTO uimResourceSaveDto, Long appId, Long userId);


    /**
     * 修改资源
     *
     * @param resourceId         修改资源ID
     * @param uimResourceSaveDto 修改资源信息
     * @param appId              平台ID
     * @param userId             操作用户ID
     * @return 返回结果
     */
    UimResourceVO updateUimResource(Long resourceId, UimResourceUpdateDTO uimResourceSaveDto, Long appId, Long userId);


    /**
     * 删除资源
     *
     * @param resourceId 删除资源ID
     * @param appId      平台ID
     * @param userId     操作用户ID
     * @return 返回删除结果
     */
    boolean removeUimResource(Long resourceId, Long userId, Long appId);


    /**
     * 树形展示资源
     *
     * @param appId 平台ID
     * @param pId   父ID
     * @return 返回结果
     */
    List<UimResourceNodeVO> treeUimResource(Long appId, Long pId);


    /**
     * 根据资源ID查询资源
     *
     * @param resourceId 资源ID
     * @param appId      平台ID
     * @return 返回结果
     */
    UimResourceVO getUimResourceById(Long resourceId, Long appId);


    /**
     * 根据资源ID查询子资源
     *
     * @param pId 父资源ID
     * @return 返回结果
     */
    List<UimResourceVO> getUimResourceByPid(Long pId);

    /**
     * 根据ID获取资源
     *
     * @param ids   资源ID
     * @param appId 平台ID
     * @return 返回信息
     */
    List<UimResourceVO> listUimResourceByIds(List<Long> ids, Long appId);


    /**
     * 根据资源ID获取资源url
     *
     * @param ids 资源ID
     * @return 返回资源url集合
     */
    List<String> listResourceUrlsByIds(List<Long> ids);
}
