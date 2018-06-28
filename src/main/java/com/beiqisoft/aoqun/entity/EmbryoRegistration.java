package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.config.SystemM;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 冻胚编码登记实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年12月5日下午5:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_embryoRegistration")
public class EmbryoRegistration extends BaseEntity{
	/**对应品种*/
	@ManyToOne @JoinColumn(name="breed_id")
	private Breed breed;
	/**编号编码*/
	@Size(max=30)
	private String code;
	/**是否使用*/
	private String isUse;
	/**使用日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date useDate;
	
	/**数量*/
	@Transient @JsonIgnore
	private Integer number;
	/**前缀*/
	@Transient @JsonIgnore
	private String prefix;
	/**开始号*/
	@Transient @JsonIgnore
	private String start;
	
	private EmbryoRegistration(EmbryoRegistration e){
		this.breed=e.breed;
		this.code=e.code;
		this.isUse=e.isUse;
		this.useDate=e.useDate;
		this.recorder=e.getRecorder();
	}
	
	public EmbryoRegistration setCodeReturnThis(String code) {
		this.code=code;
		this.isUse=SystemM.PUBLIC_FALSE;
		return new EmbryoRegistration(this);
	}

	public EmbryoRegistration setIsUseReturnThis(String isUse,Date date) {
		this.isUse=isUse;
		this.useDate=date;
		return this;
	}
	
}
