package com.r7.core.trigger.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.r7.core.trigger.model.QrtzFiredTriggers;

/**
 * 
 * @Description 存储已触发Trigger状态信息及Job 的执行信息mapper层
 * @author wutao
 * @date 2020/9/28
 */
public interface QrtzFiredTriggersMapper extends BaseMapper<QrtzFiredTriggers> {
}