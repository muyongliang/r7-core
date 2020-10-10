package com.r7.core.proxy.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wutao
 * @Description 代理层级接口
 * @date 2020/10/9
 */
@Slf4j
@Api(value = "/api/proxy", tags = {"代理层级接口"})
@RestController
@RequestMapping("/proxy")
public class CoreProxyController {

}
