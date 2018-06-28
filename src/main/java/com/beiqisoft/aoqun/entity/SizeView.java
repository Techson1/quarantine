package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 体尺实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年12月5日夜晚9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_sizeView")
public class SizeView extends BaseEntity{

	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**测定饲舍*/
	@ManyToOne @JoinColumn(name="paddock_id")
	private Paddock paddock;
	
	/**测量日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	/**身高*/
	private String tall;
	/**体长*/
	private String bodyLength;
	/**胸围*/
	private String bust;
	/**管围*/
	private String circumFerence;
	/**睾丸围*/
	private String testis;
	/**背膘厚度*/
	private String depth;
	/**眼肌深度*/
	private String yjsd;
	/**眼肌宽度*/
	private String yjkd;
	@Transient
	private String moonAge;
	//未知
	//private Long paddock_id;
	private Long grouptype_id;
	@Transient
	private Date dateAssistStart;
	@Transient
	private Date dateAssistEnd;
	/**耳号*/
	@Transient
	private String code;
	/**品种*/
	@Transient
	private String breedName;
	/**性别*/
	@Transient
	private String sex;
	
	
	
	
	public SizeView setBaseReturnThis(BaseInfo base) {
		this.base=base;
		this.paddock=base.getPaddock();
		return this;
	}

	public SizeView setAll(SizeView sizeView) {
		this.org=sizeView.getOrg();
		this.date=sizeView.getDate();
		this.tall=sizeView.getTall();
		this.bodyLength=sizeView.getBodyLength();
		this.bust=sizeView.getBust();
		this.circumFerence=sizeView.getCircumFerence();
		this.testis=sizeView.getTestis();
		this.depth=sizeView.getDepth();
		this.yjsd=sizeView.getYjsd();
		this.yjkd=sizeView.getYjkd();
		this.paddock=sizeView.getPaddock();
		this.grouptype_id=sizeView.getGrouptype_id();
		return this;
	}
	  
	
	
}
