package com.r7.core.profit.service;

import java.time.LocalDateTime;

/**
 * @author wt
 * @Description 定时核算service
 */
public interface SettlementService {

    /**
     * 定时核算指定时间以前的未核算过的分润明细，并添加发放记录，状态为待发放
     * @param endTime 截止时间
     * @return 返回核算结果
     */
    int  settlementAll(LocalDateTime endTime);

}
