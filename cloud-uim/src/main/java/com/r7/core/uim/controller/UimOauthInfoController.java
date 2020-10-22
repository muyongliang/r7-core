package com.r7.core.uim.controller;

import com.r7.core.common.holder.RequestHolder;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.UimOauthDTO;
import com.r7.core.uim.dto.UimOauthInfoDTO;
import com.r7.core.uim.service.UimOauthInfoService;
import com.r7.core.uim.service.UimOauthService;
import com.r7.core.uim.vo.UimOauthInfoVO;
import com.r7.core.uim.vo.UimOauthVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zs
 * @description: 用户认证信息接口
 * @date : 2020-10-14
 */
@Api(value = "/api/oauth/info", tags = {"用户认证信息接口"})
@Slf4j
@RestController
@RequestMapping("/oauth/info")
public class UimOauthInfoController {

    @Resource
    private UimOauthInfoService uimOauthInfoService;

    @ApiOperation(
            value = "新增用户认证信息",
            notes = "userId必须存在",
            response = UimOauthInfoVO.class)
    @PostMapping("/{userId}")
    public ResponseEntity saveUimOauthInfo(@PathVariable Long userId,
                                           @RequestBody UimOauthInfoDTO uimOauthInfoDto) {
        return ResponseEntity.success(uimOauthInfoService
                .saveUimOauthInfo(userId, uimOauthInfoDto, RequestHolder.getAppId(), RequestHolder.getOrganId()));
    }

    @ApiOperation(value = "根据用户id查询认证信息", response = UimOauthVO.class)
    @GetMapping("/{userId}")
    public ResponseEntity getUimOauthInfo(@PathVariable Long userId) {
        return ResponseEntity.success(uimOauthInfoService.getUimOauthInfoByUserId(userId));
    }

}
