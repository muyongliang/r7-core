package com.r7.core.integral.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.integral.constant.CoreIntegralEnum;
import com.r7.core.integral.constant.OperateTypeEnum;
import com.r7.core.integral.constant.RsaEnum;
import com.r7.core.integral.dto.CoreIntegralChangeDTO;
import com.r7.core.integral.dto.CoreIntegralDTO;
import com.r7.core.integral.dto.CoreIntegralDetailDTO;
import com.r7.core.integral.mapper.CoreIntegralMapper;
import com.r7.core.integral.model.CoreIntegral;
import com.r7.core.integral.service.CoreIntegralDetailService;
import com.r7.core.integral.service.CoreIntegralService;
import com.r7.core.integral.util.RsaUtil;
import com.r7.core.integral.vo.CoreIntegralVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author wt
 * @Description 当前用户积分实现类
 */
@Slf4j
@Service
public class CoreIntegralServiceImpl extends ServiceImpl<CoreIntegralMapper, CoreIntegral>
        implements CoreIntegralService {

    @Resource
    private CoreIntegralDetailService coreIntegralDetailService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreIntegralVO saveCoreIntegral(CoreIntegralDTO coreIntegralDTO, Long userId) {
        log.info("新增用户积分信息:{},操作者：{}，开始时间：{}", coreIntegralDTO, userId, LocalDateTime.now());

        //对dto中的用户id进行长度验证
        int length = 19;
        if (coreIntegralDTO.getUserId() != length) {
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_LENGTH_ERROR);
        }
        //验证当前用户积分信息是否已经存在，存在抛出异常
        if (getCoreIntegralByUserId(coreIntegralDTO.getUserId()) != null) {
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_IS_EXISTS);
        }

        Long id = SnowflakeUtil.getSnowflakeId();
        CoreIntegral coreIntegral = new CoreIntegral();
        coreIntegral.setId(id);
        coreIntegral.toCoreIntegralDTO(coreIntegralDTO);
        coreIntegral.setCreatedAt(LocalDateTime.now());
        coreIntegral.setCreatedBy(userId);
        coreIntegral.setUpdateAt(LocalDateTime.now());
        coreIntegral.setUpdateBy(userId);
        //把用户的id和积分值合成字符串，使用RSA加密生成签名


        String sign =
                RsaUtil.encryptByPublicKey("" + coreIntegralDTO.getUserId() + "" + coreIntegralDTO.getTotal(),
                        RsaEnum.publicKey);

        coreIntegral.setSign(sign);
        boolean saveCoreIntegral = save(coreIntegral);

        if (!saveCoreIntegral) {

            log.info("新增用户积分信息失败:{},操作者：{}，新增失败时间：{}", coreIntegralDTO, userId, LocalDateTime.now());
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_SAVE_ERROR);
        }

        log.info("成功新增用户积分信息:{},操作者：{}，结束时间：{}", coreIntegralDTO, userId, LocalDateTime.now());

        return baseMapper.selectById(id).toCoreIntegralVO();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreIntegralVO updateCoreIntegralAddTotal(CoreIntegralChangeDTO coreIntegralChangeDTO,
                                                     Long appId, Long operationalUserId) {

        log.info("用户增加积分信息:{},平台ID:{},操作者:{},开始时间:{}",
                coreIntegralChangeDTO, appId, operationalUserId);

        //对用户id和用户积分信息存在与否进行验证
        checkCoreIntegralUserId(coreIntegralChangeDTO.getUserId());


        //1、根据签名判断用户的积分数据有没有被非法修改,如何数据被非法修改抛出异常

        CoreIntegralVO coreIntegralVO = getCoreIntegralByUserId(coreIntegralChangeDTO.getUserId());

        boolean result = RsaUtil.verify("" + coreIntegralVO.getUserId() + "" + coreIntegralVO.getTotal()
                , coreIntegralVO.getSign(), RsaEnum.privateKey);

        if (!result) {
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_SIGN_ERROR);
        }

        Integer total = coreIntegralVO.getTotal() + coreIntegralChangeDTO.getChangeNum();
        //2、生成新的签名

        String sign =
                RsaUtil.encryptByPublicKey("" + coreIntegralVO.getUserId() + "" + total,
                        RsaEnum.publicKey);

        //3、修改总积分和新签名
        coreIntegralVO = updateCoreIntegralByUserId(coreIntegralChangeDTO.getUserId(), total, sign, operationalUserId);
        //4、增加积分详情的记录
        CoreIntegralDetailDTO coreIntegralDetailDTO = new CoreIntegralDetailDTO();
        coreIntegralDetailDTO.toCoreIntegralChangeDTO(coreIntegralChangeDTO);
        coreIntegralDetailDTO.setLaveNum(total);
        coreIntegralDetailDTO.setOperateType(OperateTypeEnum.ADD);
        coreIntegralDetailService.saveCoreIntegralDetail(coreIntegralDetailDTO, appId, operationalUserId);
        log.info("用户增加积分信息:{},平台ID:{},操作者:{},结束时间:{}",
                coreIntegralChangeDTO, appId, operationalUserId);
        return coreIntegralVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreIntegralVO updateCoreIntegralReduceTotal(CoreIntegralChangeDTO coreIntegralChangeDTO,
                                                        Long appId,
                                                        Long operationalUserId) {

        log.info("用户减少的积分信息:{},平台ID:{},操作者:{},开始时间:{}",
                coreIntegralChangeDTO, appId, operationalUserId);

        //对用户id和用户积分信息存在与否进行验证
        checkCoreIntegralUserId(coreIntegralChangeDTO.getUserId());
        //1、判断用户的id和用户的总积分或者签名有没有被非法修改
        CoreIntegralVO coreIntegralVO = getCoreIntegralByUserId(coreIntegralChangeDTO.getUserId());
        boolean result = RsaUtil.verify("" + coreIntegralVO.getUserId() + "" + coreIntegralVO.getTotal()
                , coreIntegralVO.getSign(), RsaEnum.privateKey);
        if (!result) {

            log.info("用户ID:{},签名验证结果{},操作者:{},时间:{}",
                    coreIntegralVO.getUserId(), "验证失败", operationalUserId, LocalDateTime.now());
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_SIGN_ERROR);
        }

        //2、判断用户的当前积分是否大于等于需要的积分
        if (coreIntegralVO.getTotal() < coreIntegralChangeDTO.getChangeNum()) {
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_USER_INTEGRAL_IS_NOT_ENOUGH);
        }

        Integer total = coreIntegralVO.getTotal() - coreIntegralChangeDTO.getChangeNum();
        //3、生成新的签名
        String sign = RsaUtil.encryptByPublicKey("" + coreIntegralVO.getUserId() + "" + total,
                RsaEnum.publicKey);
        //4、修改总积分和签名
        coreIntegralVO = updateCoreIntegralByUserId(coreIntegralChangeDTO.getUserId(), total, sign, operationalUserId);

        //5、增加减少积分的详情记录
        CoreIntegralDetailDTO coreIntegralDetailDTO = new CoreIntegralDetailDTO();
        coreIntegralDetailDTO.toCoreIntegralChangeDTO(coreIntegralChangeDTO);
        coreIntegralDetailDTO.setLaveNum(total);
        coreIntegralDetailDTO.setOperateType(OperateTypeEnum.REDUCE);
        coreIntegralDetailService.saveCoreIntegralDetail(coreIntegralDetailDTO, appId, operationalUserId);


        log.info("用户减少积分信息:{},平台ID:{},操作者:{},结束时间:{}",
                coreIntegralChangeDTO, appId, operationalUserId);
        return coreIntegralVO;
    }

    @Override
    public CoreIntegralVO getCoreIntegralByUserId(Long userId) {
        Option.of(userId)
                .getOrElseThrow(() -> new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_USER_ID_IS_NOT_NULL));

        //对userId长度进行验证
        int length = 19;
        if (String.valueOf(userId).length() != length) {
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_LENGTH_ERROR);
        }
        CoreIntegral coreIntegral = baseMapper.selectOne(Wrappers.<CoreIntegral>lambdaQuery()
                .select(CoreIntegral::getId, CoreIntegral::getUserId, CoreIntegral::getSign, CoreIntegral::getTotal)
                .eq(CoreIntegral::getUserId, userId));
        if (coreIntegral == null) {
            return null;
        }
        return coreIntegral.toCoreIntegralVO();
    }

    @Override
    public CoreIntegralVO getCoreIntegralById(Long id) {

        Option.of(id).getOrElseThrow(() -> new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_ID_IS_NOT_NULL));

        int length = 19;
        if (String.valueOf(id).length() != length) {
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_LENGTH_ERROR);
        }


        CoreIntegral coreIntegral = baseMapper.selectOne(Wrappers.<CoreIntegral>lambdaQuery()
                .select(CoreIntegral::getId,
                        CoreIntegral::getUserId,
                        CoreIntegral::getTotal,
                        CoreIntegral::getSign)
                .eq(CoreIntegral::getId, id));

        if (coreIntegral == null) {
            return null;
        }

        return coreIntegral.toCoreIntegralVO();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreIntegralVO updateCoreIntegralByUserId(Long userId,
                                                     Integer totalInteger,
                                                     String sign,
                                                     Long operationalUserId) {

        log.info("用户ID:{},修改用户的总积分数{},新的签名:{},操作者:{},开始时间:{}",
                userId, totalInteger, sign, operationalUserId);
        //被增加/减少积分时调用
        boolean updateCoreIntegralAddTotal = update(Wrappers.<CoreIntegral>lambdaUpdate()
                .set(CoreIntegral::getTotal, totalInteger)
                .set(CoreIntegral::getSign, sign)
                .set(CoreIntegral::getUpdateAt, LocalDateTime.now())
                .set(CoreIntegral::getUpdateBy, operationalUserId)
                .eq(CoreIntegral::getUserId, userId));
        if (!updateCoreIntegralAddTotal) {
            log.info("用户ID:{},修改用户的总积分数失败{},新的签名:{},操作者:{},失败时间:{}",
                    userId, totalInteger, sign, operationalUserId);
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_TOTAL_ERROR);
        }

        log.info("用户ID:{},修改用户的总积分数成功{},新的签名:{},操作者:{},结束时间:{}",
                userId, totalInteger, sign, operationalUserId);
        return getCoreIntegralByUserId(userId);
    }



    @Override
    public Page<CoreIntegralVO> pageCoreIntegralPage(
            Long userId, Long pageNum, Long pageSize) {

        //在用户id存在的情况下验证用户id的长度
        int length = 19;
        if (userId != null && String.valueOf(userId).length() != length) {
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_LENGTH_ERROR);
        }

        return baseMapper.pageCoreIntegralPage(userId, new Page<>(pageNum, pageSize));
    }

    @Override
    public boolean checkCoreIntegralUserId(Long userId) {

        int length = 19;
        if (String.valueOf(userId).length() != length) {
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_LENGTH_ERROR);
        }
        //验证当前用户积分信息是否已经存在,不存在抛出异常
        if (getCoreIntegralByUserId(userId) == null) {
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_IS_NOT_EXISTS);
        }

        return true;
    }
}
