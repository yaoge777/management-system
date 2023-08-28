package com.example.backend.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class MyCoreConfig {

    @Bean
    public CorsFilter cf(){
        CorsConfiguration cc = new CorsConfiguration();
        cc.addAllowedOrigin("http://localhost:8080");
        cc.setAllowCredentials(true);
        cc.addAllowedMethod("*");
        cc.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource ubccs = new UrlBasedCorsConfigurationSource();
        ubccs.registerCorsConfiguration("/**", cc);
        return new CorsFilter(ubccs);
    }
}