package com.beiqisoft.aoqun.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.beiqisoft.aoqun.task.realize.BaseInfoTaskRealize;
import org.springframework.context.ApplicationContext;
/**
 * BaseInfo定时任务配置类
 *
 * @author 程哲旭
 * @version 1.0
 * @time  2017年11月30日上午14:43:00
 */
@Configuration
@EnableScheduling // 启用定时任务
public class BaseInfoTask {
	@Autowired
	ApplicationContext applicationContext;
	
	/**
	 * 执行定时任务没10秒定时一次
	 * */
	@Scheduled(cron = "0 30 2 * * ?")
	public void task(){
		//ApplicationContext
		//BaseInfoTaskRealize.getBaseInfoTaskRealize().closeRealize();
		BaseInfoTaskRealize.getBaseInfoTaskRealize().instantiation(applicationContext);
		BaseInfoTaskRealize.getBaseInfoTaskRealize().task();
	}
}
