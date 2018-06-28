package com.beiqisoft.aoqun.util;

/**
 * 运行时间工具类
 * @author 程哲旭
 * @version 1.0
 * @time  2017年11月4日上午9:10:00
 */
public class ExecuteTime {
	
	/**运行时间*/
	private static ExecuteTime execute =new ExecuteTime();
	/**开始时间*/
	private Long startTime=0l;
	/**结束时间*/
	private Long endTime=0l;
	/**选择时间类型*/
	private int mode=1;
	
	private ExecuteTime(){
		
	}
	
	/**
	 * 获取运行时间工具类
	 * */
	public static ExecuteTime getExecuteTime(){
		return execute;
	}
	
	public void inStartTime(){
		startTime=getTime();
	}
	
	public void inEndTime(){
		endTime=getTime();
		printlnString();
	}
	
	/**
	 * 计算相差毫秒
	 * */
	private Long computationTime(){
		return endTime-startTime;
	}
	
	/**
	 * 输出毫秒数
	 * */
	private void printlnString(){
		System.err.println("此次运行一共花费:"+computationTime()+getMode());
	}
	
	/**
	 * 获取时间
	 * */
	public Long getTime(){
		switch(this.mode){
			case 0:
				return System.currentTimeMillis()/1000;
			case 1:
				return System.currentTimeMillis();
			default: 
				return System.nanoTime();
		}
	}
	
	/**
	 * 设置时间模式
	 * @param mode
	 * 			0:秒
	 * 			1:毫秒
	 * 			2:毫微秒
	 * */
	public void setMode(int mode){
		this.mode=mode;
	}
	
	/**
	 * 获取时间模式
	 * */
	public String getMode(){
		switch(this.mode){
		case 0:
			return "秒";
		case 1:
			return "毫秒";
		default: 
			return "毫微秒";
	}
	}
}
