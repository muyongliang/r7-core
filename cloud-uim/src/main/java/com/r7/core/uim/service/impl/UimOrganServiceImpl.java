package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimOrganSaveDTO;
import com.r7.core.uim.dto.UimOrganUpdateDTO;
import com.r7.core.uim.mapper.UimOrganMapper;
import com.r7.core.uim.model.UimOrgan;
import com.r7.core.uim.service.UimOrganService;
import com.r7.core.uim.vo.UimOrganNodeVO;
import com.r7.core.uim.vo.UimOrganVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组织服务实现层
 *
 * @author zhongpingli
 */
@Slf4j
@Service
public class UimOrganServiceImpl extends ServiceImpl<UimOrganMapper, UimOrgan> implements UimOrganService {

    @Override
    @Transactional
    public UimOrganVO saveUimOrganByPid(Long pId, UimOrganSaveDTO uimOrganSaveDTO, Long userId, Long appId) {
        log.info("平台:{}新增组织内容:{},操作用户:{}", appId, uimOrganSaveDTO, userId);
        Long id = SnowflakeUtil.getSnowflakeId();
        UimOrgan uimOrgan = new UimOrgan();
        uimOrgan.setId(id);
        uimOrgan.setPId(pId);
        uimOrgan.setAppId(appId);
        uimOrgan.toUimOrgan(uimOrganSaveDTO);
        uimOrgan.setCreatedAt(new Date());
        uimOrgan.setCreatedBy(userId);
        uimOrgan.setUpdatedAt(new Date());
        uimOrgan.setUpdatedBy(userId);
        boolean save = save(uimOrgan);
        if (!save) {
            log.info("平台:{}新增组织内容:{} 失败,操作用户:{}", appId, uimOrganSaveDTO, userId);
            throw new BusinessException(UimErrorEnum.ORGAN_SAVE_ERROR);
        }
        log.info("平台:{}新增组织内容:{} 成功,操作用户:{}", appId, uimOrganSaveDTO, userId);
        return getUimOrganById(id, appId);
    }

    @Override
    @Transactional
    public Boolean removeUimOrganById(Long id, Long userId, Long appId) {
        log.info("平台:{}删除组织ID:{},操作用户:{}", appId, id, userId);
        UimOrganVO uimOrganVO = getUimOrganById(id, appId);
        boolean remove = removeById(id);
        if (!remove) {
            log.info("平台:{}删除组织:{}失败,操作用户:{}", appId, uimOrganVO, userId);
            throw new BusinessException(UimErrorEnum.ORGAN_DELETE_ERROR);
        }
        log.info("平台:{}删除组织:{}成功,操作用户:{}", appId, uimOrganVO, userId);
        return true;
    }

    @Override
    @Transactional
    public Boolean removeUimOrganByIds(List<Long> ids, Long userId, Long appId) {
        ids.forEach(id -> removeUimOrganById(id, userId, appId));
        return true;
    }

    @Override
    @Transactional
    public UimOrganVO updateUimOrganById(Long id, UimOrganUpdateDTO uimOrganUpdateDTO, Long userId, Long appId) {
        log.info("平台:{}修改组织{}内容:{},操作用户:{}", appId, id, uimOrganUpdateDTO, userId);
        UimOrgan uimOrgan = Option.of(getOne(Wrappers.<UimOrgan>lambdaQuery()
                .eq(UimOrgan::getId, id).eq(UimOrgan::getAppId, appId)))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ORGAN_IS_NOT_EXISTS));
        uimOrgan.toUimOrgan(uimOrganUpdateDTO);
        uimOrgan.setUpdatedAt(new Date());
        uimOrgan.setUpdatedBy(userId);
        boolean update = updateById(uimOrgan);
        if (!update) {
            log.info("平台:{}修改组织{}内容:{}失败,操作用户:{}", appId, id, uimOrganUpdateDTO, userId);
            throw new BusinessException(UimErrorEnum.ORGAN_UPDATE_ERROR);
        }
        log.info("平台:{}修改组织{}内容:{}成功,操作用户:{}", appId, id, uimOrganUpdateDTO, userId);
        return getUimOrganById(id, appId);
    }

    @Override
    public List<UimOrganNodeVO> treeUimOrganNodeByPid(Long pId, Long appId) {
        List<UimOrgan> uimOrgans = Option.of(list(Wrappers.<UimOrgan>lambdaQuery()
                .select(UimOrgan::getId, UimOrgan::getPId, UimOrgan::getOrganCode,
                        UimOrgan::getOrganName, UimOrgan::getType)
                .eq(UimOrgan::getPId, pId).eq(UimOrgan::getAppId, appId).orderByAsc(UimOrgan::getSort)))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ORGAN_IS_NOT_EXISTS));
        ArrayList<UimOrganNodeVO> nodeVOList = Lists.newArrayList();
        treeUimResource(nodeVOList, uimOrgans, 0L);
        return nodeVOList;
    }

    /**
     * 树形展示组织
     *
     * @param uimOrganNodeVOList 树形组织
     * @param uimOrganList       组织数据
     * @param pId                组织父ID
     */
    public void treeUimResource(List<UimOrganNodeVO> uimOrganNodeVOList, List<UimOrgan> uimOrganList, Long pId) {
        uimOrganList.stream().filter(x -> x.getPId().equals(pId)).forEach(x -> {
            UimOrganNodeVO uimOrganNodeVO = x.toUimOrganNodeVO();
            uimOrganNodeVOList.add(uimOrganNodeVO);
            treeUimResource(uimOrganNodeVO.getSubNodes(), uimOrganList, x.getId());
        });
    }

    @Override
    public UimOrganVO getUimOrganById(Long id, Long appId) {
        UimOrgan uimOrgan = Option.of(getOne(Wrappers.<UimOrgan>lambdaQuery()
                .select(UimOrgan::getId, UimOrgan::getPId, UimOrgan::getOrganCode, UimOrgan::getOrganName,
                        UimOrgan::getType, UimOrgan::getSort)
                .eq(UimOrgan::getId, id)
                .eq(UimOrgan::getAppId, appId)))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ORGAN_IS_NOT_EXISTS));
        return uimOrgan.toUimOrganVO();
    }
}
