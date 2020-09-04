package com.r7.core.uims.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security配置
 *
 * @author mrli
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * swagger过滤文件
     */
    private static final String[] SWAGGER_AUTH_LIST = {
            "/v2/api-docs",
            "/swagger-resources/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    /**
     * 系统过滤地址
     */
    private static final String[] SYSTEM_AUTH_LIST = {"/auth/**"};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(SWAGGER_AUTH_LIST)
                .permitAll()
                .antMatchers(SYSTEM_AUTH_LIST)
                .permitAll()
                .anyRequest()
//                .access("@rbacService.hasPermission(request,authentication)") // todo rbac需要用都的 目前资源还未增加不使用
                .authenticated()
                .and()
                .formLogin()
                .permitAll();
    }

}
