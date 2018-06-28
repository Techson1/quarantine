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

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 疾病诊疗
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_generalVeternary")
public class GeneralVeternary extends BaseEntity{

	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**疾病子原因*/
	@ManyToOne @JoinColumn(name="reason_id")
	private DeathDisposalReason reason;
	/**疾病父原因*/
	@ManyToOne @JoinColumn(name="fatherReason_id")
	private DeathDisposalReason fatherReason;
	/**诊疗结果*/
	private String result;
	/**诊疗措施*/
	private String remark;
	/**诊疗日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	@Transient
	private Date dateAssistStart;
	@Transient
	private Date dateAssistEnd;
	/**月龄*/
	@Transient
	private String moonAge;
	/**耳号*/
	@Transient
	private String code;
	/**品种*/
	@Transient
	private String breedName;
	/**性别*/
	@Transient
	private String sex;
	/**疾病父原因*/
	@Transient
	private String fatherReasonName;
	/**疾病子原因*/
	@Transient
	private String resultName;
	/**圈舍名称*/
	@Transient
	private String paddockName;
	
	public GeneralVeternary (Object object){
		Object[] params = (Object[]) object;
		this.code=params[0].toString();
		this.breedName=params[1].toString();
		this.sex=params[2].toString();
		this.moonAge=params[3].toString();
		this.fatherReasonName=params[4].toString();
		this.resultName=params[5].toString();
		this.paddockName=params[6].toString();
		this.date=DateUtils.StrToDate(params[7].toString());
		this.result=params[1].toString();
	}
	
	/**
	 * 修改
	 * */
	public GeneralVeternary update(GeneralVeternary generalVeternary) {
		this.org=generalVeternary.getOrg();
		this.reason=generalVeternary.getReason();
		this.fatherReason=generalVeternary.getFatherReason();
		this.result=generalVeternary.getResult();
		this.remark=generalVeternary.getRemark();
		this.date=generalVeternary.getDate();
		return this;
	}
	
	public Long getPhysiologyStatus(){
		if(this.base==null){
			return 0L;
		}
		if (this.base.getPhysiologyStatus()==null){
			return 0L;
		}
		return this.base.getPhysiologyStatus();
	}
}
