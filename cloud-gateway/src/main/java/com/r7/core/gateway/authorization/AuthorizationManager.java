package com.r7.core.gateway.authorization;

import com.google.common.collect.Lists;
import com.r7.core.cache.service.RedisListService;
import com.r7.core.gateway.constant.PermissionEnum;
import com.r7.core.gateway.constant.RedisConstant;
import com.r7.core.gateway.vo.UimResourceInfoVo;
import com.r7.core.gateway.vo.UimRoleResourceVO;
import io.vavr.control.Option;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.vavr.API.*;

/**
 * 鉴权管理
 *
 * @author zhongpingli
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Resource
    private RedisListService redisListService;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        return mono.map(auth -> new AuthorizationDecision(checkAuthorities(auth, request)))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }


    /**
     * 权限校验
     *
     * @param authentication 用户权限
     * @param request        请求
     */
    private boolean checkAuthorities(Authentication authentication, ServerHttpRequest request) {
        // 获取所有角色
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities == null || authorities.size() == 0) {
            return false;
        }
        // 获取请求方式
        String method = Objects.requireNonNull(request.getMethod()).name();
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
                    if (antPathMatcher.match(y.getUrl(), request.getURI().getPath())) {
                        return y.getPermission().equals(permissionEnum);
                    }
                    return false;
                }));
    }

}
