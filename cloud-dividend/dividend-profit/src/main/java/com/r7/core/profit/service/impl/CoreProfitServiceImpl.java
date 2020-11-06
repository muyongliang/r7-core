package com.r7.core.profit.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.profit.constant.CalculationStatusEnum;
import com.r7.core.profit.constant.CoreProfitEnum;
import com.r7.core.profit.constant.ProfitTypeEnum;
import com.r7.core.profit.constant.SettlementEnum;
import com.r7.core.profit.dto.CoreProfitDTO;
import com.r7.core.profit.mapper.CoreProfitMapper;
import com.r7.core.profit.model.CoreProfit;
import com.r7.core.profit.service.CoreProfitService;
import com.r7.core.profit.vo.CoreProfitVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author wutao
 * @Description 分润明细实现类
 */
@Slf4j
@Service
public class CoreProfitServiceImpl extends ServiceImpl<CoreProfitMapper, CoreProfit>
        implements CoreProfitService {


    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreProfitVO saveCoreProfit(CoreProfitDTO coreProfitDTO, Long optionalUserId) {

        log.info("分润明细增加内容：{}，操作人：{}，开始时间：{}",
                coreProfitDTO, optionalUserId, LocalDateTime.now());

        CoreProfit coreProfit = new CoreProfit();
        //雪花生成id
        coreProfit.setId(SnowflakeUtil.getSnowflakeId());
        //todo 需要考虑统计数据时的跨天问题？
        coreProfit.setProfitDate(Integer.valueOf(new SimpleDateFormat("yyyyMMdd")
                .format(System.currentTimeMillis())));
        coreProfit.setHour(LocalDateTime.now().getHour());

        coreProfit.setCreatedBy(optionalUserId);
        coreProfit.setCreatedAt(LocalDateTime.now());
        coreProfit.setUpdatedBy(optionalUserId);
        coreProfit.setUpdatedAt(LocalDateTime.now());
        coreProfit.toCoreProfitDTO(coreProfitDTO);
        boolean saveCoreProfit = save(coreProfit);
        if (!saveCoreProfit) {

            log.info("分润明细增加内容失败：{}，操作人：{}，结束时间：{}",
                    coreProfitDTO, optionalUserId, LocalDateTime.now());

            throw new BusinessException(CoreProfitEnum.CORE_PROFIT_SAVE_ERROR);
        }

        log.info("分润明细增加内容成功：{}，操作人：{}，结束时间：{}",
                coreProfitDTO, optionalUserId, LocalDateTime.now());

        return coreProfit.toCoreProfitVO();
    }

    @Override
    public CoreProfitVO getCoreProfitById(Long id, Long appId) {
        log.info("分润明细ID：{}，平台ID：{}，根据分润id查询分润明细详情开始时间：{}",
                id, appId, LocalDateTime.now());
        Option.of(id).getOrElseThrow(() -> new BusinessException(CoreProfitEnum.CORE_PROFIT_ID_NOT_NULL));

        checkIdLength(id);
        checkIdLength(appId);

        CoreProfit coreProfit = Option.of(getOne(Wrappers.<CoreProfit>lambdaQuery()
                .select(CoreProfit::getId, CoreProfit::getAppId,
                        CoreProfit::getPlanId, CoreProfit::getOrderId,
                        CoreProfit::getUserId, CoreProfit::getRecordIncomeId,
                        CoreProfit::getLevel, CoreProfit::getAmount,
                        CoreProfit::getIntegral, CoreProfit::getStatus,
                        CoreProfit::getRate, CoreProfit::getType,
                        CoreProfit::getDescription, CoreProfit::getCreatedAt,
                        CoreProfit::getProfitDate, CoreProfit::getHour)
                .eq(CoreProfit::getId, id).eq(CoreProfit::getAppId, appId)))
                .getOrElseThrow(() -> new BusinessException(CoreProfitEnum.CORE_PROFIT_IS_NOT_EXISTS));
        log.info("分润明细ID：{}，平台ID：{}，根据分润id查询分润明细详情结束时间：{}",
                id, appId, LocalDateTime.now());
        return coreProfit.toCoreProfitVO();
    }

    @Override
    public Page<CoreProfitVO> pageCoreProfit(Long userId, Long orderId,
                                             Long appId, ProfitTypeEnum type,
                                             Integer pageNum, Integer pageSize) {

        log.info("分页查询分润参数1：{}，参数二：{}，参数三：{}，参数四：{}，参数五：{}，参数六：{}，开始时间：{}",
                userId, orderId, appId, type, pageNum, pageSize, LocalDateTime.now());

        if (userId != null) {
            checkIdLength(userId);
        }
        if (orderId != null) {
            checkIdLength(orderId);
        }
        if (appId != null) {
            checkIdLength(appId);
        }

        Integer profitType;

        if (type == null) {
            profitType = 0;
        } else {
            profitType = type.getValue();
        }


        Page<CoreProfitVO> page = baseMapper.pageCoreProfit(userId, orderId, appId, profitType,
                new Page(pageNum, pageSize));

        log.info("分页查询分润参数1：{}，参数二：{}，参数三：{}，参数四：{}，参数五：{}，参数六：{}，结束时间：{}",
                userId, orderId, appId, type, pageNum, pageSize, LocalDateTime.now());
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreProfitVO updateCoreProfitStatusById(Long id, Long appId,
                                                   CalculationStatusEnum status,
                                                   Long recordIncomeId,
                                                   Long optionalUserId) {
        log.info("分润明细id：{}，修改内容：{}，操作人：{}，修改开始时间：{}"
                , id, status, optionalUserId, LocalDateTime.now());

        boolean coreProfitStatus = update(Wrappers.<CoreProfit>lambdaUpdate()
                .set(CoreProfit::getStatus, status)
                .set(CoreProfit::getRecordIncomeId, recordIncomeId)
                .set(CoreProfit::getUpdatedBy, optionalUserId)
                .set(CoreProfit::getUpdatedAt, LocalDateTime.now())
                .eq(CoreProfit::getId, id)
                .eq(CoreProfit::getAppId, appId));
        if (!coreProfitStatus) {
            log.info("分润明细id：{}，修改内容失败：{}，操作人：{}，修改失败时间：{}",
                    id, status, optionalUserId, LocalDateTime.now());
            throw new BusinessException(CoreProfitEnum.CORE_PROFIT_UPDATE_STATUS_ERROR);
        }

        log.info("分润明细id：{}，修改内容：{}，操作人：{}，修改结束时间：{}"
                , id, status, optionalUserId, LocalDateTime.now());
        return getCoreProfitById(id, appId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List settlementAmount(Long userId, Long appId, CalculationStatusEnum status,
                                 Long recordIncomeId, LocalDateTime endTime) {
        log.info("合并需要发放金额的用户id：{}，平台id：{}，合并开始时间：{}",
                userId, appId, LocalDateTime.now());

        List arrayList = new ArrayList();
        List<CoreProfit> list = getCoreProfitByUserId(userId, appId, status, endTime)
                .stream().filter(x -> x.getAmount() > 0 && x.getIntegral() == 0).collect(Collectors.toList());
        //合并计算的金额
        int settlementTotalAmount = list.stream().mapToInt(CoreProfit::getAmount).sum();
        //统计分润的条数
        int countNumAmount = list.size();
        //把核算过的分润明细的计算状态，修改为2即已计算
        list.forEach(x -> updateCoreProfitStatusById(x.getId(), x.getAppId(), CalculationStatusEnum.CALCULATED, recordIncomeId, 0L));
        arrayList.add(settlementTotalAmount);
        arrayList.add(countNumAmount);

        log.info("合并需要发放金额的用户id：{}，平台id：{}，合并结束时间：{}",
                userId, appId, LocalDateTime.now());

        return arrayList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List settlementIntegral(Long userId, Long appId, CalculationStatusEnum status,
                                   Long recordIncomeId, LocalDateTime endTime) {

        log.info("合并需要发放积分的用户id：{}，平台id：{}，合并开始时间：{}",
                userId, appId, LocalDateTime.now());

        List arrayList = new ArrayList();

        List<CoreProfit> list = getCoreProfitByUserId(userId, appId, status, endTime)
                .stream().filter(x -> x.getIntegral() > 0 && x.getAmount() == 0).collect(Collectors.toList());
        //1、合并计算的积分
        int settlementTotalIntegral = list.stream().mapToInt(CoreProfit::getIntegral).sum();
        //2、统计分润的条数
        int countNumIntegral = list.size();
        //3、把核算过的分润明细的计算状态，修改为2即已计算
        list.forEach(x -> updateCoreProfitStatusById(x.getId(), x.getAppId(), CalculationStatusEnum.CALCULATED, recordIncomeId, 0L));
        //4、把用户分润的积分和分润条数放进集合中
        arrayList.add(settlementTotalIntegral);
        arrayList.add(countNumIntegral);

        log.info("合并需要发放积分的用户id：{}，平台id：{}，合并结束时间：{}",
                userId, appId, LocalDateTime.now());
        return arrayList;
    }

    @Override
    public List<CoreProfit> getCoreProfitByUserId(Long userId, Long appId,
                                                  CalculationStatusEnum status, LocalDateTime endTime) {

        Option.of(userId).getOrElseThrow(() -> new BusinessException(CoreProfitEnum.CORE_PROFIT_USER_ID_NOT_NULL));


        List<CoreProfit> list = Option.of(list(Wrappers.<CoreProfit>lambdaQuery()
                .select(CoreProfit::getAmount,
                        CoreProfit::getIntegral,
                        CoreProfit::getStatus,
                        CoreProfit::getCreatedAt,
                        CoreProfit::getAppId,
                        CoreProfit::getId)
                .lt(CoreProfit::getCreatedAt, endTime)
                .eq(CoreProfit::getUserId, userId)
                .eq(CoreProfit::getAppId, appId)
                .eq(CoreProfit::getStatus, status)))
                .getOrNull();

        if (list.size() == 0) {
            throw new BusinessException(CoreProfitEnum.CORE_PROFIT_USER_ID_IS_NOT_EXISTS);
        }
        return list;
    }

    @Override
    public List<CoreProfit> getAllCoreProfitByStatus(CalculationStatusEnum status, LocalDateTime endTime) {

        //查询出截止时间以前未计算的分润明细

        List<CoreProfit> list = Option.of(list(Wrappers.<CoreProfit>lambdaQuery()
                .select(CoreProfit::getUserId,
                        CoreProfit::getAmount,
                        CoreProfit::getIntegral,
                        CoreProfit::getDifference,
                        CoreProfit::getAppId)
                .lt(CoreProfit::getCreatedAt, endTime)
                .eq(CoreProfit::getStatus, status)))
                .getOrNull();


        list.forEach(x -> {
            if (x.getAmount() != 0 && x.getIntegral() != 0) {
                throw new BusinessException(SettlementEnum.SETTLEMENT_ERROR);
            }
        });

        List<CoreProfit> listCoreProfit = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(x -> x.getUserId() + ";" + x.getAppId() + ";" + x.getDifference()))),
                ArrayList::new));


        return listCoreProfit;
    }

    @Override
    public boolean checkIdLength(Long id) {
        int length = 19;
        if (String.valueOf(id).length() != length) {
            throw new BusinessException(CoreProfitEnum.CORE_ID_LENGTH_ERROR);
        }
        return true;
    }
}
