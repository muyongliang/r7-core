package com.r7.core.trigger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.mapper.QrtzBlobTriggersMapper;
import com.r7.core.trigger.model.QrtzBlobTriggers;
import com.r7.core.trigger.service.QrtzBlobTriggersService;
/**
 * 
 * @Description  存储自定义的Trigger 类型实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class QrtzBlobTriggersServiceImpl extends ServiceImpl<QrtzBlobTriggersMapper, QrtzBlobTriggers>
        implements QrtzBlobTriggersService{

}
