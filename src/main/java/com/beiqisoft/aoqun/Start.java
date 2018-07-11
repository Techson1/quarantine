package com.beiqisoft.aoqun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

import com.beiqisoft.aoqun.task.util.ContextUtils;

@SpringBootApplication
public class Start implements EmbeddedServletContainerCustomizer{
	public static void main(String[] args) {
		ContextUtils.setApplicationContext(SpringApplication.run(Start.class, args));
    }
	/**
	 * 修改默认端口
	 */
	@Override 
	public void customize(ConfigurableEmbeddedServletContainer container){
		container.setPort(8080);
	}
}