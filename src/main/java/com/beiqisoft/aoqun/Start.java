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
		//TomcatEmbeddedServletContainerFactory factory = (TomcatEmbeddedServletContainerFactory) container;
		container.setPort(8080);
		//设置工作记录目录
		//factory.setBaseDirectory(new File("E:/temps"));
		//设置日志(并不是事实记录)
		//factory.addEngineValves(getLogAccessLogValue());
		//设置连接数
		//factory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
		//添加错误页面
		//factory.addErrorPages(new org.springframework.boot.web.servlet.ErrorPage(HttpStatus.NOT_FOUND,"/404.html"));
		//初始化一些设置
		//factory.addInitializers((servletContext ->{
		//	System.out.println("server start!");
		//}));
		 
	}
	
	
}
