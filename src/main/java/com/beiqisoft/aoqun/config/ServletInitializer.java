package com.beiqisoft.aoqun.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.beiqisoft.aoqun.Start;
  
public class ServletInitializer extends SpringBootServletInitializer{  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){  
        return application.sources(Start.class);  
    }
} 