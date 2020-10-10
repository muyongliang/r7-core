package com.r7.core.trigger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.model.QrtzFiredTriggers;
import com.r7.core.trigger.mapper.QrtzFiredTriggersMapper;
import com.r7.core.trigger.service.QrtzFiredTriggersService;
/**
 * 
 * @Description 存储已触发Trigger状态信息及Job 的执行信息实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class QrtzFiredTriggersServiceImpl extends ServiceImpl<QrtzFiredTriggersMapper, QrtzFiredTriggers>
        implements QrtzFiredTriggersService{

}
