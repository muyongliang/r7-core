package com.r7.core.uim.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.web.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 登录成功相应
 *
 * @author zhongpingli
 */
@Slf4j
@Component("uimAuthenticationSuccessHandler")
public class UimAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private ClientDetailsService clientDetailsService;

    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.security.web.authentication
     * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        log.info("登录成功:{}", authentication);

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Basic")) {
            throw new BusinessException("request_header_is_not_client", "请求头中无client信息");
        }

        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;

        passwordEncoder.encode("123");
        String clientId = tokens[0];
        String clientSecret = tokens[1];

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new BusinessException("client_id_is_not_exists", "clientId对应的配置信息不存在:" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new BusinessException("client_secret_error", "clientSecret不匹配:" + clientId);
        }

        TokenRequest tokenRequest =
                new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ResponseEntity.success(token)));

    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) {

        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("", "Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BusinessException("", "Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }


}
