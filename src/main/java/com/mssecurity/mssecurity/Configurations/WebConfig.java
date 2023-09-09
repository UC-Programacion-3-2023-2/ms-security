package com.mssecurity.mssecurity.Configurations;
import com.mssecurity.mssecurity.Interceptors.TokenInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/public/**");; // Asegúrate de que las rutas sean las correctas
    }
}
