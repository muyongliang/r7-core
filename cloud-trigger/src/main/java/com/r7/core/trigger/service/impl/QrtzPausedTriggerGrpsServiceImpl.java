package com.r7.core.trigger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.mapper.QrtzPausedTriggerGrpsMapper;
import com.r7.core.trigger.model.QrtzPausedTriggerGrps;
import com.r7.core.trigger.service.QrtzPausedTriggerGrpsService;
/**
 * 
 * @Description 存储已暂停的 Trigger 组的信息实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class QrtzPausedTriggerGrpsServiceImpl extends ServiceImpl<QrtzPausedTriggerGrpsMapper, QrtzPausedTriggerGrps>
        implements QrtzPausedTriggerGrpsService{

}
