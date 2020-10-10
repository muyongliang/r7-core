package com.r7.core.trigger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.model.QrtzTriggers;
import com.r7.core.trigger.mapper.QrtzTriggersMapper;
import com.r7.core.trigger.service.QrtzTriggersService;
/**
 * 
 * @Description 保存触发器的基本信息的实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class QrtzTriggersServiceImpl extends ServiceImpl<QrtzTriggersMapper, QrtzTriggers>
        implements QrtzTriggersService{

}
