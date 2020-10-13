package com.r7.core.uim.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.UimChillSaveDTO;
import com.r7.core.uim.dto.UimChillUpdateDTO;
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

    @ApiOperation(value = "新增冻结", response = UimChillVO.class)
    @PostMapping("")
    public ResponseEntity saveUimChill(@Valid @RequestBody UimChillSaveDTO uimChillSaveDTO) {
        return ResponseEntity.success(uimChillService.saveUimChill(uimChillSaveDTO, 0L, 0L, 0L));
    }

    @ApiOperation(value = "修改冻结", response = UimChillVO.class)
    @PutMapping("/{id}")
    public ResponseEntity updateUimChill(@PathVariable Long id,
            @Valid @RequestBody UimChillUpdateDTO uimChillUpdateDTO) {
        return ResponseEntity.success(uimChillService.updateUimChill(id, uimChillUpdateDTO, 0L, 0L, 0L));
    }

    @ApiOperation(value = "根据id查询冻结", response = UimChillVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getUimChillById(@PathVariable Long id) {
        return ResponseEntity.success(uimChillService.getChillById(id));
    }

    @ApiOperation(value = "根据用户id查询冻结", response = UimChillVO.class)
    @GetMapping("/get/{userId}")
    public ResponseEntity getUimChillByUserId(@PathVariable Long userId) {
        return ResponseEntity.success(uimChillService.getChillByUserId(userId));
    }

}
