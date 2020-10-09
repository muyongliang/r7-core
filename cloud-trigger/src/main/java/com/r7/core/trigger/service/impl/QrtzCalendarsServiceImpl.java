package com.r7.core.trigger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.mapper.QrtzCalendarsMapper;
import com.r7.core.trigger.model.QrtzCalendars;
import com.r7.core.trigger.service.QrtzCalendarsService;
/**
 * 
 * @Description 存储存放日历信息实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class QrtzCalendarsServiceImpl extends ServiceImpl<QrtzCalendarsMapper, QrtzCalendars>
        implements QrtzCalendarsService{

}
