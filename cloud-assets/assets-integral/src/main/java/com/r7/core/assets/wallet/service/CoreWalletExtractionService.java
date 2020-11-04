package com.r7.core.assets.wallet.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.assets.wallet.dto.CoreWalletExtractionSaveDTO;
import com.r7.core.assets.wallet.dto.CoreWalletExtractionUpdateDTO;
import com.r7.core.assets.wallet.model.CoreWalletExtraction;
import com.r7.core.assets.wallet.vo.CoreWalletExtractionVO;

/**
 * @author zs
 * @description: 钱包提现明细服务层
 * @date : 2020-10-26
 */
public interface CoreWalletExtractionService extends IService<CoreWalletExtraction> {

    /**
     * 新增钱包提现明细
     *
     * @param coreWalletExtractionSaveDto 钱包提现明细传输实体
     * @param appId                       平台id
     * @param organId                     组织id
     * @param userId                      操作者id
     * @return 返回是否成功
     */
    Boolean saveWalletExtraction(CoreWalletExtractionSaveDTO coreWalletExtractionSaveDto,
                                 Long appId, Long organId, Long userId);

    /**
     * 根据id修改钱包提现明细
     *
     * @param id                            提现明细id
     * @param coreWalletExtractionUpdateDTO 钱包账单传输实体
     * @param appId                         平台id
     * @param organId                       组织id
     * @param userId                        用户id
     * @return 返回修改结果
     */
    CoreWalletExtractionVO updateWalletExtractionById(Long id, CoreWalletExtractionUpdateDTO coreWalletExtractionUpdateDTO,
                                                      Long appId, Long organId, Long userId);

    /**
     * 根据id查询钱包提现明细详情
     *
     * @param id 钱包提现明细id
     * @return 返回查询结果
     */
    CoreWalletExtractionVO getWalletExtractionById(Long id);
}
