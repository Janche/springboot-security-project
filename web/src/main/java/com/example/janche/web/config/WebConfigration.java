package com.example.janche.web.config;

import com.example.janche.common.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigration implements  WebMvcConfigurer {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] IPs = this.applicationConfig.getOrigins().split(",");
        registry.addMapping("/**")
                .allowedOrigins(IPs)
                //允许的请求方式
                .allowedMethods("*")
                // 允许的请求头
                .allowedHeaders("*")
                .maxAge(3600)
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //处理的路径规则
        registry.addResourceHandler("/static/**")
                //到哪些目录下去查找静态资源
                .addResourceLocations("classpath:/static/");
    }

}
