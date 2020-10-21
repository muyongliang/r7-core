package com.r7.core.common.config;

import com.r7.core.common.filter.UserInfoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @author zhongpingli
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new UserInfoFilter());
        registration.addUrlPatterns("/*");
        registration.setName("userInfoFilter");
        registration.setOrder(1);
        return registration;
    }

}
