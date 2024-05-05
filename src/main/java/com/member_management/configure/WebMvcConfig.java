package com.member_management.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .excludePathPatterns("/signin", "/signup")
                .excludePathPatterns("/forgot-password/**")
                .addPathPatterns("/forgot-password/**").excludePathPatterns(HttpMethod.POST.name());
    }
}
