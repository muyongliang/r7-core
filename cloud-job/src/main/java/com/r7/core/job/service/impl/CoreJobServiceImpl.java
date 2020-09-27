package com.r7.core.job.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.job.mapper.CoreJobMapper;
import com.r7.core.job.model.CoreJob;
import com.r7.core.job.service.CoreJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 任务实现层
 * @author zs
 */

@Slf4j
@Service
public class CoreJobServiceImpl extends ServiceImpl<CoreJobMapper, CoreJob> implements CoreJobService {

}
