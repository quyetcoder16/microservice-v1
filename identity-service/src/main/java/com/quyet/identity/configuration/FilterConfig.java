package com.quyet.identity.configuration;

import com.quyet.identity.filter.RequestLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> loggingFilterRegistration(RequestLoggingFilter loggingFilter) {
        FilterRegistrationBean<RequestLoggingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(loggingFilter);
        registration.addUrlPatterns("/*"); // Áp dụng cho tất cả URL
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE); // -2, trước Security (-1)
        return registration;
    }

}
