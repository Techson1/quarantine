package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_looks")
public class Looks extends BaseEntity{

	/**色斑打分*/
	@Size(max=20)
	private String splash;
	/**角*/
	@Size(max=20)
	private String horn;
	/**未知字段*/
	@Size(max=20)
	private String feature;
	/**毛发打分*/
	@Size(max=20)
	private String hair;
	/**牙齿咬合*/
	@Size(max=20)
	private String tooth;
	/**蹄颜色打分*/
	@Size(max=20)
	private String footColor;
	/**嘴颜色打分*/
	@Size(max=20)
	private String mouthColor;
	/**脂肪分表*/
	@Size(max=20)
	private String fats;
	/**修改日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	/**分厂id*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**羊只id*/
	@OneToOne @JoinColumn(name="base_id") @JsonIgnore
	private BaseInfo base;
	
	private String code;
	private String breed;
	private String sex;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date birthDay;
	private String rank;
	private Integer age;
	private Long baseOrgId;
	@Transient
	private Date dateAssistStart;
	@Transient
	private Date dateAssistEnd;
	
	public Looks edit(Looks look) {
		this.splash=look.getSplash();
		this.horn=look.getHorn();
		this.feature=look.getFeature();
		this.hair=look.getHair();
		this.tooth=look.getTooth();
		this.footColor=look.getFootColor();
		this.mouthColor=look.getMouthColor();
		this.fats=look.getFats();
		this.date=look.getDate();
		this.org=look.getOrg();
		this.recorder=look.getRecorder();
		return this;
	}
}
