package com.r7.core.integral.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.integral.constant.CoreIntegralEnum;
import com.r7.core.integral.dto.CoreIntegralDTO;
import com.r7.core.integral.dto.CoreIntegralDetailDTO;
import com.r7.core.integral.mapper.CoreIntegralMapper;
import com.r7.core.integral.model.CoreIntegral;
import com.r7.core.integral.model.CoreIntegralDetail;
import com.r7.core.integral.service.CoreIntegralDetailService;
import com.r7.core.integral.service.CoreIntegralService;
import com.r7.core.integral.util.Md5Util;
import com.r7.core.integral.vo.CoreIntegralVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 
 * @Description 当前用户积分实现类
 * @author wt
 * 
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
        log.info("新增用户积分信息:{},操作者：{}，开始时间：{}",coreIntegralDTO,userId, LocalDateTime.now());


        Long id =  SnowflakeUtil.getSnowflakeId();
        CoreIntegral coreIntegral = new CoreIntegral();
        coreIntegral.setId(id);
        coreIntegral.toCoreIntegralDTO(coreIntegralDTO);
        coreIntegral.setCreatedAt(LocalDateTime.now());
        coreIntegral.setCreatedBy(userId);
        coreIntegral.setUpdateAt(LocalDateTime.now());
        coreIntegral.setUpdateBy(userId);
       //把用户的id和积分值合成字符串，生成签名
        String sign = Md5Util.generate(""+coreIntegralDTO.getUserId()+""+coreIntegralDTO.getTotal());
        coreIntegral.setSign(sign);
        boolean saveCoreIntegral =   save(coreIntegral);

        if (!saveCoreIntegral) {

            log.info("新增用户积分信息失败:{},操作者：{}，新增失败时间：{}",coreIntegralDTO,userId, LocalDateTime.now());
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_SAVE_ERROR);
        }

        log.info("成功新增用户积分信息:{},操作者：{}，结束时间：{}",coreIntegralDTO,userId, LocalDateTime.now());

        return baseMapper.selectById(id).toCoreIntegralVO();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreIntegralVO updateCoreIntegralAddTotal( String businessCode,
                                                      Integer addInteger,
                                                      Long userId,
                                                      Integer sourceType,
                                                      Long appId,Long operationalUserId) {

        log.info("用户ID:{},用户增加的积分数{},操作者:{},开始时间:{}",
                userId,addInteger,appId,operationalUserId);
        //1、根据签名判断用户的积分数据有没有被非法修改,如何数据被非法修改抛出异常

        CoreIntegralVO coreIntegralVO =  getCoreIntegralByUserId(userId);
       boolean result = Md5Util.verify(""+coreIntegralVO.getUserId()+""+coreIntegralVO.getTotal(),
               coreIntegralVO.getSign());
        if (!result) {
            log.info("用户ID:{},用户增加的积分数{},操作者:{},时间:{}");
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_SIGN_ERROR);
        }

        Integer total = coreIntegralVO.getTotal()+addInteger;
        //2、生成新的签名
        String sign = Md5Util.generate(""+userId+""+total);
        //3、修改总积分和新签名
        coreIntegralVO = updateCoreIntegralByUserId(userId,total,sign,operationalUserId);
        //4、增加积分详情的记录
        CoreIntegralDetailDTO coreIntegralDetailDTO = new CoreIntegralDetailDTO();
        coreIntegralDetailDTO.setBusinessCode(businessCode);
        coreIntegralDetailDTO.setChangeNum(addInteger);
        coreIntegralDetailDTO.setUserId(userId);
        coreIntegralDetailDTO.setLaveNum(total);
        coreIntegralDetailDTO.setOperateType(1);
        coreIntegralDetailDTO.setSourceType(sourceType);
        coreIntegralDetailDTO.setDescription("积分已成功增加");
        coreIntegralDetailService.saveCoreIntegralDetail(coreIntegralDetailDTO,appId,operationalUserId);
        log.info("用户ID:{},用户增加的积分数成功{},操作者:{},结束时间:{}",
                userId,addInteger,appId,operationalUserId);
        return coreIntegralVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreIntegralVO updateCoreIntegralReduceTotal( String businessCode,
                                                         Integer reduceInteger,
                                                         Long userId,
                                                         Integer sourceType,
                                                         Long appId,
                                                         Long operationalUserId) {

        log.info("用户ID:{},用户减少的积分数{},操作者:{},开始时间:{}",
                userId,reduceInteger,operationalUserId,LocalDateTime.now());

        //1、判断用户的id和用户的总积分或者签名有没有被非法修改
        CoreIntegralVO coreIntegralVO =  getCoreIntegralByUserId(userId);
        boolean result = Md5Util.verify(""+coreIntegralVO.getUserId()+""+coreIntegralVO.getTotal(),
                coreIntegralVO.getSign());
        if (!result) {
            log.info("用户ID:{},签名验证结果{},操作者:{},时间:{}",
                    userId,"验证失败",operationalUserId,LocalDateTime.now());
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_SIGN_ERROR);
        }

        //2、判断用户的当前积分是否大于等于需要的积分
        if (coreIntegralVO.getTotal() < reduceInteger) {
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_USER_INTEGRAL_IS_NOT_ENOUGH);
        }

        Integer total = coreIntegralVO.getTotal()-reduceInteger;
        //3、生成新的签名
        String sign = Md5Util.generate(""+userId+""+total);
        //4、修改总积分和签名
        coreIntegralVO = updateCoreIntegralByUserId(userId,total,sign,operationalUserId);

        //5、增加减少积分的详情记录
        CoreIntegralDetailDTO coreIntegralDetailDTO = new CoreIntegralDetailDTO();
        coreIntegralDetailDTO.setLaveNum(total);
        coreIntegralDetailDTO.setBusinessCode(businessCode);
        coreIntegralDetailDTO.setChangeNum(reduceInteger);
        coreIntegralDetailDTO.setUserId(userId);
        coreIntegralDetailDTO.setOperateType(2);
        coreIntegralDetailDTO.setSourceType(sourceType);
        coreIntegralDetailDTO.setDescription("积分已使用");
        coreIntegralDetailService.saveCoreIntegralDetail(coreIntegralDetailDTO,appId,operationalUserId);


        log.info("用户ID:{},用户减少的积分数成功{},操作者:{},结束时间:{}",
                userId,reduceInteger,operationalUserId,LocalDateTime.now());
        return coreIntegralVO;
    }

    @Override
    public CoreIntegralVO getCoreIntegralByUserId(Long userId) {
        log.info("用户id:{},开始查询时间:{}");
        Option.of(userId).getOrElseThrow(()->new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_USER_ID_IS_NOT_NULL));
        CoreIntegral coreIntegral =  Option.of(getOne(Wrappers.<CoreIntegral>lambdaQuery()
                .select(CoreIntegral::getId,
                        CoreIntegral::getTotal,
                        CoreIntegral::getUserId,
                        CoreIntegral::getSign)
                .eq(CoreIntegral::getUserId,userId)))
                .getOrElseThrow(() -> new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_IS_NOT_EXISTS));
        log.info("用户id:{},查询结束时间:{}");
        return coreIntegral.toCoreIntegralVO();
    }

    @Override
    public CoreIntegralVO getCoreIntegralById(Long id) {
        log.info("用户积分信息ID:{},查询开始时间:{}",id,LocalDateTime.now());

        Option.of(id).getOrElseThrow(()->new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_ID_IS_NOT_NULL));
        CoreIntegral coreIntegral = baseMapper.selectOne(Wrappers.<CoreIntegral>lambdaQuery()
                .select(CoreIntegral::getId,
                        CoreIntegral::getUserId,
                        CoreIntegral::getTotal,
                        CoreIntegral::getSign)
                .eq(CoreIntegral::getId,id));

        if (coreIntegral == null) {
            return null;
        }

        log.info("用户积分信息ID:{},查询结束时间:{}",id,LocalDateTime.now());
        return coreIntegral.toCoreIntegralVO();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreIntegralVO updateCoreIntegralByUserId(Long userId,
                                                     Integer totalInteger,
                                                     String sign,
                                                     Long operationalUserId) {

        log.info("用户ID:{},修改用户的总积分数{},新的签名:{},操作者:{},开始时间:{}",
                userId,totalInteger, sign,operationalUserId);
        boolean updateCoreIntegralAddTotal = update(Wrappers.<CoreIntegral>lambdaUpdate()
                .set(CoreIntegral::getTotal,totalInteger)
                .set(CoreIntegral::getSign,sign)
                .set(CoreIntegral::getUpdateAt,LocalDateTime.now())
                .set(CoreIntegral::getUpdateBy,userId)
                .eq(CoreIntegral::getUserId,userId));
        if (!updateCoreIntegralAddTotal) {
            log.info("用户ID:{},修改用户的总积分数失败{},新的签名:{},操作者:{},失败时间:{}",
                    userId,totalInteger,sign, operationalUserId);
            throw new BusinessException(CoreIntegralEnum.CORE_INTEGRAL_TOTAL_ERROR);
        }

        log.info("用户ID:{},修改用户的总积分数成功{},新的签名:{},操作者:{},结束时间:{}",
                userId,totalInteger,sign, operationalUserId);
        return getCoreIntegralByUserId(userId);
    }

    @Override
    public Page<CoreIntegralVO> pageCoreIntegralPage(
                                                     Long userId, Long pageNum, Long pageSize) {
        return baseMapper.pageCoreIntegralPage(userId,new Page<>(pageNum,pageSize));
    }


}
