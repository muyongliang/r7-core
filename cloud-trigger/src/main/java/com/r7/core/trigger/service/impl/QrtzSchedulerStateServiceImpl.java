package com.r7.core.trigger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.model.QrtzSchedulerState;
import com.r7.core.trigger.mapper.QrtzSchedulerStateMapper;
import com.r7.core.trigger.service.QrtzSchedulerStateService;
/**
 * 
 * @Description 调度实例的基本信息实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class QrtzSchedulerStateServiceImpl extends ServiceImpl<QrtzSchedulerStateMapper, QrtzSchedulerState>
        implements QrtzSchedulerStateService{

}
