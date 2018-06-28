package com.beiqisoft.aoqun.task.util;

import org.springframework.context.ApplicationContext;

/**
 * 创建实例化对象
 * */
public class ContextUtils {
	
	private static ApplicationContext applicationContext;//启动类set入，调用下面set方法

	public static ApplicationContext getContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ContextUtils.applicationContext = applicationContext;
	}
}
