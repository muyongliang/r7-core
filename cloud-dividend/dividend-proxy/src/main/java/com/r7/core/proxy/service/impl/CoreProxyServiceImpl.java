package com.r7.core.proxy.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.proxy.constant.CoreProxyEnum;
import com.r7.core.proxy.dto.CoreProxyDTO;
import com.r7.core.proxy.dto.CoreProxyUpdateDTO;
import com.r7.core.proxy.mapper.CoreProxyMapper;
import com.r7.core.proxy.model.CoreProxy;
import com.r7.core.proxy.service.CoreProxyService;
import com.r7.core.proxy.vo.CoreProxyNodeVO;
import com.r7.core.proxy.vo.CoreProxyVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author wutao
 * @Description 代理层级实现类
 */
@Slf4j
@Service
public class CoreProxyServiceImpl extends ServiceImpl<CoreProxyMapper, CoreProxy> implements CoreProxyService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreProxyVO saveCoreProxy(CoreProxyDTO coreProxyDto, Long optionalUserId) {
        log.info("新增层级信息内容:{},操作人id:{}，开始时间：{}"
                , coreProxyDto, optionalUserId, LocalDateTime.now());

        //对各种id长度进行验证
        checkIdLength(coreProxyDto.getPId());
        checkIdLength(coreProxyDto.getUserId());
        checkIdLength(coreProxyDto.getOrganId());
        // todo 判断用户父id是否存在，用户id是否存在，组织id是否存在
        CoreProxy coreProxy = new CoreProxy();
        //新增层级的层级值默认为1
        if (coreProxyDto.getLevel() == null) {
            coreProxyDto.setLevel(1);
        }
        coreProxy.setId(SnowflakeUtil.getSnowflakeId());
        coreProxy.setCreatedBy(optionalUserId);
        coreProxy.setCreatedAt(LocalDateTime.now());
        coreProxy.setUpdatedBy(optionalUserId);
        coreProxy.setUpdatedAt(LocalDateTime.now());
        coreProxy.toCoreProxyDto(coreProxyDto);
        //执行层级信息添加操作
        boolean save = save(coreProxy);
        //判断是否添加成功
        if (!save) {
            //操作失败记录添加
            log.info("新增层级信息失败内容:{},操作人id:{}，时间：{}", coreProxyDto, optionalUserId, LocalDateTime.now());
            throw new BusinessException(CoreProxyEnum.CORE_PROXY_SAVE_ERROR);
        }

        //重新计算下级人数及层级值
        updateAndCountCoreProxyLevelByOrganId(coreProxyDto.getOrganId(), optionalUserId);

