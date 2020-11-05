package com.r7.core.assets.wallet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.assets.wallet.constant.WalletBillStatusEnum;
import com.r7.core.assets.wallet.constant.WalletBillTypeEnum;
import com.r7.core.assets.wallet.dto.CoreWalletBillDTO;
import com.r7.core.assets.wallet.model.CoreWalletBill;
import com.r7.core.assets.wallet.vo.CoreWalletBillPageVO;
import com.r7.core.assets.wallet.vo.CoreWalletBillVO;

/**
 * @author zs
 * @description: 钱包账单服务层
 * @date : 2020-10-26
 */
public interface CoreWalletBillService extends IService<CoreWalletBill> {

    /**
     * 新增钱包账单
     *
     * @param coreWalletBillDto 钱包账单传输实体
     * @param appId             平台id
     * @param organId           组织id
     * @param userId            操作者id
     * @return 返回是否成功
     */
    Boolean saveWalletBill(CoreWalletBillDTO coreWalletBillDto, Long appId, Long organId, Long userId);

    /**
     * 根据id修改钱包账单
     *
     * @param id                钱包账单id
     * @param coreWalletBillDto 钱包账单传输实体
     * @param appId             平台id
     * @param organId           组织id
     * @param userId            操作者id
     * @return 返回修改结果
     */
    CoreWalletBillVO updateWalletBillById(Long id, CoreWalletBillDTO coreWalletBillDto, Long appId, Long organId, Long userId);

    /**
     * 根据钱包账单id查询
     *
     * @param id 钱包账单id
     * @return 返回查询结果
     */
    CoreWalletBillVO getWalletBillById(Long id);

    /**
     * 分页查询全部账单
     *
     * @param userId    用户id
     * @param type      交易类型
     * @param source    交易来源
     * @param status    交易状态
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param pageNum   当前页
     * @param pageSize  每页条数
     * @return 返回查询结果
     */
    IPage<CoreWalletBillPageVO> pageWalletBillByUserId(Long userId, WalletBillTypeEnum type, String source, WalletBillStatusEnum status,
                                                       String startDate, String endDate, Integer pageNum, Integer pageSize);

    /**
     * 根据条件查询分页查询平台所有用户钱包账单信息
     *
     * @param appId     平台id
     * @param type      交易类型
     * @param source    交易来源
     * @param status    交易状态
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param pageNum   当前页
     * @param pageSize  每页条数
     * @return 返回查询结果
     */
    IPage<CoreWalletBillPageVO> pageWalletBillByAppId(Long appId, WalletBillTypeEnum type, String source, WalletBillStatusEnum status,
                                                      String startDate, String endDate, Integer pageNum, Integer pageSize);

    /**
     * 根据平台id和条件获取用户消费总余额
     *
     * @param appId     平台
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 返回消费总余额
     */
    Integer getTotalConsumptionBalance(Long appId, String startDate, String endDate);
}
