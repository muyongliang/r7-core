package com.r7.core.uim.controller;


import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.UimResourceSaveDto;
import com.r7.core.uim.dto.UimResourceUpdateDto;
import com.r7.core.uim.service.UimResourceService;
import com.r7.core.uim.vo.UimResourceNodeVo;
import com.r7.core.uim.vo.UimResourceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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


    @ApiOperation(value = "创建资源", response = UimResourceVo.class)
    @PostMapping("")
    public ResponseEntity saveUimResource(@RequestBody UimResourceSaveDto uimResourceSaveDto) {
        return ResponseEntity.success(uimResourceService.saveUimResource(uimResourceSaveDto, 0L, 0L));
    }


    @ApiOperation(value = "根据ID修改资源", response = UimResourceVo.class)
    @PutMapping("/{resourceId}")
    public ResponseEntity updateUimResource(@PathVariable("resourceId") Long resourceId,
                                            @RequestBody UimResourceUpdateDto uimResourceSaveDto) {
        return ResponseEntity.success(uimResourceService.updateUimResource(resourceId, uimResourceSaveDto, 0L));
    }

    @ApiOperation(value = "根据ID删除资源", response = Boolean.class)
    @DeleteMapping("/{resourceId}")
    public ResponseEntity removeUimResource(@PathVariable("resourceId") Long resourceId) {
        return ResponseEntity.success(uimResourceService.removeUimResource(resourceId, 0L));
    }


    @ApiOperation(value = "树形展示资源", response = UimResourceNodeVo.class)
    @GetMapping("/tree")
    public ResponseEntity treeUimResource() {
        return ResponseEntity.success(uimResourceService.treeUimResource(0L, 0L));
    }


    @ApiOperation(value = "根据ID获取资源详情", response = UimResourceVo.class)
    @GetMapping("/{resourceId}")
    public ResponseEntity getUimResourceById(@PathVariable("resourceId") Long resourceId) {
        return ResponseEntity.success(uimResourceService.getUimResourceById(resourceId));
    }

}
