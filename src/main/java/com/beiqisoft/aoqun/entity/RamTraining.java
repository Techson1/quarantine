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

/**
 * 采精调教访实体类
 * @author 程哲旭
 * @version 1.0
 * @time  2017年12月13日上午10:45:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_ramTraining")
public class RamTraining extends BaseEntity{
	/**公羊*/
	@ManyToOne @JoinColumn(name="ram_id")
	public BaseInfo ram;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	public Organization org;
	/**圈舍id*/
	@ManyToOne @JoinColumn(name="paddock_id")
	public Paddock paddock;
	/**采精日期*/
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date date;
	/**整体评价*/
	@Size(max=20)
	public String assess;
	/**精液量*/
	@Size(max=20)
	public String amount;
	/**精液密度*/
	@Size(max=20)
	public String density;
	/**精子活力*/
	@Size(max=20)
	public String activity;
	/**精液颜色*/
	@Size(max=20)
	public String color;
	/**精液气味*/
	@Size(max=20)
	public String smell;
	/**月龄*/
	@Transient
	public String moonAge;
	
	@Transient
	private Date dateAssistStart;
	@Transient
	private Date dateAssistEnd;
	/**耳号*/
	@Transient
	private String code;
	/**品种名称*/
	@Transient
	private String breedName;
	/**出生日期*/
	@Transient
	private String birthDay;
	/**现在圈舍名称*/
	@Transient
	private String paddockName;
	/**厂区名称*/
	@Transient
	private String orgName;
	
	public RamTraining (String assess,Date date){
		if ("好".equals(assess)) this.assess=SystemM.BASE_INFO_BREEDING_GOOD;
		if ("中".equals(assess)) this.assess=SystemM.BASE_INFO_BREEDING_CENTRE;
		if ("差".equals(assess)) this.assess=SystemM.BASE_INFO_BREEDING_DIFFERENCE;
		if ("不爬羊".equals(assess)) this.assess=SystemM.BASE_INFO_BREEDING_SHEEP;
		if ("本交成功".equals(assess)) this.assess=SystemM.BASE_INFO_BREEDING_SUCCEED;
		this.date=date;
		
	}
	
	public RamTraining setRamReturnThis(BaseInfo ram) {
		this.ram=ram;
		if (this.paddock!=null && this.paddock.getId()==null){
			this.paddock=null;
		}
		return this;
	}
}
