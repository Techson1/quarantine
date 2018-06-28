package com.beiqisoft.aoqun.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.beiqisoft.aoqun.util.json.spring.JsonReturnHandler;

/**
 * Web 配置
 * @author WD
 */
@Configuration 
@EnableWebMvc
public class WebConfig  extends WebMvcConfigurerAdapter {
	 @Override  
	    public void addCorsMappings(CorsRegistry registry) {  
	        registry.addMapping("/**")  
	                .allowedOrigins("*")  
	                .allowCredentials(true)  
	                .allowedMethods("GET", "POST", "DELETE", "PUT")  
	                .maxAge(3600);  
	    }  
	 @Bean
	    public JsonReturnHandler JsonReturnHandler(){
	        return new JsonReturnHandler();
	    }
	 @Override
	    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		 	returnValueHandlers.add(JsonReturnHandler());
	    }
}
