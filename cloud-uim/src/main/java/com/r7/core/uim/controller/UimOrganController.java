package com.r7.core.uim.controller;

import com.r7.core.common.holder.RequestHolder;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.UimOrganSaveDTO;
import com.r7.core.uim.dto.UimOrganUpdateDTO;
import com.r7.core.uim.service.UimOrganService;
import com.r7.core.uim.vo.UimOrganNodeVO;
import com.r7.core.uim.vo.UimOrganVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 组织接口
 *
 * @author zhongpingli
 */
@Slf4j
@Api(value = "/api/organ", tags = {"组织接口"})
@RestController
@RequestMapping("/organ")
public class UimOrganController {

    @Resource
    private UimOrganService uimOrganService;


    @ApiOperation(value = "创建组织", response = UimOrganVO.class)
    @PostMapping("/{pId}")
    public ResponseEntity saveUimOrganByPid(@PathVariable("pId") Long pId,
                                            @Valid @RequestBody UimOrganSaveDTO uimOrganSaveDTO) {
        return ResponseEntity.success(uimOrganService.saveUimOrganByPid(pId, uimOrganSaveDTO,
                RequestHolder.getUserId(), RequestHolder.getAppId()));
    }

    @ApiOperation(value = "根据ID删除组织", response = Boolean.class)
    @DeleteMapping("/{id}")
    public ResponseEntity removeUimOrganById(@PathVariable("id") Long id) {
        return ResponseEntity.success(uimOrganService.removeUimOrganById(id,
                RequestHolder.getUserId(), RequestHolder.getAppId()));
    }

    @ApiOperation(value = "根据ID批量删除组织", response = Boolean.class)
    @DeleteMapping("")
    public ResponseEntity removeUimOrganByIds(@RequestBody List<Long> ids) {
        return ResponseEntity.success(uimOrganService.removeUimOrganByIds(ids,
                RequestHolder.getUserId(), RequestHolder.getAppId()));
    }

    @ApiOperation(value = "根据ID修改组织", response = UimOrganVO.class)
    @PutMapping("/{id}")
    public ResponseEntity updateUimOrganById(@PathVariable("id") Long id,
                                             @Valid @RequestBody UimOrganUpdateDTO uimOrganUpdateDTO) {
        return ResponseEntity.success(uimOrganService.updateUimOrganById(id, uimOrganUpdateDTO,
                RequestHolder.getUserId(), RequestHolder.getAppId()));
    }

    @ApiOperation(value = "树形展示组织", response = UimOrganNodeVO.class)
    @GetMapping("/tree")
    public ResponseEntity treeUimOrganNodeByPid() {
        return ResponseEntity.success(uimOrganService.treeUimOrganNodeByPid(RequestHolder.getOrganId(),
                RequestHolder.getAppId()));
    }

}
