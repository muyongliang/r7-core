package com.r7.core.integral.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.integral.constant.CoreIntegralDetailEnum;
import com.r7.core.integral.constant.OperateTypeEnum;
import com.r7.core.integral.constant.SourceTypeEnum;
import com.r7.core.integral.dto.CoreIntegralDetailDTO;
import com.r7.core.integral.mapper.CoreIntegralDetailMapper;
import com.r7.core.integral.model.CoreIntegralDetail;
import com.r7.core.integral.service.CoreIntegralDetailService;
import com.r7.core.integral.vo.CoreIntegralDetailVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * 
 * @Description 积分详情实现类
 * @author wt
 * 
 */
@Slf4j
@Service
public class CoreIntegralDetailServiceImpl extends ServiceImpl<CoreIntegralDetailMapper, CoreIntegralDetail>
        implements CoreIntegralDetailService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreIntegralDetailVO saveCoreIntegralDetail(CoreIntegralDetailDTO coreIntegralDetailDTO,
                                                       Long appId, Long userId) {

        log.info("新增积分详情：{}，平台ID：{}，操作者：{}，开始时间：{}");

        //对dto中用户id长度进行验证
        int length =19;

        if (String.valueOf(coreIntegralDetailDTO.getUserId().longValue()).length() != length) {
            throw new BusinessException(CoreIntegralDetailEnum.CORE_INTEGRAL_LENGTH_ERROR);
        }

        CoreIntegralDetail coreIntegralDetail = new CoreIntegralDetail();
        coreIntegralDetail.toCoreIntegralDetailDTO(coreIntegralDetailDTO);
        Long id = SnowflakeUtil.getSnowflakeId();
        coreIntegralDetail.setDetailedDate(Integer.valueOf(new SimpleDateFormat( "yyyyMMdd")
                .format(System.currentTimeMillis())));
        coreIntegralDetail.setId(id);
        coreIntegralDetail.setAppId(appId);
        coreIntegralDetail.setCreatedAt(LocalDateTime.now());
        coreIntegralDetail.setCreatedBy(userId);
        coreIntegralDetail.setUpdateAt(LocalDateTime.now());
        coreIntegralDetail.setUpdateBy(userId);
        boolean saveCoreIntegralDetail = save(coreIntegralDetail);
        if (!saveCoreIntegralDetail ) {
            log.info("新增积分详情失败：{}，平台ID：{}，操作者：{}，失败时间：{}");
            throw new BusinessException(CoreIntegralDetailEnum.CORE_INTEGRAL_DETAIL_SAVE_ERROR);
        }

        log.info("成功新增积分详情：{}，平台ID：{}，操作者：{}，结束时间：{}");

        return baseMapper.selectById(id).toCoreIntegralDetailVO();
    }

    @Override
    public CoreIntegralDetailVO getCoreIntegralDetailById(Long id, Long appId) {
        log.info("积分详情ID：{},平台ID：{},查询开始时间：{}",
                id,appId,LocalDateTime.now());
        Option.of(id)
                .getOrElseThrow(()-> new BusinessException(CoreIntegralDetailEnum.CORE_INTEGRAL_DETAIL_ID_IS_NOT_NULL));

        int length =19;
        if ( String.valueOf(id).length() != length) {
            throw new BusinessException(CoreIntegralDetailEnum.CORE_INTEGRAL_LENGTH_ERROR);
        }

        CoreIntegralDetail coreIntegralDetail=    Option.of(getOne(Wrappers.<CoreIntegralDetail>lambdaQuery()
                .select(CoreIntegralDetail::getId,
                        CoreIntegralDetail::getAppId,
                        CoreIntegralDetail::getUserId,
                        CoreIntegralDetail::getUpdateAt,
                        CoreIntegralDetail::getSourceType,
                        CoreIntegralDetail::getBusinessCode,
                        CoreIntegralDetail::getLaveNum,
                        CoreIntegralDetail::getChangeNum,
                        CoreIntegralDetail::getOperateType,
                        CoreIntegralDetail::getDetailedDate,
                        CoreIntegralDetail::getDescription)
                .eq(CoreIntegralDetail::getId,id).eq(CoreIntegralDetail::getAppId,appId)))
                .getOrElseThrow(() -> new BusinessException(CoreIntegralDetailEnum.CORE_INTEGRAL_DETAIL_IS_NOT_EXISTS));


        log.info("积分详情ID：{}，平台ID：{}，查询结束时间：{}",
                id,appId,LocalDateTime.now());
        return coreIntegralDetail.toCoreIntegralDetailVO();
    }

    @Override
    public Page<CoreIntegralDetailVO> pageCoreIntegralDetailAll(String businessCode,
                                                                SourceTypeEnum sourceType,
                                                                Long appId,
                                                                OperateTypeEnum operateType,
                                                                Long pageNum,
                                                                Long pageSize) {




        Integer source ;

        if (sourceType == null ) {
            source = 0;
        }else {
            source = sourceType.getValue();
        }

        Integer operate;
        if (operateType == null) {
            operate = 0;
        }else {
            operate = operateType.getValue();
        }

        //对参数进行长度验证
        int length =19;

        if (businessCode!=null && length != businessCode.length()) {
            throw new BusinessException(CoreIntegralDetailEnum.CORE_INTEGRAL_LENGTH_ERROR);
        }

        if (appId !=null && String.valueOf(appId).length() != length) {
            throw new BusinessException(CoreIntegralDetailEnum.CORE_INTEGRAL_LENGTH_ERROR);
        }

        return  baseMapper.pageCoreIntegralDetailAll(businessCode,source,appId,operate,
                new Page<>(pageNum,pageSize)) ;
    }

    @Override
    public Page<CoreIntegralDetailVO> pageCoreIntegralDetailByUserId(Long userId, Long appId,
                                                                     OperateTypeEnum operateType,
                                                                     SourceTypeEnum sourceType,
                                                                     Long pageNum, Long pageSize) {

        int length =19;

        if (userId != null && String.valueOf(userId).length() != length) {
            throw new BusinessException(CoreIntegralDetailEnum.CORE_INTEGRAL_LENGTH_ERROR);
        }

        if (appId != null && String.valueOf(appId).length() != length) {
            throw new BusinessException(CoreIntegralDetailEnum.CORE_INTEGRAL_LENGTH_ERROR);
        }


        Integer source ;

        if (sourceType == null ) {
            source = 0;
        }else {
            source = sourceType.getValue();
        }

        Integer operate;
        if (operateType != null) {
            operate = operateType.getValue();

        }else {
            operate = 0;
        }


        return baseMapper.pageCoreIntegralDetailByUserId(userId,appId,operate,source,
                new Page<>(pageNum,pageSize) );
    }
}
