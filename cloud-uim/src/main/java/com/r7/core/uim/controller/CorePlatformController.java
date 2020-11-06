package com.r7.core.uim.controller;

import com.r7.core.common.holder.RequestHolder;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.CorePlatformDTO;
import com.r7.core.uim.service.CorePlatformService;
import com.r7.core.uim.vo.CorePlatformVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author wt
 * @Description 平台信息接口
 */
@Slf4j
@Api(value = "/api/platform", tags = {"平台信息接口"})
@RestController
@RequestMapping("/platform")
public class CorePlatformController {


    @Resource
    private CorePlatformService corePlatformService;

    @ApiOperation(value = "平台信息新增", response = CorePlatformVO.class)
    @PostMapping("/")
    public ResponseEntity saveUimSysUser(@Valid @RequestBody CorePlatformDTO corePlatformDTO) {
        return ResponseEntity.success(corePlatformService.saveCorePlatform(corePlatformDTO, RequestHolder.getUserId()));
    }


    @ApiOperation(value = "根据平台id修改平台的名称", response = CorePlatformVO.class)
    @PutMapping("/app/name/{id}")
    public ResponseEntity updateCorePlatformAppName(@PathVariable Long id,
                                                    @RequestParam String appName) {
        return ResponseEntity.success(corePlatformService.updateCorePlatformAppName(id,
                appName, RequestHolder.getUserId()));
    }

    @ApiOperation(value = "根据平台id修改平台描述", response = CorePlatformVO.class)
    @PutMapping("/description/{id}")
    public ResponseEntity updateCorePlatformDescription(@PathVariable Long id,
                                                        @RequestParam String description) {
        return ResponseEntity.success(corePlatformService.updateCorePlatformDescription(id,
                description, RequestHolder.getUserId()));
    }

    @ApiOperation(value = "根据平台id查询平台信息", response = CorePlatformVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getCorePlatformById(@PathVariable Long id) {
        return ResponseEntity.success(corePlatformService.getCorePlatformById(id));
    }

    @ApiOperation(value = "根据平台名称查询平台信息", response = CorePlatformVO.class)
    @GetMapping("/name")
    public ResponseEntity getCorePlatformByAppName(@RequestParam String appName) {
        return ResponseEntity.success(corePlatformService.getCorePlatformByAppName(appName));
    }

    @ApiOperation(value = "分页查询平台信息", response = CorePlatformVO.class)
    @GetMapping("/page")
    public ResponseEntity pagePlatform(@RequestParam(value = "appName", required = false) String appName,
                                       @RequestParam(defaultValue = "1", value = "pageNum") int pageNum,
                                       @RequestParam(defaultValue = "2", value = "pageSize") int pageSize) {
        return ResponseEntity.success(corePlatformService.pagePlatform(appName, pageNum, pageSize));
    }
}
