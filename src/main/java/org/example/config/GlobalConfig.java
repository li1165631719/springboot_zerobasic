package org.example.config;

import org.example.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author 李志豪
 * @Date 2024/6/2 4:33
 */
@Configuration
public class GlobalConfig implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/token/logout")
                .addPathPatterns("/dynamic/publish")
                .excludePathPatterns();
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
