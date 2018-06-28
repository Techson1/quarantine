package com.beiqisoft.aoqun.entity.domain;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.beiqisoft.aoqun.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MessageEntity {
	/**耳号*/
	private String code;
	/**品种名称*/
	private String breedName;
	/**圈舍名称*/
	private String paddockName;
	
	private String moonAge;
	
	private String breedingState;
	/**预计日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date predictDate;
	/**剩余天数*/
	private int day; 
	
	public MessageEntity(String code,Date date, String breedName,String paddockName){
		this.code=code;
		this.breedName = breedName;
		this.paddockName = paddockName;
		this.predictDate=date;
	}
	
	public MessageEntity(String code,Date date, String breedName,String paddockName,String max){
		this.code=code;
		this.breedName = breedName;
		this.paddockName = paddockName;
		this.predictDate=date;
	}
	public MessageEntity(String code,Date date, String breedName,String paddockName,String moonAge,String breedingState){
		this.code=code;
		this.breedName = breedName;
		this.paddockName = paddockName;
		this.predictDate=date;
		this.moonAge = moonAge;
		this.breedingState = breedingState;
	}
	public MessageEntity calculate(Integer day){
		this.predictDate=DateUtils.dateAddInteger(predictDate, day);
		this.day= DateUtils.dateSubDate(predictDate,new Date());
		return this;
	}
	
	public int compareTo(int day) {
        return this.day - day;
    }
}
