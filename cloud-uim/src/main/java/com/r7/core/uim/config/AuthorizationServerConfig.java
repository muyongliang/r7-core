package com.r7.core.uim.config;

import com.r7.core.uim.service.impl.UimUserServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;


/**
 * 认证服务器
 *
 * @author mrli
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UimUserServiceImpl userServiceImpl;

    @Resource(name = "jwtTokenStore")
    private TokenStore tokenStore;

    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("uim")
                .secret(passwordEncoder.encode("uim123"))
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(864000)
                .scopes("all")
                .authorizedGrantTypes("authorization_code", "password", "refresh_token");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userServiceImpl)
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter);
    }

}
