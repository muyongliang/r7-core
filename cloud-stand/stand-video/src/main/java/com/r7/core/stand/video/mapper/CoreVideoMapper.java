package com.r7.core.stand.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.stand.video.model.CoreVideo;
import com.r7.core.stand.video.vo.CoreVideoVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author zs
 * @description: 视频Mapper层
 * @date : 2020-10-10
 */
public interface CoreVideoMapper extends BaseMapper<CoreVideo> {

    /**
     * 分页查询
     * @param search 条件
     * @param page 分页
     * @return 分页结果
     */
    Page<CoreVideoVO> pageVideo(@Param("search") String search, @Param("page") Page<CoreVideoVO> page);
}
