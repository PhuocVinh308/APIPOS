package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/auth/addNewUser").allowedOrigins("*").allowedMethods("GET", "POST");
        registry.addMapping("/auth/Token").allowedOrigins("*").allowedMethods("GET", "POST");
        registry.addMapping("/api/products/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("Authorization");
        registry.addMapping("/api/table/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("Authorization");
        registry.addMapping("/api/customers/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("Authorization");
        registry.addMapping("/api/orders/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("Authorization");
        registry.addMapping("/api/order-items/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("Authorization");
        registry.addMapping("/api/employees/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("Authorization");
    }
}
