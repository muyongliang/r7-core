package com.r7.core.common.fegin;

import com.r7.core.common.web.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wt
 * @Description 积分服务
 */
@Component
@FeignClient("assets-integral")
public interface IntegralFeign {

    /**
     * 新增积分信息
     *
     * @param userId 用户id
     * @param total  用户的总积分
     * @return 返回新增结果
     */
    @PostMapping("")
    ResponseEntity saveCoreIntegralDetail(@RequestParam Long userId, @RequestParam Integer total);


}
