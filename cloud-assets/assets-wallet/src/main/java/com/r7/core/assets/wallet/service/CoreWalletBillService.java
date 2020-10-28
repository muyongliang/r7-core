package com.r7.core.assets.wallet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
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
     * @param userId   用户id
     * @param pageNum  当前页数
     * @param pageSize 每页展示个数
     * @return 返回分页结果
     */
    IPage<CoreWalletBillPageVO> pageWalletBill(Long userId, Integer pageNum, Integer pageSize);
}
