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
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 羊只缺陷实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年12月13日上午11:50:42
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_fake")
public class Fake extends BaseEntity{
	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**缺陷类型*/ //ADH15T0263//ADH15T0356
	@Size(max=60)
	private String fakeType;
	/**时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**fakeID*/
	@Transient
	private FakeTypeDam fakeDam;
	@Transient
	private FakeTypeSire fakeSire;
	/**耳号*/
	@Transient
	private String code;
	/**品种*/
	@Transient
	private String breedName;
	/**性别*/
	@Transient
	private String sex;
	/**出生日期*/
	@Transient
	private String birthDay;
	/**月龄*/
	@Transient
	private String moonAge;
	/**在库状态*/
	@Transient
	private String physiologyStatus;
	
	public Fake setFakeIdReturnThis(FakeTypeDam fakeDam,FakeTypeSire fakeSire){
		this.fakeDam=fakeDam;
		this.fakeSire=fakeSire;
		return this;
	}
	
	public Fake setBaseReturnThis(BaseInfo base) {
		this.base=base;
		return this;
	}
}
