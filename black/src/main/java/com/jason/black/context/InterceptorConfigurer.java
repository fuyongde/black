package com.jason.black.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] pathPatterns = {
            "/**"
        };

        String[] excludePathPatterns = {
            "/index"
        };

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns(pathPatterns)
                .excludePathPatterns(excludePathPatterns);
        super.addInterceptors(registry);
    }
}
