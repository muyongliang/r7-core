package com.r7.core.trigger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.model.QrtzSimpleTriggers;
import com.r7.core.trigger.mapper.QrtzSimpleTriggersMapper;
import com.r7.core.trigger.service.QrtzSimpleTriggersService;
/**
 * 
 * @Description 存储简单的Trigger包括重复次数等实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class QrtzSimpleTriggersServiceImpl extends ServiceImpl<QrtzSimpleTriggersMapper, QrtzSimpleTriggers>
        implements QrtzSimpleTriggersService{

}
