package com.r7.core.trigger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.model.QrtzLocks;
import com.r7.core.trigger.mapper.QrtzLocksMapper;
import com.r7.core.trigger.service.QrtzLocksService;
/**
 * 
 * @Description 存储程序的锁的信息实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class QrtzLocksServiceImpl extends ServiceImpl<QrtzLocksMapper, QrtzLocks>
        implements QrtzLocksService{

}
