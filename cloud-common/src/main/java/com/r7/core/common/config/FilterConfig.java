package com.r7.core.common.config;

import com.r7.core.common.filter.UserInfoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * 过滤器配置
 *
 * @author zhongpingli
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> registFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
        registration.setFilter(new UserInfoFilter());
        registration.addUrlPatterns("/*");
        registration.setName("userInfoFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }

}
