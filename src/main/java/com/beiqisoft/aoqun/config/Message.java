package com.beiqisoft.aoqun.config;

public class Message {
	private Integer code;
	private String msg;
	
	public Message() {
		super();
	}
	
	public String toString(){
		return "code:["+code+"]"+",msg:["+msg+"]";
	}
	
	/**
	 * 判断返回状态是否为正常
	 * @return 正常返回true
	 * */
	public boolean isCodeEqNormal(){
		return this.code==GlobalConfig.NORMAL;
	}
	
	public Message(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}