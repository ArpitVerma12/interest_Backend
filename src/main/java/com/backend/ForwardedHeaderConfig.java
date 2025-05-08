//package com.backend;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.web.filter.ForwardedHeaderFilter;
//
//@Configuration
//public class ForwardedHeaderConfig {
//
//    @Bean
//    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
//        FilterRegistrationBean<ForwardedHeaderFilter> filterRegBean = new FilterRegistrationBean<>();
//        filterRegBean.setFilter(new ForwardedHeaderFilter());
//        return filterRegBean;
//    }
//}
//
