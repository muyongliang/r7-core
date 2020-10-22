package com.r7.core.uim.controller;

import com.r7.core.common.holder.RequestHolder;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.UimOauthOrderDTO;
import com.r7.core.uim.service.UimOauthOrderService;
import com.r7.core.uim.vo.UimOauthOrderVO;
import com.r7.core.uim.vo.UimOauthVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zs
 * @description: 用户认证订单接口
 * @date : 2020-10-14
 */
@Api(value = "/api/oauth/order", tags = {"用户认证订单接口"})
@Slf4j
@RestController
@RequestMapping("/oauth/order")
public class UimOauthOrderController {

    @Resource
    private UimOauthOrderService uimOauthOrderService;

    @ApiOperation(value = "新增用户认证订单", response = UimOauthOrderVO.class)
    @PostMapping("/")
    public ResponseEntity saveUimOauthOrder(@RequestBody UimOauthOrderDTO uimOauthOrderDto) {
        return ResponseEntity.success(uimOauthOrderService
                .saveUimOauthOrder(uimOauthOrderDto, RequestHolder.getAppId(),
                        RequestHolder.getOrganId(), RequestHolder.getUserId()));
    }

    @ApiOperation(value = "修改用户认证订单", response = UimOauthOrderVO.class)
    @PutMapping("/{id}")
    public ResponseEntity updateUimOauthOrder(@PathVariable Long id,
                                              @RequestBody UimOauthOrderDTO uimOauthOrderDto) {
        return ResponseEntity.success(uimOauthOrderService
                .updateUimOauthOrder(id, uimOauthOrderDto, RequestHolder.getAppId(),
                        RequestHolder.getOrganId(), RequestHolder.getUserId()));
    }

    @ApiOperation(value = "根据id查询认证信息", response = UimOauthVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getUimOauthOrder(@PathVariable Long id) {
        return ResponseEntity.success(uimOauthOrderService.getUimOauthOrderById(id));
    }


}
