package com.apixelhouse.taskaph.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	  @Override
//	    public void addCorsMappings(CorsRegistry registry) {
//	        registry.addMapping("/**").allowedOrigins("*") 
//	                .allowedMethods("*");
//	    }
	  public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**") // Allow all paths
	                .allowedOrigins("*") // Allow all origins
	                .allowedMethods("*"); // Allow all HTTP methods
	    }
}
