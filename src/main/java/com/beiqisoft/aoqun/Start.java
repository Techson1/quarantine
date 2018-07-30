package com.beiqisoft.aoqun;

import java.io.File;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.http.HttpStatus;

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
		TomcatEmbeddedServletContainerFactory factory = (TomcatEmbeddedServletContainerFactory) container;
		container.setPort(8080);
		//设置工作记录目录
		factory.setBaseDirectory(new File("E:/temps"));
		//设置日志(并不是事实记录)
		factory.addEngineValves(getLogAccessLogValue());
		//设置连接数
		factory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
		//添加错误页面
		factory.addErrorPages(new org.springframework.boot.web.servlet.ErrorPage(HttpStatus.NOT_FOUND,"/404.html"));
		//初始化一些设置
		factory.addInitializers((servletContext ->{
			System.out.println("server start!");
		}));
		 
	}
	private AccessLogValve getLogAccessLogValue() {
		AccessLogValve log = new AccessLogValve();
		//日志目录
		log.setDirectory("e:/temps");
		//开始日志
		log.setEnabled(true);
		//日志级别
		log.setPattern("common");
		//日志前缀
		log.setPrefix("springboot-access-log");
		//日志后缀
		log.setSuffix(".txt");
		return log;
	}
}
class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer{
	 
	@Override
	public void customize(Connector connector) {
		Http11NioProtocol protocol=(Http11NioProtocol) connector.getProtocolHandler();
		//设置最大连接数
		protocol.setMaxConnections(2000);
		//设置最大线程数
		protocol.setMaxThreads(500);
	}
	
}
