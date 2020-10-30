package com.r7.core.uim.service.impl;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.r7.core.cache.service.RedisListService;
import com.r7.core.uim.constant.PermissionEnum;
import com.r7.core.uim.constant.RedisConstant;
import com.r7.core.uim.service.RbacService;
import com.r7.core.uim.vo.UimResourceInfoVo;
import com.r7.core.uim.vo.UimRoleResourceVO;
import io.vavr.control.Option;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.*;

/**
 * rbac服务实现
 *
 * @author zhongpingli
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private RedisListService redisListService;

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        // 访问的是否为公共资源
        boolean exists = publicResourceCheck(request.getRequestURI());
        if (exists) {
            return true;
        }
        // 获取所有角色
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities == null || authorities.size() == 0) {
            return false;
        }
        // 获取请求方式
        String method = request.getMethod();
        PermissionEnum permissionEnum = Match(method).of(
                Case($("GET"), PermissionEnum.BROWSE),
                Case($("PUT"), PermissionEnum.UPDATE),
                Case($("POST"), PermissionEnum.CREATE),
                Case($("DELETE"), PermissionEnum.DELETE),
                Case($(), PermissionEnum.OTHER)
        );
        if (permissionEnum.equals(PermissionEnum.OTHER)) {
            return false;
        }
        List<String> roleCodes = authorities.stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 获取用户角色对应的资源
        List<Object> redisListServiceKey = redisListService.getKey(RedisConstant.REDIS_RESOURCE_ROLE_KEY);
        List<UimRoleResourceVO> uimRoleResourceVos = redisListServiceKey.stream()
                .map(x -> JSONUtil.toBean(x.toString(), UimRoleResourceVO.class)).collect(Collectors.toList());
        List<UimResourceInfoVo> uimResourceInfoVoList = Lists.newArrayListWithExpectedSize(uimRoleResourceVos.size());
        roleCodes.forEach(x ->
                uimResourceInfoVoList.addAll(uimRoleResourceVos.stream()
                        .filter(y -> y.getRoleCode().equals(x))
                        .map(UimRoleResourceVO::toUimResourceInfoVo).collect(Collectors.toList()))
        );

        return Option.of(uimResourceInfoVoList).exists(x ->
                x.stream().anyMatch(y -> {
                    if (antPathMatcher.match(y.getUrl(), request.getRequestURI())) {
                        return y.getPermission().getValue().equals(permissionEnum.getValue());
                    }
                    return false;
                }));
    }

    /**
     * 公共资源
     *
     * @param url 访问路径
     * @return 返回是否为公共资源
     */
    private boolean publicResourceCheck(String url) {
        // 只需要登录就能访问的资源
        // 获取所有受保护的资源
        List<String> resourceUrls = redisListService.getKey(RedisConstant.REDIS_RESOURCE_KEY)
                .stream().map(Object::toString).collect(Collectors.toList());
        // 访问路径不在受保护资源中返回true
        return Option.of(resourceUrls).exists(x ->
                x.stream().noneMatch(y -> antPathMatcher.match(y, url)));
    }

}