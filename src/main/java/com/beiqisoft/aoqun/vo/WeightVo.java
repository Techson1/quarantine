package com.beiqisoft.aoqun.vo;



import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.config.Message;




public class WeightVo extends BaseEntity{
   
	/**
	 * 
	 */
	private String docNum;//序号
	private String code;//可视耳号
	private String rfid;//电子耳号
	private String time;//日期
	private float weight;//重量
	private String type;//称重类型
	private Message message;//结果消息
	
	public WeightVo() {
		super();
	}
	public void init() {
		
	}
	public WeightVo(String docNum,String code,String rfid,String time,float weight,String type,Message message) {
		this.docNum=docNum;
		this.code=code;
		this.rfid=rfid;
		this.time=time;
		this.weight=weight;
		this.type=type;
		this.message=message;
				
	}
	public WeightVo(String docNum,String code,String rfid,String time,float weight,String type) {
		this.docNum=docNum;
		this.code=code;
		this.rfid=rfid;
		this.time=time;
		this.weight=weight;
		this.type=type;
				
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	
}
