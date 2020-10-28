package com.r7.core.assets.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.assets.model.CoreWallet;
import com.r7.core.assets.vo.CoreWalletVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author zs
 * @description: 核心钱包mapper层
 * @date : 2020-10-26
 */
public interface CoreWalletMapper extends BaseMapper<CoreWallet> {

    /**
     * 分页查询钱包
     *
     * @param page 分页
     * @return 返回查询结果
     */
    IPage<CoreWalletVO> pageWallet(@Param("page") Page<CoreWalletVO> page);
}
