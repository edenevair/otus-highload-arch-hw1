package ru.otus.highload.hw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
     //   registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("user");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/user").setViewName("user");
        registry.addViewController("/user-list").setViewName("user-list");
        registry.addViewController("/friendship-request-list").setViewName("friendship-request-list");
        registry.addViewController("/403").setViewName("/error/403");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/news").setViewName("news");
    }
}
