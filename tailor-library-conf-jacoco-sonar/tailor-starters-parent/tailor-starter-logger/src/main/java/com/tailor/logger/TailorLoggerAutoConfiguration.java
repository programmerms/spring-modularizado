package com.tailor.logger;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class TailorLoggerAutoConfiguration {

    @Bean
    public FilterRegistrationBean<TailorRequestLoggingFilter> tailorRequestLoggingFilter() {
        FilterRegistrationBean<TailorRequestLoggingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TailorRequestLoggingFilter());
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        return registration;
    }
}