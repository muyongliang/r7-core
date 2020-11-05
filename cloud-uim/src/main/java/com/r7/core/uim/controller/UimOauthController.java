package com.r7.core.uim.controller;

import com.r7.core.common.holder.RequestHolder;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.UimOauthDTO;
import com.r7.core.uim.model.UimOauth;
import com.r7.core.uim.service.UimOauthService;
import com.r7.core.uim.vo.UimOauthVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zs
 * @description: 用户认证接口
 * @date : 2020-10-14
 */
@Api(value = "/api/user/oauth", tags = {"用户认证接口"})
@Slf4j
@RestController
@RequestMapping("/user/oauth")
public class UimOauthController {

    @Resource
    private UimOauthService uimOauthService;

    @ApiOperation(value = "新增用户认证", response = UimOauthVO.class)
    @PostMapping("/")
    public ResponseEntity saveUimOauth(@Valid @RequestBody UimOauthDTO uimOauthDto) {
        return ResponseEntity.success(uimOauthService.saveUimOauth(uimOauthDto, RequestHolder.getAppId(),
                RequestHolder.getOrganId(), RequestHolder.getUserId()));
    }

    @ApiOperation(value = "根据用户id查询认证信息", response = UimOauthVO.class)
    @GetMapping("/list/{userId}")
    public ResponseEntity listUimOauth(@PathVariable Long userId) {
        return ResponseEntity.success(uimOauthService.listUimOauth(userId));
    }


}
