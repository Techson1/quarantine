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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 称重实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_weight")
public class Weight extends BaseEntity{
	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**圈舍*/
	@ManyToOne @JoinColumn(name="paddock_id")
	private Paddock paddock;
	/**体重*/
	@Size(max=30)
	private String weights;
	/**体重日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date weighthDate;
	/**日龄*/
	private Integer dayAge;
	/**月龄*/
	private Integer monthAge;
	/**描述*/
	private String descRiption;
	/**临时月龄*/
	private String tempMonthAge;
	/**操作员*/
	private String operator;
	/**分组*/
	private Long group_id;
	/**距上次天数*/
	@Size(max=20)
	private String lastDay;
	/**日增重*/
	@Size(max=20)
	private String daily;
	@Size(max=2)
	private String type;
	/**耳号*/
	@Transient
	private String code;
	/**圈舍*/
	@Transient
	private String paddockName;
	/**分厂*/
	@Transient
	private String orgName;
	@Transient
	private Date weighthDateAssistStart;
	@Transient
	private Date weighthDateAssistEnd;
	
	/**
	 * 计算日龄月龄并返回自身
	 * */
	public Weight setAgeReturnThis(Weight weight, BaseInfo baseInfo){
		this.base=baseInfo;
		this.paddock=baseInfo.getPaddock();
		this.dayAge=DateUtils.dateSubDate(this.weighthDate, baseInfo.getBirthDay());
		this.monthAge=DateUtils.dateSubDate(this.weighthDate, baseInfo.getBirthDay())/30;
		if (weight!=null){
			this.lastDay=DateUtils.dateSubDate(this.weighthDate,weight.getWeighthDate())+"";
			int w1=(int) (Double.parseDouble(this.weights)*1000);
			int w2=(int) (Double.parseDouble(weight.getWeights())*1000);
		    daily=((w1-w2)/Integer.parseInt(lastDay))+"";
		}
		return this;
	}

	public Weight addBirthWeight(BaseInfo base) {
		this.base=base;
		this.org=base.getOrg();
		this.paddock=base.getPaddock();
		this.weights=base.getInitialWeigh()+"";
		this.dayAge=0;
		this.monthAge=0;
		this.weighthDate=base.getBirthDay();
		this.recorder=base.getRecorder();
		this.type=SystemM.WEIGHT_TYPE_INITIAL;
		return this;
	}

	public void setInitial(Weight weigth) {
		this.paddock=weigth.paddock;
		this.dayAge=weigth.dayAge;
		this.monthAge=weigth.monthAge;
	}
}
