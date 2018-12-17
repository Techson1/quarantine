package com.beiqisoft.aoqun.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaddockChangeVo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//耳号
	private String code;
	//品种
	private String breedName;
	//性别
	private String sex;
	//转出圈舍名称
	private String fromPaddock;
	//转出圈舍id
	private String fromPaddockId;
	//转入圈舍名称
	private String toPaddock;
	//转入圈舍id
	private String toPaddockId;
	//操作场区
	private String brief;
	//操作人
	private String recorder;
	//转栏日期
	private Date cDate;
	//更新时间
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private String ctime;
	
	
}
