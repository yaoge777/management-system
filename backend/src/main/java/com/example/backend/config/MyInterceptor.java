package com.example.backend.config;

import com.example.backend.interceptor.JwtValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


public class MyInterceptor implements WebMvcConfigurer {

    //    @Autowired
    private JwtValidateInterceptor jvi;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(jvi);
        interceptorRegistration.addPathPatterns("/**")
                .excludePathPatterns(
                        "user/login/",
                        "user/logout"
                );

    }
}
