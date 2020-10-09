package com.r7.core.trigger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.model.QrtzJobDetails;
import com.r7.core.trigger.mapper.QrtzJobDetailsMapper;
import com.r7.core.trigger.service.QrtzJobDetailsService;
/**
 * 
 * @Description quartz自带jobDetail 的详细信息实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class QrtzJobDetailsServiceImpl extends ServiceImpl<QrtzJobDetailsMapper, QrtzJobDetails>
        implements QrtzJobDetailsService{

}
