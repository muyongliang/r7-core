package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.CorePlatformEnum;
import com.r7.core.uim.dto.CorePlatformDTO;
import com.r7.core.uim.mapper.CorePlatformMapper;
import com.r7.core.uim.model.CorePlatform;
import com.r7.core.uim.service.CorePlatformService;
import com.r7.core.uim.vo.CorePlatformVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author wt
 * @Description
 */
@Slf4j
@Service
public class CorePlatformServiceImpl extends ServiceImpl<CorePlatformMapper, CorePlatform>
        implements CorePlatformService {


    @Override
    public CorePlatformVO saveCorePlatform(CorePlatformDTO corePlatformDTO, Long userId) {

        log.info("新增平台信息：{}，操作者ID：{},新增开始时间：{}",
                corePlatformDTO, userId, LocalDateTime.now());


        //验证平台信息是否已经存在
        if (getCorePlatformByAppName(corePlatformDTO.getAppName()) != null) {
            throw new BusinessException(CorePlatformEnum.CORE_PLATFORM_APP_NAME_IS_EXISTS);
        }


        //生成id
        Long id = SnowflakeUtil.getSnowflakeId();

        CorePlatform corePlatform = new CorePlatform();
        corePlatform.toCorePlatformDto(corePlatformDTO);
        corePlatform.setId(id);
        corePlatform.setCreatedAt(LocalDateTime.now());
        corePlatform.setCreatedBy(userId);
        corePlatform.setUpdatedBy(userId);
        corePlatform.setUpdatedAt(LocalDateTime.now());

        boolean saveCorePlatform = save(corePlatform);

        if (!saveCorePlatform) {
            log.info("新增平台信息失败：{}，操作者ID：{},失败时间：{}",
                    corePlatformDTO, userId, LocalDateTime.now());
            throw new BusinessException(CorePlatformEnum.CORE_PLATFORM_SAVE_ERROR);
        }

        log.info("新增平台信息成功：{}，操作者ID：{},新增结束时间：{}",
                corePlatformDTO, userId, LocalDateTime.now());
        return baseMapper.selectById(id).toCorePlatformVO();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CorePlatformVO updateCorePlatformAppName(Long id, String appName, Long userId) {

        log.info("平台id:{},平台新名称:{},操作者:{}，开始时间:{}",
                id, appName, userId, LocalDateTime.now());

        //验证平台信息是否已经存在,id是否为空，id长度
        if (getCorePlatformById(id) == null) {
            throw new BusinessException(CorePlatformEnum.CORE_PLATFORM_APP_NAME_IS_EXISTS);
        }


        boolean updateCorePlatformAppName = update(Wrappers.<CorePlatform>lambdaUpdate()
                .set(CorePlatform::getAppName, appName)
                .set(CorePlatform::getUpdatedBy, userId)
                .set(CorePlatform::getUpdatedAt, LocalDateTime.now())
                .eq(CorePlatform::getId, id));
        if (!updateCorePlatformAppName) {
            log.info("平台id:{},平台新名称:{},操作者:{}，修改平台名称失败时间:{}",
                    id, appName, userId, LocalDateTime.now());
            throw new BusinessException(CorePlatformEnum.CORE_PLATFORM_APP_NAME_UPDATE_ERROR);

        }
        log.info("平台id:{},平台新名称:{},操作者:{}，结束时间:{}",
                id, appName, userId, LocalDateTime.now());
        return baseMapper.selectById(id).toCorePlatformVO();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CorePlatformVO updateCorePlatformDescription(Long id, String description, Long userId) {

        log.info("平台id:{},平台新描述:{},操作者:{}，开始时间:{}",
                id, description, userId, LocalDateTime.now());
        //id长度，是否存在，是否为空
        if (getCorePlatformById(id) == null) {
            throw new BusinessException(CorePlatformEnum.CORE_PLATFORM_APP_NAME_IS_EXISTS);
        }

        boolean updateCorePlatformDescription = update(Wrappers.<CorePlatform>lambdaUpdate()
                .set(CorePlatform::getDescription, description)
                .set(CorePlatform::getUpdatedAt, LocalDateTime.now())
                .set(CorePlatform::getUpdatedBy, userId)
                .eq(CorePlatform::getId, id));

        if (!updateCorePlatformDescription) {
            log.info("平台id:{},平台新描述:{},操作者:{}，修改平台名称失败时间:{}",
                    id, description, userId, LocalDateTime.now());
            throw new BusinessException(CorePlatformEnum.CORE_PLATFORM_DESCRIPTION_UPDATE_ERROR);

        }
        log.info("平台id:{},平台新描述:{},操作者:{}，结束时间:{}",
                id, description, userId, LocalDateTime.now());

        return baseMapper.selectById(id).toCorePlatformVO();
    }

    @Override
    public CorePlatformVO getCorePlatformById(Long id) {

        checkCorePlatformId(id);

        CorePlatform corePlatform = baseMapper.selectById(id);
        if (corePlatform == null) {
            return null;
        }
        return corePlatform.toCorePlatformVO();

    }

    @Override
    public CorePlatformVO getCorePlatformByAppName(String appName) {
        Option.of(appName).getOrElseThrow(
                () -> new BusinessException(CorePlatformEnum.CORE_PLATFORM_APP_NAME_IS_NOT_NULL));


        CorePlatform corePlatform = baseMapper.selectOne(Wrappers.<CorePlatform>lambdaQuery()
                .select(
                        CorePlatform::getId,
                        CorePlatform::getAppName,
                        CorePlatform::getDescription).eq(CorePlatform::getAppName, appName));
        if (corePlatform == null) {
            return null;
        }
        return corePlatform.toCorePlatformVO();
    }


    @Override
    public Page<CorePlatformVO> pagePlatform(String appName, int pageNum, int pageSize) {

        Page<CorePlatformVO> platformVOPage = baseMapper.pagePlatform(appName, new Page<>(pageNum, pageSize));
        return platformVOPage;
    }

    @Override
    public boolean checkCorePlatformId(Long id) {

        Option.of(id).getOrElseThrow(() -> new BusinessException(CorePlatformEnum.CORE_PLATFORM_ID_IS_NOT_NULL));
        int length = 19;
        int idLength = String.valueOf(id).length();
        if (idLength != length) {
            throw new BusinessException(CorePlatformEnum.CORE_PLATFORM_ID_LENGTH_ERROR);
        }
        return true;
    }
}
