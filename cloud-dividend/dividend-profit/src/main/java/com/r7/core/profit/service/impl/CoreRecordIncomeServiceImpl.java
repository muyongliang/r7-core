package com.r7.core.profit.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.profit.constant.CoreRecordIncomeEnum;
import com.r7.core.profit.constant.IncomeEnum;
import com.r7.core.profit.dto.CoreRecordIncomeDTO;
import com.r7.core.profit.mapper.CoreRecordIncomeMapper;
import com.r7.core.profit.model.CoreRecordIncome;
import com.r7.core.profit.service.CoreRecordIncomeService;
import com.r7.core.profit.vo.CoreRecordIncomeVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @Description 发放明细实现类
 * @author wutao
 *
 */
@Slf4j
@Service
public class CoreRecordIncomeServiceImpl extends ServiceImpl<CoreRecordIncomeMapper, CoreRecordIncome>
        implements CoreRecordIncomeService{

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreRecordIncomeVO saveCoreRecordIncome(Long id, CoreRecordIncomeDTO coreRecordIncomeDTO, Long optionalUserId) {
        log.info("发放明细新增内容：{}，操作人：{}，开始时间：{}",
                coreRecordIncomeDTO,optionalUserId, LocalDateTime.now());

        CoreRecordIncome coreRecordIncome = new CoreRecordIncome();


        coreRecordIncome.setId(id);
        //发放时设置这个时间和发放时间
         //todo 需要考虑统计时跨天的问题
//        coreRecordIncome.setIncomeDate(Integer.valueOf(new SimpleDateFormat( "yyyyMMdd")
//                .format(System.currentTimeMillis())));

        coreRecordIncome.setCreatedBy(optionalUserId);
        coreRecordIncome.setCreatedAt(LocalDateTime.now());
        coreRecordIncome.setUpdatedBy(optionalUserId);
        coreRecordIncome.setUpdatedAt(LocalDateTime.now());
        coreRecordIncome.toCoreRecordIncomeDTO(coreRecordIncomeDTO);

        boolean saveCoreRecordIncome = save(coreRecordIncome);

        if (!saveCoreRecordIncome ) {
            log.info("发放明细新增内容失败：{}，操作人：{}，失败时间：{}",
                    coreRecordIncomeDTO,optionalUserId, LocalDateTime.now());
            throw new BusinessException(CoreRecordIncomeEnum.CORE_RECORD_INCOME_SAVE_ERROR);
        }

        log.info("发放明细新增内容成功：{}，操作人：{}，结束时间：{}",
                coreRecordIncomeDTO,optionalUserId, LocalDateTime.now());
        return getCoreRecordIncomeById(id,coreRecordIncomeDTO.getAppId());
    }

    @Override
    public CoreRecordIncomeVO getCoreRecordIncomeById(Long id, Long appId) {
        log.info("发放记录明细ID：{}，appID：{}，查询开始时间：{}",
                id,appId,LocalDateTime.now());

        Option.of(id).getOrElseThrow(() -> new BusinessException(CoreRecordIncomeEnum.CORE_RECORD_INCOME_ID_IS_NULL));

        CoreRecordIncome coreRecordIncome = Option.of(getOne(Wrappers.<CoreRecordIncome>lambdaQuery()
                .select(CoreRecordIncome::getId,
                        CoreRecordIncome::getAppId,
                        CoreRecordIncome::getUserId,
                        CoreRecordIncome::getDistributionAt,
                        CoreRecordIncome::getDistributionAmount,
                        CoreRecordIncome::getDistributionIntegral,
                        CoreRecordIncome::getCountNum,
                        CoreRecordIncome::getStatus,
                        CoreRecordIncome::getDescription)
                .eq(CoreRecordIncome::getId,id).eq(CoreRecordIncome::getAppId,appId)))
                .getOrElseThrow(() -> new BusinessException(CoreRecordIncomeEnum.CORE_RECORD_INCOME_IS_NOT_EXISTS));

        log.info("发放记录明细ID：{}，appID：{}，查询结束时间：{}",
                id,appId,LocalDateTime.now());
        return coreRecordIncome.toCoreRecordIncomeVO();
    }

    @Override
    public Page<CoreRecordIncomeVO> pageCoreRecordIncome(IncomeEnum status,
                                                         Long userId,
                                                         Long appId,
                                                         Integer pageNum,
                                                         Integer pageSize) {

        log.info("分页查询发放记录参数1：{}，参数二：{}，参数三：{}，参数四：{}，参数五：{}，开始时间：{}",
                status,userId,appId,pageNum,pageSize,LocalDateTime.now());

        Page<CoreRecordIncomeVO> page = baseMapper.pageCoreRecordIncome(status.getValue(),userId,appId,
                new Page(pageNum, pageSize));

        log.info("分页查询发放记录参数1：{}，参数二：{}，参数三：{}，参数四：{}，参数五：{}，结束时间：{}",
                status,userId,appId,pageNum,pageSize,LocalDateTime.now());
        return page;
    }

    @Override
    public int countTotalAmountByUserId(Long userId) {

        log.info("统计用户id的总金额：{}，平台id:{},开始时间：{}",userId,LocalDateTime.now());
        //根据用户id平台id把发放记录明细查出来
        List<CoreRecordIncome> list  = getCoreRecordIncomeByUserId(userId,IncomeEnum.ISSUED);
        //对发放金额进行统计
        int amount= list.stream().collect(Collectors.summingInt(CoreRecordIncome::getDistributionAmount));
        log.info("统计用户id的总金额：{}，平台id:{},结束时间：{}",userId,LocalDateTime.now());

        return amount;
    }

    @Override
    public int countTotalIntegralByUserId(Long userId) {

        log.info("统计用户id的总积分：{}，平台id:{},开始时间：{}",userId,LocalDateTime.now());
        List<CoreRecordIncome> list  = getCoreRecordIncomeByUserId(userId,IncomeEnum.ISSUED);
        //对发放积分进行统计
        int integral= list.stream().mapToInt(CoreRecordIncome::getDistributionIntegral).sum();
        log.info("统计用户id的总积分：{}，平台id:{},结束时间：{}",userId,LocalDateTime.now());

        return integral;
    }

    @Override
    public List<CoreRecordIncome> getCoreRecordIncomeByUserId(Long userId, IncomeEnum status) {

        List<CoreRecordIncome> list = Option.of(list(Wrappers.<CoreRecordIncome>lambdaQuery()
                .select(CoreRecordIncome::getDistributionIntegral,
                        CoreRecordIncome::getDistributionAmount)
                .eq(CoreRecordIncome::getUserId,userId)
                .eq(CoreRecordIncome::getStatus,status)))
                .getOrNull();
        if (list.size() == 0) {
            throw new BusinessException(CoreRecordIncomeEnum.CORE_RECORD_INCOME_IS_NOT_EXISTS);
        }

        return list;
    }



    @Override
    public Page<CoreRecordIncomeVO> getCoreRecordIncomeAmountByUserId(Long userId,
                                                                      Integer pageNum,
                                                                      Integer pageSize) {

        log.info("分页查询用户，发放成功的发放金额记录参数1：{}，参数二：{}，参数三：{}，开始时间：{}",
                userId,pageNum,pageSize,LocalDateTime.now());

        Page<CoreRecordIncomeVO> page = baseMapper.pageCoreRecordIncomeAmountByUserId(userId,2,
                new Page(pageNum, pageSize));

        log.info("分页查询用户，发放成功的发放金额记录参数1：{}，参数二：{}，参数三：{}，结束时间：{}",
                userId,pageNum,pageSize,LocalDateTime.now());
        return page;
    }

    @Override
    public Page<CoreRecordIncomeVO> getCoreRecordIncomeIntegralByUserId(Long userId,
                                                                        Integer pageNum,
                                                                        Integer pageSize) {

        log.info("分页查询用户，发放成功的发放积分记录参数1：{}，参数二：{}，参数三：{}，开始时间：{}",
                userId,pageNum,pageSize,LocalDateTime.now());

        Page<CoreRecordIncomeVO> page = baseMapper.pageCoreRecordIncomeIntegralByUserId(userId,2,
                new Page(pageNum, pageSize));

        log.info("分页查询用户，发放成功的发放积分记录参数1：{}，参数二：{}，参数三：{}，结束时间：{}",
                userId,pageNum,pageSize,LocalDateTime.now());

        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreRecordIncomeVO updateCoreRecordIncomeStatusById(Long id, Long appId,
                                                               IncomeEnum status ,
                                                               LocalDateTime distributionAt,
                                                               String description,
                                                               Integer incomeDate,
                                                               Long optionalUserId) {

        log.info("发放明细id：{}，修改内容1：{}，修改内容2：{},修改内容3：{},操作者：{}，修改开始时间：{}",
                id,status,distributionAt,incomeDate,optionalUserId,LocalDateTime.now());
        boolean updateCoreRecordIncomeStatus =   update(Wrappers.<CoreRecordIncome>lambdaUpdate()
                .set(CoreRecordIncome::getDescription,description)
                .set(CoreRecordIncome::getStatus,status)
                .set(CoreRecordIncome::getDistributionAt,distributionAt)
                .set(CoreRecordIncome::getIncomeDate,incomeDate)
                .set(CoreRecordIncome::getUpdatedBy,optionalUserId)
                .set(CoreRecordIncome::getUpdatedAt,LocalDateTime.now())
                .eq(CoreRecordIncome::getId,id)
                .eq(CoreRecordIncome::getAppId,appId));
        if (!updateCoreRecordIncomeStatus ) {
            log.info("发放明细id：{}，修改内容失败：{}，操作者：{}，失败时间：{}",
                    id,optionalUserId,LocalDateTime.now());
            throw new BusinessException(CoreRecordIncomeEnum.CORE_RECORD_INCOME_STATUS_UPDATE_ERROR);
        }
        log.info("发放明细id：{}，修改内容1：{}，修改内容2：{},修改内容3：{},操作者：{}，修改结束时间：{}",
                id,status,distributionAt,incomeDate,optionalUserId,LocalDateTime.now());
        return getCoreRecordIncomeById(id, appId);
    }

    @Override
    public List<CoreRecordIncome> getAllCoreRecordIncomeByStatus(IncomeEnum status) {

        log.info("查询参数发放状态:{},开始时间：{}",status,LocalDateTime.now());
        List<CoreRecordIncome> list = Option.of(list(Wrappers.<CoreRecordIncome>lambdaQuery()
                .select(CoreRecordIncome::getId,
                        CoreRecordIncome::getAppId,
                        CoreRecordIncome::getUserId,
                        CoreRecordIncome::getDistributionAmount,
                        CoreRecordIncome::getDistributionIntegral,
                        CoreRecordIncome::getCountNum,
                        CoreRecordIncome::getStatus,
                        CoreRecordIncome::getDescription,
                        CoreRecordIncome::getDistributionAt)
                .eq(CoreRecordIncome::getStatus,status).orderByAsc(CoreRecordIncome::getCreatedAt)))
                .getOrNull();
        if (list.size() == 0) {
            throw new BusinessException(CoreRecordIncomeEnum.CORE_RECORD_INCOME_IS_NOT_EXISTS);
        }
        log.info("发放状态:{},结束时间：{}",status,LocalDateTime.now());

        return list;
    }

    @Override
    public List<CoreRecordIncome> getCoreRecordIncomeByAppId(Long appId, IncomeEnum status) {

        List<CoreRecordIncome> listCoreRecordIncome = Option.of(list(Wrappers.<CoreRecordIncome>lambdaQuery()
                .select(CoreRecordIncome::getDistributionIntegral,CoreRecordIncome::getDistributionAmount,
                        CoreRecordIncome::getAppId)
                .eq(CoreRecordIncome::getAppId,appId)
                .eq(CoreRecordIncome::getStatus,status)))
                .getOrNull();
        if (listCoreRecordIncome.size() == 0) {
            throw new BusinessException(CoreRecordIncomeEnum.CORE_RECORD_INCOME_IS_NOT_EXISTS);
        }

        return listCoreRecordIncome;
    }
    @Override
    public int countTotalAmountByAppId(Long appId) {
        List<CoreRecordIncome> list  = getCoreRecordIncomeByAppId(appId,IncomeEnum.ISSUED);
        int totalAmount= list.stream().mapToInt(CoreRecordIncome::getDistributionAmount).sum();
        return totalAmount;
    }

    @Override
    public int countTotalIntegralByAppId(Long appId) {
        List<CoreRecordIncome> list  = getCoreRecordIncomeByAppId(appId,IncomeEnum.ISSUED);
        int totalIntegral= list.stream().mapToInt(CoreRecordIncome::getDistributionIntegral).sum();
        return totalIntegral;
    }
}
