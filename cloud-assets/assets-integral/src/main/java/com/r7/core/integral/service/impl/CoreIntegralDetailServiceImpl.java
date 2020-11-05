package com.r7.core.integral.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.integral.constant.CoreIntegralDetailEnum;
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
 * @author wt
 * @Description 积分详情实现类
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

        CoreIntegralDetail coreIntegralDetail = new CoreIntegralDetail();
        coreIntegralDetail.toCoreIntegralDetailDTO(coreIntegralDetailDTO);
        Long id = SnowflakeUtil.getSnowflakeId();
        coreIntegralDetail.setDetailedDate(Integer.valueOf(new SimpleDateFormat("yyyyMMdd")
                .format(System.currentTimeMillis())));
        coreIntegralDetail.setId(id);
        coreIntegralDetail.setAppId(appId);
        coreIntegralDetail.setCreatedAt(LocalDateTime.now());
        coreIntegralDetail.setCreatedBy(userId);
        coreIntegralDetail.setUpdateAt(LocalDateTime.now());
        coreIntegralDetail.setUpdateBy(userId);
        boolean saveCoreIntegralDetail = save(coreIntegralDetail);
        if (!saveCoreIntegralDetail) {
            log.info("新增积分详情失败：{}，平台ID：{}，操作者：{}，失败时间：{}");
            throw new BusinessException(CoreIntegralDetailEnum.CORE_INTEGRAL_DETAIL_SAVE_ERROR);
        }

        log.info("成功新增积分详情：{}，平台ID：{}，操作者：{}，结束时间：{}");

        return baseMapper.selectById(id).toCoreIntegralDetailVO();
    }

    @Override
    public CoreIntegralDetailVO getCoreIntegralDetailById(Long id, Long appId) {
        log.info("积分详情ID：{},平台ID：{},查询开始时间：{}",
                id, appId, LocalDateTime.now());
        Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreIntegralDetailEnum.CORE_INTEGRAL_DETAIL_ID_IS_NOT_NULL));


        CoreIntegralDetail coreIntegralDetail = Option.of(getOne(Wrappers.<CoreIntegralDetail>lambdaQuery()
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
                .eq(CoreIntegralDetail::getId, id).eq(CoreIntegralDetail::getAppId, appId)))
                .getOrElseThrow(() -> new BusinessException(CoreIntegralDetailEnum.CORE_INTEGRAL_DETAIL_IS_NOT_EXISTS));


        log.info("积分详情ID：{}，平台ID：{}，查询结束时间：{}",
                id, appId, LocalDateTime.now());
        return coreIntegralDetail.toCoreIntegralDetailVO();
    }

    @Override
    public Page<CoreIntegralDetailVO> pageCoreIntegralDetailAll(String businessCode,
                                                                Integer sourceType,
                                                                Long appId,
                                                                Integer operateType,
                                                                Long pageNum,
                                                                Long pageSize) {

        return baseMapper.pageCoreIntegralDetailAll(businessCode, sourceType, appId, operateType,
                new Page<>(pageNum, pageSize));
    }

    @Override
    public Page<CoreIntegralDetailVO> pageCoreIntegralDetailByUserId(Long userId, Long appId,
                                                                     Integer operateType,
                                                                     Integer sourceType,
                                                                     Long pageNum, Long pageSize) {


        return baseMapper.pageCoreIntegralDetailByUserId(userId, appId, operateType, sourceType,
                new Page<>(pageNum, pageSize));
    }
}