        //操作成功记录添加
        log.info("新增层级信息内容:{},操作人id:{}，结束时间：{}"
                , coreProxyDto, optionalUserId, LocalDateTime.now());
        return coreProxy.toCoreProxyVo();
    }

    @Override
    public CoreProxyVO getCoreProxyById(Long id, Long organId) {

        log.info("层级信息id:{}，组织id：{}，查询开始时间：{}"
                , id, organId, LocalDateTime.now());

        Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreProxyEnum.CORE_PROXY_ID_IS_NOT_NULL));

        checkIdLength(id);

        CoreProxy coreProxy = Option.of(getOne(Wrappers.<CoreProxy>lambdaQuery()
                .select(CoreProxy::getId, CoreProxy::getPId, CoreProxy::getOrganId, CoreProxy::getUserId,
                        CoreProxy::getLevel, CoreProxy::getType, CoreProxy::getSubordinateNum)
                .eq(CoreProxy::getId, id).eq(CoreProxy::getOrganId, organId)))
                .getOrElseThrow(() -> new BusinessException(CoreProxyEnum.CORE_PROXY_ID_IS_NOT_EXISTS));
        log.info("层级信息id:{}，查询结束时间：{}"
                , id, LocalDateTime.now());
        return coreProxy.toCoreProxyVo();
    }

    @Override
    public CoreProxyVO getCoreProxyByUserId(Long userId, Long organId) {


        log.info("用户id:{}，查询开始时间：{}"
                , userId, LocalDateTime.now());
        Option.of(userId)
                .getOrElseThrow(() -> new BusinessException(CoreProxyEnum.CORE_PROXY_USER_ID_IS_NULL));
        checkIdLength(userId);
        CoreProxy coreProxy = Option.of(getOne(Wrappers.<CoreProxy>lambdaQuery()
                .select(CoreProxy::getId, CoreProxy::getPId,
                        CoreProxy::getOrganId, CoreProxy::getUserId,
                        CoreProxy::getLevel, CoreProxy::getType,
                        CoreProxy::getSubordinateNum)
                .eq(CoreProxy::getOrganId, organId).eq(CoreProxy::getUserId, userId)))
                .getOrElseThrow(() -> new BusinessException(CoreProxyEnum.CORE_PROXY_ID_IS_NOT_EXISTS));

        log.info("用户id:{}，查询结束时间：{}"
                , userId, LocalDateTime.now());
        return coreProxy.toCoreProxyVo();
    }

    @Override
    public List<CoreProxyVO> getCoreProxyByParentId(Long parentId) {

        log.info("用户id:{}，查询开始时间：{}"
                , parentId, LocalDateTime.now());
        Option.of(parentId)
                .getOrElseThrow(() -> new BusinessException(CoreProxyEnum.CORE_PROXY_USER_ID_IS_NULL));

        checkIdLength(parentId);

        List<CoreProxy> list = Option.of(list(Wrappers.<CoreProxy>lambdaQuery()
                .select(CoreProxy::getId, CoreProxy::getPId,
                        CoreProxy::getOrganId, CoreProxy::getUserId,
                        CoreProxy::getLevel, CoreProxy::getType,
                        CoreProxy::getSubordinateNum)
                .eq(CoreProxy::getPId, parentId)))
                .getOrElseThrow(() -> new BusinessException(CoreProxyEnum.CORE_PROXY_ID_IS_NOT_EXISTS));

        List<CoreProxyVO> listCoreProxyVO = list.stream().map(CoreProxy::toCoreProxyVo).collect(Collectors.toList());

        log.info("用户id:{}，查询结束时间：{}"
                , parentId, LocalDateTime.now());
        return listCoreProxyVO;
    }


    @Override
    public int countSubordinateNumByUserId(Long userId, Long organId) {
        checkIdLength(userId);
        //统计指定用户的下级人数
        List<CoreProxyVO> arrayList = getAllSubordinateCoreProxy(userId, organId);
        return arrayList.size();
    }

    /**
     * 遍历子节点
     *
     * @param arrayList 子节点存放位置
     * @param list      指定组织下的所有层级资源
     * @param pId       层级父id
     */
    public void recursion(List<CoreProxyVO> arrayList, List<CoreProxy> list, Long pId) {
        list.stream().filter(x -> x.getPId().equals(pId)).forEach(x -> {
            CoreProxyVO resourceNodeVo = x.toCoreProxyVo();
            arrayList.add(resourceNodeVo);
            recursion(arrayList, list, x.getUserId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreProxyVO updateSubordinateNumById(Long userId, Integer subordinateNum, Long organId, Long optionalUserId) {
        log.info("用户id:{}，修改内容：{}，操作者：{}，开始修改时间：{}"
                , userId, subordinateNum, optionalUserId, LocalDateTime.now());
        checkIdLength(userId);
        //todo 用户是否存在
        boolean updateSubordinateNum = update(Wrappers.<CoreProxy>lambdaUpdate()
                .set(CoreProxy::getSubordinateNum, subordinateNum)
                .set(CoreProxy::getUpdatedAt, LocalDateTime.now())
                .set(CoreProxy::getUpdatedBy, optionalUserId)
                .eq(CoreProxy::getOrganId, organId)
                .eq(CoreProxy::getUserId, userId));
        if (!updateSubordinateNum) {
            log.info("用户id:{}，修改失败内容：{}，操作者：{}，修改失败时间：{}"
                    , userId, subordinateNum, optionalUserId, LocalDateTime.now());
            throw new BusinessException(CoreProxyEnum.CORE_PROXY_UPDATE_SUBORDINATE_ERROR);
        }
        CoreProxyVO coreProxyVo = Option.of(getCoreProxyByUserId(userId, organId))
                .getOrElseThrow(() -> new BusinessException(CoreProxyEnum.CORE_PROXY_ID_IS_NOT_EXISTS));

        log.info("用户id:{}，修改内容：{}，操作者：{}，修改结束时间：{}"
                , userId, subordinateNum, optionalUserId, LocalDateTime.now());
        return coreProxyVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreProxyVO updateCoreProxyPidById(Long id, Long userId, Long organId, Long optionalUserId) {
        log.info("层级id:{}，修改内容：{}，操作者：{}，修改开始时间：{}"
                , id, userId, optionalUserId, LocalDateTime.now());
        //验证层级id不能为空
        Option.of(id).getOrElseThrow(() -> new BusinessException(CoreProxyEnum.CORE_PROXY_ID_IS_NOT_NULL));
        checkIdLength(id);
        checkIdLength(userId);
        //判断指定层级是否存在
        Option.of(getCoreProxyByUserId(userId, organId))
                .getOrElseThrow(() -> new BusinessException(CoreProxyEnum.CORE_PROXY_ID_IS_NOT_EXISTS));

        boolean updateCoreProxyPid = update(Wrappers.<CoreProxy>lambdaUpdate()
                .set(CoreProxy::getPId, userId)
                .set(CoreProxy::getUpdatedAt, LocalDateTime.now())
                .set(CoreProxy::getUpdatedBy, optionalUserId)
                .eq(CoreProxy::getId, id).eq(CoreProxy::getOrganId, organId));
        if (!updateCoreProxyPid) {
            log.info("层级id:{}，修改失败内容：{}，操作者：{}，修改失败时间：{}"
                    , id, userId, optionalUserId, LocalDateTime.now());
            throw new BusinessException(CoreProxyEnum.CORE_PROXY_UPDATE_PID_ERROR);
        }

        log.info("层级id:{}，修改内容：{}，操作者：{}，修改结束时间：{}"
                , id, userId, optionalUserId, LocalDateTime.now());

        return baseMapper.selectById(id).toCoreProxyVo();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreProxyVO updateCoreProxy(Long id, CoreProxyUpdateDTO coreProxyUpdateDto, Long optionalUserId) {
        log.info("层级id:{}，修改内容：{}，操作者：{}，修改开始时间：{}"
                , id, coreProxyUpdateDto, optionalUserId, LocalDateTime.now());

        Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreProxyEnum.CORE_PROXY_ID_IS_NOT_NULL));
        checkIdLength(id);
        checkIdLength(coreProxyUpdateDto.getOrganId());

        //验证层级是否存在
        CoreProxy coreProxy = Option.of(getOne(Wrappers.<CoreProxy>lambdaQuery().eq(CoreProxy::getId, id)))
                .getOrElseThrow(() -> new BusinessException(CoreProxyEnum.CORE_PROXY_ID_IS_NOT_EXISTS));

        coreProxy.toCoreProxyUpdateDTO(coreProxyUpdateDto);
        coreProxy.setUpdatedBy(optionalUserId);
        coreProxy.setUpdatedAt(LocalDateTime.now());

        boolean updateCoreProxy = updateById(coreProxy);
        if (!updateCoreProxy) {
            log.info("层级id:{}，修改失败内容：{}，操作者：{}，修改失败时间：{}"
                    , id, coreProxyUpdateDto, optionalUserId, LocalDateTime.now());
            throw new BusinessException(CoreProxyEnum.CORE_PROXY_UPDATE_ERROR);
        }
        log.info("层级id:{}，修改内容：{}，操作者：{}，修改结束时间：{}"
                , id, coreProxyUpdateDto, optionalUserId, LocalDateTime.now());

        return baseMapper.selectById(id).toCoreProxyVo();
    }

    @Override
    public List<CoreProxyNodeVO> treeCoreProxyByPid(Long pId, Long organId) {
        log.info("层级父id:{}，树形查询开始时间：{}"
                , pId, LocalDateTime.now());
        checkIdLength(pId);
        //查询出某个组织下所有的层级信息
        List<CoreProxy> proxies = getAllCountCoreProxyByOrganId(organId);
        List<CoreProxyNodeVO> arrayList = Lists.newArrayList();
        treeCoreProxy(arrayList, proxies, pId);
        log.info("层级父id:{}，树形查询结束时间：{}"
                , pId, LocalDateTime.now());
        return arrayList;
    }


    public void treeCoreProxy(List<CoreProxyNodeVO> coreProxyNodeVos, List<CoreProxy> coreProxies, Long pId) {
        coreProxies.stream().filter(x -> x.getPId().equals(pId)).forEach(x -> {
            CoreProxyNodeVO coreProxyNodeVO = x.toCoreProxyNodeVO();
            coreProxyNodeVos.add(coreProxyNodeVO);
            treeCoreProxy(coreProxyNodeVO.getList(), coreProxies, x.getUserId());
        });
    }

    @Override
    public int countLevel(Long userId, Long organId) {

        checkIdLength(userId);
        //1、把该节点的所有下级的层级放在一个集合里
        List<CoreProxyVO> arrayList = getAllSubordinateCoreProxy(userId, organId);

        //2、根据层级值去重
        //3、计算节点的层级值
        List<CoreProxyVO> listVo = arrayList.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(o -> o.getLevel()))), ArrayList::new)
        );

        return listVo.size() + 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreProxyVO updateCoreProxyLevelById(Long id, Integer level, Long organId, Long optionalUserId) {
        log.info("层级id:{}，修改内容：{}，操作者：{}，修改开始时间：{}"
                , id, level, optionalUserId, LocalDateTime.now());

        checkIdLength(id);

        //验证代理层级是否存在
        Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(CoreProxyEnum.CORE_PROXY_ID_IS_NOT_EXISTS));


        boolean updateSubordinateNum = update(Wrappers.<CoreProxy>lambdaUpdate()
                .set(CoreProxy::getLevel, level)
                .set(CoreProxy::getUpdatedBy, optionalUserId)
                .set(CoreProxy::getUpdatedAt, LocalDateTime.now())
                .eq(CoreProxy::getId, id).eq(CoreProxy::getOrganId, organId));
        if (!updateSubordinateNum) {
            log.info("层级id:{}，修改失败内容：{}，操作者：{}，修改失败时间：{}"
                    , id, level, optionalUserId, LocalDateTime.now());
            throw new BusinessException(CoreProxyEnum.CORE_PROXY_UPDATE_LEVEL_ERROR);
        }

        log.info("层级id:{}，修改内容：{}，操作者：{}，修改结束时间：{}"
                , id, level, optionalUserId, LocalDateTime.now());
        return baseMapper.selectById(id).toCoreProxyVo();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<CoreProxyVO> updateAndCountCoreProxyLevelByOrganId(Long organId, Long optionalUserId) {

        log.info("组织id：{}，操作者：{}，开始时间：{}", organId, optionalUserId, LocalDateTime.now());

        checkIdLength(organId);

        //查询出指定组织下的所有层级
        List<CoreProxy> list = getAllCountCoreProxyByOrganId(organId);
        //计算层级及下级人数并修改
        for (CoreProxy coreProxy :
                list) {
            int countLevel = countLevel(coreProxy.getUserId(), organId);
            updateCoreProxyLevelById(coreProxy.getId(), countLevel, organId, optionalUserId);
            int countSubordinateNum = countSubordinateNumByUserId(coreProxy.getUserId(), organId);
            updateSubordinateNumById(coreProxy.getUserId(), countSubordinateNum, organId, optionalUserId);
        }

        List<CoreProxy> coreProxyList = getAllCountCoreProxyByOrganId(organId);
        List<CoreProxyVO> listCoreProxyVO = coreProxyList.stream().map(CoreProxy::toCoreProxyVo)
                .collect(Collectors.toList());

        log.info("组织id：{}，操作者：{}，结束时间：{}", organId, optionalUserId, LocalDateTime.now());
        return listCoreProxyVO;
    }

    @Override
    public List<CoreProxy> getAllCountCoreProxyByOrganId(Long organId) {

        checkIdLength(organId);

        List<CoreProxy> list = list(Wrappers.<CoreProxy>lambdaQuery()
                .select(CoreProxy::getId, CoreProxy::getPId,
                        CoreProxy::getOrganId, CoreProxy::getUserId,
                        CoreProxy::getLevel, CoreProxy::getType,
                        CoreProxy::getSubordinateNum)
                .eq(CoreProxy::getOrganId, organId).orderByAsc(CoreProxy::getLevel));
        return list;
    }


    @Override
    public List<CoreProxyNodeVO> updateCoreProxyLevel(Long id, Long userId, Long organId, Long optionalUserId) {
        log.info("层级id：{}，操作参数：{}，操作者：{}，开始时间：{}",
                id, userId, optionalUserId, LocalDateTime.now());
        checkIdLength(id);
        //验证层级信息是否存在
        getCoreProxyById(id, organId);
        checkIdLength(userId);

        //判断目标层级是不是要移动自己层级的下级，如果是不允许修改
        boolean isExist = checkCoreProxyIsExistList(id, userId, organId);
        if (isExist) {
            throw new BusinessException(CoreProxyEnum.CORE_PROXY_SUB_IS_EXISTS);
        }

        //1、先把层级的父id修改成userId
        updateCoreProxyPidById(id, userId, organId, optionalUserId);
        //2、重新计算该组织下的所有子节点的层级值 及重新计算节点的下级人数
        updateAndCountCoreProxyLevelByOrganId(organId, optionalUserId);

        //3、树形展示该组织下的所有层级
        List<CoreProxyNodeVO> coreProxyNodeVOList = treeCoreProxyByPid(0L, organId);

        log.info("层级id：{}，操作参数：{}，操作者：{}，结束时间：{}",
                id, userId, optionalUserId, LocalDateTime.now());

        return coreProxyNodeVOList;
    }

    @Override
    public List<CoreProxyVO> getAllSubordinateCoreProxy(Long userId, Long organId) {

        checkIdLength(userId);
        checkIdLength(organId);

        List<CoreProxyVO> arrayList = Lists.newArrayList();
        List<CoreProxy> list = getAllCountCoreProxyByOrganId(organId);
        recursion(arrayList, list, userId);
        return arrayList;
    }

    @Override
    public boolean checkCoreProxyIsExistList(Long id, Long userId, Long organId) {

        checkIdLength(id);
        checkIdLength(userId);
        checkIdLength(organId);

        List<CoreProxyVO> list = getAllSubordinateCoreProxy(baseMapper.selectById(id).getUserId(), organId);

        boolean isExist = list.stream()
                .filter(x -> x.getUserId().equals(userId)).findAny().isPresent();
        return isExist;
    }

    @Override
    public boolean checkIdLength(Long id) {
        int length = 19;
        if (String.valueOf(id).length() != length) {
            throw new BusinessException(CoreProxyEnum.CORE_ID_LENGTH_ERROR);
        }
        return true;
    }
}
