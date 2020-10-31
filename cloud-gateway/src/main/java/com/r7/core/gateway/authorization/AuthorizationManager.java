package com.r7.core.gateway.authorization;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.r7.core.cache.service.RedisListService;
import com.r7.core.gateway.constant.PermissionEnum;
import com.r7.core.gateway.constant.RedisConstant;
import com.r7.core.gateway.vo.UimChillInfoVO;
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
import java.util.Map;
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
        // 是否为冻结资源
        Map map = JSONUtil.toBean(JSONUtil.parseObj(authentication.getPrincipal()), Map.class);
        Object claims = map.get("claims");
        Map userInfo = JSONUtil.toBean(JSONUtil.parseObj(claims), Map.class);
        boolean chill = resourceChillCheck(Long.valueOf(userInfo.get("id").toString()), request.getURI().getPath());
        if (chill) {
            return false;
        }
        // 访问的是否为公共资源
        boolean exists = publicResourceCheck(request.getURI().getPath());
        if (exists) {
            return true;
        }
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
                    if (antPathMatcher.match(y.getUrl(), request.getURI().getPath())) {
                        return y.getPermission().equals(permissionEnum);
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

    /**
     * 是否有冻结资源
     *
     * @param url 访问资源url
     * @return 返回是否冻结
     */
    private boolean resourceChillCheck(Long userId, String url) {
        List<Object> resourceUrls = redisListService.getKey(RedisConstant.REDIS_CHILL_RESOURCE_KEY);
        return Option.of(resourceUrls).exists(resourceUrl -> {
            List<UimChillInfoVO> uimRoleResourceVOList = resourceUrl.stream()
                    .map(x -> JSONUtil.toBean(x.toString(), UimChillInfoVO.class))
                    .collect(Collectors.toList());
            List<String> checkResourceIds = uimRoleResourceVOList.stream().filter(x -> x.getUserId().equals(userId))
                    .flatMap(x -> x.getResourceUrl().stream()).collect(Collectors.toList());
            return checkResourceIds.stream().anyMatch(y -> antPathMatcher.match(y, url));
        });

    }

}
