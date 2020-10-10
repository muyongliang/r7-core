package com.r7.core.stand.video.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.stand.video.mapper.CoreVideoMapper;
import com.r7.core.stand.video.model.CoreVideo;
import com.r7.core.stand.video.service.CoreVideoService;
import com.r7.core.stand.video.vo.CoreVideoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author zs
 * @description:
 * @date : 2020-10-10
 */
@Slf4j
@Service
public class CoreVideoServiceImpl extends ServiceImpl<CoreVideoMapper, CoreVideo> implements CoreVideoService {

    @Override
    public CoreVideoVO getVideoById(Long id) {
        CoreVideoVO coreVideoVO = new CoreVideoVO();
        CoreVideo coreVideo = baseMapper.selectById(id);
        BeanUtils.copyProperties(coreVideo, coreVideoVO);
        return coreVideoVO;
    }

    @Override
    public Page<CoreVideoVO> pageVideo(String search, Integer pageNum, Integer pageSize) {
        Page<CoreVideoVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageVideo(search, page);
    }
}
