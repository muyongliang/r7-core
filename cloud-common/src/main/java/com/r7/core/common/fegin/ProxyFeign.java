package com.r7.core.common.fegin;

import com.r7.core.common.web.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wt
 * @Description 层级远程调用服务
 */
@Component
@FeignClient("dividend-proxy")
public interface ProxyFeign {

    /**
     * 新增层级
     *
     * @param pId     父id即邀请人id
     * @param userId  注册的用户id
     * @param organId 邀请人的组织id
     * @return 新增结果
     */
    @PostMapping("/api/proxy")
    ResponseEntity saveCoreProxy(@RequestParam Long pId, @RequestParam Long userId, @RequestParam Long organId);

}
