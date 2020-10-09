package com.r7.core.trigger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.mapper.QrtzSimpropTriggersMapper;
import com.r7.core.trigger.model.QrtzSimpropTriggers;
import com.r7.core.trigger.service.QrtzSimpropTriggersService;
/**
 * 
 * @Description 存储CalendarIntervalTrigger和DailyTimeIntervalTrigger实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class QrtzSimpropTriggersServiceImpl extends ServiceImpl<QrtzSimpropTriggersMapper, QrtzSimpropTriggers>
        implements QrtzSimpropTriggersService{

}
