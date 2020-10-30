package com.r7.core.uim.controller;


import com.r7.core.common.holder.RequestHolder;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.UimResourceSaveDTO;
import com.r7.core.uim.dto.UimResourceUpdateDTO;
import com.r7.core.uim.service.UimResourceService;
import com.r7.core.uim.vo.UimResourceNodeVO;
import com.r7.core.uim.vo.UimResourceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 资源接口
 *
 * @author zhongpingli
 */
@Slf4j
@Api(value = "/api/resource", tags = {"资源接口"})
@RestController
@RequestMapping("/resource")
public class UimResourceController {

    @Resource
    private UimResourceService uimResourceService;


    @ApiOperation(value = "创建资源", response = UimResourceVO.class)
    @PostMapping("")
    public ResponseEntity saveUimResource(@Valid @RequestBody UimResourceSaveDTO uimResourceSaveDto) {
        return ResponseEntity.success(uimResourceService.saveUimResource(uimResourceSaveDto,
                RequestHolder.getAppId(), RequestHolder.getUserId()));
    }


    @ApiOperation(value = "根据ID修改资源", response = UimResourceVO.class)
    @PutMapping("/{resourceId}")
    public ResponseEntity updateUimResource(@PathVariable("resourceId") Long resourceId,
                                            @Valid @RequestBody UimResourceUpdateDTO uimResourceSaveDto) {
        return ResponseEntity.success(uimResourceService.updateUimResource(resourceId, uimResourceSaveDto,
                RequestHolder.getAppId(), RequestHolder.getUserId()));
    }

    @ApiOperation(value = "根据ID删除资源", response = Boolean.class)
    @DeleteMapping("/{resourceId}")
    public ResponseEntity removeUimResource(@PathVariable("resourceId") Long resourceId) {
        return ResponseEntity.success(uimResourceService.removeUimResource(resourceId,
                RequestHolder.getUserId(), RequestHolder.getAppId()));
    }


    @ApiOperation(value = "树形展示资源", response = UimResourceNodeVO.class)
    @GetMapping("/tree")
    public ResponseEntity treeUimResource() {
        return ResponseEntity.success(uimResourceService.treeUimResource(RequestHolder.getAppId(), 0L));
    }


    @ApiOperation(value = "根据ID获取资源详情", response = UimResourceVO.class)
    @GetMapping("/{resourceId}")
    public ResponseEntity getUimResourceById(@PathVariable("resourceId") Long resourceId) {
        return ResponseEntity.success(uimResourceService.getUimResourceById(resourceId, RequestHolder.getAppId()));
    }

}
