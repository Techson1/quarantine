package com.beiqisoft.aoqun.config;

public class GlobalConfig {
	public static String LOGIN_SESSION_KEY = "springboot_user";
	public static String DEFAULT_LOGO="img/logo.jpg";
	/**分页数据*/
	public static int PAGE_SIZE= 50;
	public static int PAGE_MAX_SIZE=150;
	
	public static final int NORMAL = 100;//状态正常	
	public static final int ABNORMAL = 101;//状态异常
	public static final int IS_PASS=102;//需要判读是否通过
	public static final int ABNORMAL_TO=103;
	public static final int ABNORMAL_NULL=104;
	/**平均月龄*/
	public static final double AVERAGE_MONTH=30.4375;
	
	public static final Message SUCCESS = new Message(NORMAL,"操作成功");
	public static final Message FAIL = new Message(ABNORMAL,"操作失败");
	public static final Message LOGIN_FAIL = new Message(ABNORMAL,"账号或密码错误");
	public static final Message VCODE_FAIL = new Message(ABNORMAL,"验证码错误");
	public static final Message UNEXIST_USER = new Message(ABNORMAL,"用户不存在");
	public static final Message COUNT_NULL= new Message(ABNORMAL_NULL,"该销售单下没有羊只");
	public static final Message VERSION = new Message(NORMAL,"1.0.0");
	
	/**
	 * 设置错误信息并返回
	 * @param msg
	 * 			错误原因
	 * @return Message
	 * */
	public static Message setAbnormal(String msg){
		return new Message(ABNORMAL,msg);
	}
	
	/**
	 * 设置判断信息并返回
	 * @param msg
	 * 			判断信息
	 * @return Message
	 * */
	public static Message setIsPass(String msg){
		return new Message(IS_PASS,msg);
	}
	
	/**
	 * 设置正确信息并返回
	 * */
	public static Message setNormal(String msg){
		return new Message(NORMAL,msg);
	}
	
	/**
	 * 错误结果2
	 * */
	public static Message setAbnormalTo(String msg){
		return new Message(ABNORMAL_TO,msg);
	}
}
