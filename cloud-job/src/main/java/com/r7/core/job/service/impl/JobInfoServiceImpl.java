package com.r7.core.job.service.impl;

import com.r7.core.job.mapper.JobInfoMapper;
import com.r7.core.job.model.CoreJob;
import com.r7.core.job.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务实现层
 * @author zs
 */

@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoMapper jobInfoMapper;

    @Override
    public void add(CoreJob coreJob) {
        jobInfoMapper.add(coreJob);
    }

    @Override
    public void delete(Long id) {
        jobInfoMapper.delete(id);
    }

    @Override
    public void update(CoreJob coreJob) {
        jobInfoMapper.update(coreJob);
    }

    @Override
    public CoreJob findById(Long id) {
        return jobInfoMapper.findById(id);
    }

    @Override
    public List<CoreJob> findAll() {
        return jobInfoMapper.findAll();
    }
}
