package com.r7.core.proxy.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.proxy.dto.CoreProxyDTO;
import com.r7.core.proxy.dto.CoreProxyUpdateDTO;
import com.r7.core.proxy.service.CoreProxyService;
import com.r7.core.proxy.vo.CoreProxyNodeVO;
import com.r7.core.proxy.vo.CoreProxyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author wutao
 * @Description 代理层级接口
 *
 */
@Slf4j
@Api(value = "/api/proxy", tags = {"代理层级接口"})
@RestController
@RequestMapping("/proxy")
public class CoreProxyController {

    @Autowired
    private CoreProxyService coreProxyService;

    @ApiOperation(value = "新增层级", response = CoreProxyVO.class)
    @PostMapping("")
    public ResponseEntity saveUimResource(@Valid @RequestBody CoreProxyDTO coreProxyDTO) {
        return ResponseEntity.success(coreProxyService.saveCoreProxy(coreProxyDTO,1L));
    }

    @ApiOperation(value = "根据层级ID获取层级信息", response = CoreProxyVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getCoreProxyById(@PathVariable("id") Long id) {
        return ResponseEntity.success(coreProxyService.getCoreProxyById(id,
                1311197443478200378L));
    }

    @ApiOperation(value = "根据用户ID获取层级信息", response = CoreProxyVO.class)
    @GetMapping("/user/id")
    public ResponseEntity getCoreProxyByUserId(@RequestParam Long userId) {
        return ResponseEntity.success(coreProxyService.getCoreProxyByUserId(userId,
                1311197443478200378L));
    }

    @ApiOperation(value = "根据层级ID修改层级信息", response = CoreProxyVO.class)
    @PutMapping("/")
    public ResponseEntity updateCoreProxy(@RequestParam Long id,
                                          @RequestBody CoreProxyUpdateDTO coreProxyUpdateDto) {
        return ResponseEntity.success(coreProxyService.updateCoreProxy(id,
                coreProxyUpdateDto,3L));
    }

    @ApiOperation(value = "树形展示层级", response = CoreProxyNodeVO.class)
    @GetMapping("/tree")
    public ResponseEntity treeCoreProxyByPid(@RequestParam Long parentId) {
        return ResponseEntity.success(coreProxyService.treeCoreProxyByPid(parentId,
                1311197443478200378L));
    }

    @ApiOperation(value = "根据用户ID统计该用户的下级人数", response = CoreProxyVO.class)
    @PostMapping("/count")
    public ResponseEntity countSubordinateNumByUserId(@RequestParam Long userId) {
        return ResponseEntity.success(coreProxyService.countSubordinateNumByUserId(userId,
                1311197443478200378L));
    }

    @ApiOperation(value = "根据层级ID修改层级值", response = CoreProxyVO.class)
    @PutMapping("/level")
    public ResponseEntity updateCoreProxyLevelById(@RequestParam Long id,
                                                   @RequestParam Integer level) {
        return ResponseEntity.success(coreProxyService.updateCoreProxyLevelById(id,level,
                1311197443478200378L,6L));
    }

    @ApiOperation(value = "把某个节点改到指定节点下面", response = CoreProxyNodeVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "要移动的层级id"),
            @ApiImplicitParam(name = "userId",value = "目标层级的用户id")
    })
    @PutMapping("/proxy/level")
    public ResponseEntity updateCoreProxyLevel(@RequestParam Long id,
                                               @RequestParam Long userId) {
        return ResponseEntity.success(coreProxyService.updateCoreProxyLevel(id,userId,
                1311197443478200378L,9L));
    }




    //============以下是不对外开放接口，测试用================


    @ApiOperation(value = "根据用户ID修改用户下级人数", response = CoreProxyVO.class)
    @PutMapping("/subordinate/num")
    public ResponseEntity updateSubordinateNumById(@RequestParam Long userId,
                                                   @RequestParam Integer subordinateNum) {
        return ResponseEntity.success(coreProxyService.updateSubordinateNumById(userId,
                subordinateNum,1311197443478200378L,3L));
    }

    @ApiOperation(value = "得到指定用户的所有下级", response = CoreProxyVO.class)
    @GetMapping("/all/proxy/user")
    public ResponseEntity getAllSubordinateCoreProxy(
            @RequestParam Long userId,
            @RequestParam Long organId) {
        return ResponseEntity.success(coreProxyService.getAllSubordinateCoreProxy(userId,organId));
    }


    @ApiOperation(value = "验证目标层级是否存在于移动层级的下级集合中",
            response = Boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "要移动的层级id"),
            @ApiImplicitParam(name = "userId",value = "目标层级的用户id")
    })
    @PostMapping("/check")
    public ResponseEntity checkCoreProxyIsExistList(
            @RequestParam Long userId,
            @RequestParam Long id) {
        return ResponseEntity.success(coreProxyService.checkCoreProxyIsExistList(id,userId,
                1311197443478200378L));
    }

    @ApiOperation(value = "根据层级ID修改层级的父ID", response = CoreProxyVO.class)
    @PutMapping("/pid")
    public ResponseEntity updateCoreProxyPidById(@RequestParam Long id,
                                                 @RequestParam Long userId) {
        return ResponseEntity.success(coreProxyService.updateCoreProxyPidById(id,userId,
                1311197443478200378L,6L));
    }

    @ApiOperation(value = "重新计算并修改指定组织下节点的层级值", response = CoreProxyVO.class)
    @PutMapping("/organ/all")
    public ResponseEntity updateAndCountCoreProxyLevelByOrganId(@RequestParam Long organId) {
        return ResponseEntity.success(coreProxyService.updateAndCountCoreProxyLevelByOrganId(organId,8L));
    }

    @ApiOperation(value = "根据组织ID查询该组织下所有层级", response = CoreProxyVO.class)
    @GetMapping("/organ/id")
    public ResponseEntity getAllCountCoreProxyByOrganId(@RequestParam Long organId) {
        return ResponseEntity.success(coreProxyService.getAllCountCoreProxyByOrganId(organId));
    }

    @ApiOperation(value = "计算某个节点的层级", response = CoreProxyVO.class)
    @PostMapping("/user/id")
    public ResponseEntity countLevel(@RequestParam Long userId) {
        return ResponseEntity.success(coreProxyService.countLevel(userId,1311197443478200378L));
    }

    @ApiOperation(value = "根据层级父ID查询直接下级层级信息", response = CoreProxyVO.class)
    @GetMapping("/")
    public ResponseEntity getCoreProxyByParentId(@RequestParam Long parentId) {
        return ResponseEntity.success(coreProxyService.getCoreProxyByParentId(parentId));
    }
}
