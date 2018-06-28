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
 * 死亡实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月11日上午11:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_deathDisposal")
public class DeathDisposal extends BaseEntity{

	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**死亡原因*/
	@ManyToOne @JoinColumn(name="reason_id")
	private DeathDisposalReason reason;
	/**死亡父原因*/
	@ManyToOne @JoinColumn(name="fatherReason_id")
	private DeathDisposalReason fatherReason;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**栋栏*/
	@ManyToOne @JoinColumn(name="paddock_id")
	private Paddock paddock;
	/**育种淘汰日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	@Transient
	private Date dateAssistStart;
	@Transient
	private Date dateAssistEnd;
	/**处理措施*/
	@Size(max=2)
	private String treat;
	/***/ 
	private Long operator_id;
	
	/**死亡月龄*/
	@Transient
	private String moonAge;
	
	@Transient
	private Long num;
	/**百分比*/
	@Transient
	private Double percent;
	
	/**耳号*/
	@Transient
	private String code;
	/**公羊耳号*/
	@Transient
	private String damCode;
	/**母羊耳号*/
	@Transient
	private String sireCode;
	/**出生日期*/
	@Transient
	private String birthDay;
	/**品种*/
	@Transient
	private String breedName;
	/**性别*/
	@Transient
	private String sex;
	/**死亡原因*/
	@Transient
	private String reasonName;
	/**死亡父原因*/
	@Transient
	private String fatherReasonName;
	/**所在圈舍*/
	@Transient
	private String paddockName;
	/**饲养员*/
	@Transient
	private String contactName;
	
	/**
	 *修改 
	 */
	public DeathDisposal update(DeathDisposal deathdisposal){
		this.reason=deathdisposal.getReason();
		this.fatherReason=deathdisposal.getFatherReason();
		this.org=deathdisposal.getOrg();
		this.paddock=deathdisposal.getPaddock();
		this.date=deathdisposal.getDate();
		this.treat=deathdisposal.getTreat();
		return this;
	}
	
	public DeathDisposal(DeathDisposalReason reason,Long num){
		this.reason = reason;
		this.num = num;
	}
}
