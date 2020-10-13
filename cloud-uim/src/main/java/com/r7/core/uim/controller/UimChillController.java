package com.r7.core.uim.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.UimChillSaveListDTO;
import com.r7.core.uim.service.UimChillService;
import com.r7.core.uim.vo.UimChillVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zs
 * @description: 冻结/解冻接口
 * @date : 2020-10-13
 */
@Api(value = "/api/chill", tags = {"冻结/解冻接口"})
@Slf4j
@RestController
@RequestMapping("/chill")
public class UimChillController {

    @Resource
    private UimChillService uimChillService;

    @ApiOperation(value = "冻结/解冻", response = UimChillVO.class)
    @PostMapping("")
    public ResponseEntity frostUimChill(@Valid @RequestBody UimChillSaveListDTO uimChillSaveListDTO) {
        return ResponseEntity.success(uimChillService.frostUimChill(uimChillSaveListDTO, 0L, 0L, 0L));
    }

    @ApiOperation(value = "根据用户id查询冻结", response = UimChillVO.class)
    @GetMapping("/list/{userId}")
    public ResponseEntity listUimChillByUserId(@PathVariable Long userId) {
        return ResponseEntity.success(uimChillService.listChillByUserId(userId));
    }

}
