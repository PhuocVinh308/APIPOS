package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Cấu hình CORS cho các yêu cầu liên quan đến `auth`
        registry.addMapping("/auth/**")
                .allowedOrigins("http://localhost:3000") // Chỉ cho phép từ nguồn cụ thể
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true) // Cho phép sử dụng chứng thực (credentials)
                .maxAge(3600);

        // Cấu hình CORS cho các yêu cầu liên quan đến `api`
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000") // Chỉ cho phép từ nguồn cụ thể
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Authorization", "Content-Type") // Chỉ định các tiêu đề HTTP được phép
                .allowCredentials(true) // Cho phép sử dụng chứng thực (credentials)
                .maxAge(3600);
    }
}
