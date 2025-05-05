package com.wapp.core.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //
                .allowedOriginPatterns("*") //
                .allowedMethods("POST", "GET", "DELETE", "PUT", "OPTIONS") //
                .allowedHeaders("Authorization", "Content-Type", "Accept") //
                .allowCredentials(true).maxAge(3600);
    }

}
