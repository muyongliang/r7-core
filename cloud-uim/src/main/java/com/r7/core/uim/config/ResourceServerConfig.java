package com.r7.core.uim.config;

import com.r7.core.uim.handler.UimAuthenticationFailureHandler;
import com.r7.core.uim.handler.UimAuthenticationSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.annotation.Resource;

/**
 * 资源服务器配置
 *
 * @author mrli
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * swagger过滤文件
     */
    private static final String[] SWAGGER_AUTH_LIST = {
            "/v2/api-docs",
            "/swagger-resources/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
    };

    /**
     * 系统过滤地址
     */
    private static final String[] SYSTEM_AUTH_LIST = {
            "/oauth/**",
            "/sign/**"};
    @Resource
    private UimAuthenticationFailureHandler uimAuthenticationFailureHandler;

    @Resource
    private UimAuthenticationSuccessHandler uimAuthenticationSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(SWAGGER_AUTH_LIST)
                .permitAll()
                .antMatchers(SYSTEM_AUTH_LIST)
                .permitAll()
                .anyRequest()
                .access("@rbacService.hasPermission(request,authentication)")
                .and()
                .formLogin()
                .loginPage("/oauth/require")
                .loginProcessingUrl("/oauth/singin")
                .successHandler(uimAuthenticationSuccessHandler)
                .failureHandler(uimAuthenticationFailureHandler)
                .permitAll();
    }
}
