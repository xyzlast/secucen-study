package com.xyzlast.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
    "com.xyzlast.web.controller",
    "com.xyzlast.web.view"
})
public class RootConfiguration implements WebMvcConfigurer {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
        internalResourceViewResolver.setSuffix(".jsp");
        internalResourceViewResolver.setOrder(5);
        internalResourceViewResolver.setViewClass(JstlView.class);
        return internalResourceViewResolver;
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper();
//    }

//    @Bean(name = "freemarkerConfig")
//    public FreeMarkerConfigurer freemarkerConfig() {
//        FreeMarkerConfigurer freemarkerConfigurer = new FreeMarkerConfigurer();
//        freemarkerConfigurer.setTemplateLoaderPath("/WEB-INF/fmt/");
//        freemarkerConfigurer.setDefaultEncoding("UTF-8");
//        freemarkerConfigurer.setPreferFileSystemAccess(false);
//        return freemarkerConfigurer;
//    }
//
//    @Bean(name = "freeMarkerViewResolver")
//    public FreeMarkerViewResolver freemarkerViewResolver() {
//        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
//        viewResolver.setExposeSpringMacroHelpers(true);
//        viewResolver.setExposeRequestAttributes(true);
//        viewResolver.setContentType("text/html; charset=UTF-8");
//        viewResolver.setCache(false);
//        viewResolver.setSuffix(".ftl");
//        return viewResolver;
//    }

//    @Bean
//    public TilesConfigurer tilesConfigurer() {
//        TilesConfigurer tilesConfigurer = new TilesConfigurer();
//        tilesConfigurer.setDefinitions("/WEB-INF/tiles-configs.xml");
//        return tilesConfigurer;
//    }
//
//    @Bean
//    public TilesViewResolver tilesViewResolver() {
//        TilesViewResolver tilesViewResolver = new TilesViewResolver();
//        tilesViewResolver.setOrder(1);
//        return tilesViewResolver;
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/node_modules/**").addResourceLocations("/node_modules/");
    }


}
