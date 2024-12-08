package com.g2.tiptopG2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/image/**")
                .addResourceLocations("classpath:/static/image/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/robots.txt")
                .addResourceLocations("classpath:/static/robots.txt");
        registry.addResourceHandler("/sitemap.xml")
                .addResourceLocations("classpath:/static/sitemap.xml");
    }
    
}