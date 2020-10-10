package com.r7.core.stand.video.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.stand.video.vo.CoreVideoVO;

/**
 * @author zs
 * @description: 视频服务层
 * @date : 2020-10-10
 */


public interface CoreVideoService {

    /**
     * 根据视频id查询详细信息
     * @param id 视频id
     * @return 视频详细信息
     */
    CoreVideoVO getVideoById(Long id);

    /**
     * 分页查询
     * @param search 条件
     * @param pageNum 当前页
     * @param pageSize 页数
     * @return 查询分页结果
     */
    Page<CoreVideoVO> pageVideo(String search, Integer pageNum, Integer pageSize);
}
