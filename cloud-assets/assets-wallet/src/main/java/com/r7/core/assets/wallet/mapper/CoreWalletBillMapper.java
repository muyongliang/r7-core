package com.r7.core.assets.wallet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.assets.wallet.model.CoreWalletBill;
import com.r7.core.assets.wallet.vo.CoreWalletBillPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author zs
 * @description: 钱包账单mapper层
 * @date : 2020-10-26
 */
public interface CoreWalletBillMapper extends BaseMapper<CoreWalletBill> {

    /**
     * 分页查询钱包账单
     *
     * @param userId 用户id
     * @param page   分页
     * @return 返回查询结果
     */
    IPage<CoreWalletBillPageVO> pageWalletBill(@Param("userId") Long userId, @Param("page") Page<CoreWalletBillPageVO> page);
}
