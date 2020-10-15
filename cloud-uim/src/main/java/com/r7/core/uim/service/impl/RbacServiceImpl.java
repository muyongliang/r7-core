package com.r7.core.uim.service.impl;

import com.r7.core.uim.service.RbacService;
import com.r7.core.uim.service.UimRoleResourceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * rbac服务实现
 *
 * @author zhongpingli
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private UimRoleResourceService uimRoleResourceService;

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof UserDetails) {
            // 获取所有角色
            Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();
            if (authorities == null || authorities.size() == 0) {
                return false;
            }
            List<String> roleCodes = authorities.stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            // 获取用户角色对应的资源
            List<String> resourceUrls = uimRoleResourceService.listResourceUrlByRoleCodes(roleCodes);
            for (String resourceUrl : resourceUrls) {
                if (antPathMatcher.match(resourceUrl, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }

        return hasPermission;
    }

}