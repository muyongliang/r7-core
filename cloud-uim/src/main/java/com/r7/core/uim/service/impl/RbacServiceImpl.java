package com.r7.core.uim.service.impl;

import com.google.common.collect.Lists;
import com.r7.core.cache.service.RedisListService;
import com.r7.core.uim.constant.PermissionEnum;
import com.r7.core.uim.constant.RedisConstant;
import com.r7.core.uim.service.RbacService;
import com.r7.core.uim.service.UimRoleResourceService;
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
        List<UimRoleResourceVO> uimRoleResourceVos = redisListService.getKey(RedisConstant.REDIS_RESOURCE_ROLE_KEY,
                UimRoleResourceVO.class);
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

}