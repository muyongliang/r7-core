package com.r7.core.trigger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.model.QrtzCronTriggers;
import com.r7.core.trigger.mapper.QrtzCronTriggersMapper;
import com.r7.core.trigger.service.QrtzCronTriggersService;
/**
 * 
 * @Description 存储触发器的cron表达式实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class QrtzCronTriggersServiceImpl extends ServiceImpl<QrtzCronTriggersMapper, QrtzCronTriggers>
        implements QrtzCronTriggersService{

}
