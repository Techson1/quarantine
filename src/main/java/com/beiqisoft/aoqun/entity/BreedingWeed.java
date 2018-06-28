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
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 育种淘汰实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月11日上午11:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_breedingWeed")
public class BreedingWeed extends BaseEntity{

	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**淘汰子原因*/
	@ManyToOne @JoinColumn(name="reason_id")
	private DeathDisposalReason reason;
	/**淘汰父原因*/
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
	/**处理措施*/
	@Size(max=2)
	private String treat;
	/**是否锁定*/
	@Size(max=2)
	private String isLock;
	/***/ 
	private Long operator_id;
	
	/**月龄*/
	@Transient
	private String moonAge;
	@Transient
	private Date dateAssistStart;
	@Transient
	private Date dateAssistEnd;
	/**耳号*/
	@Transient
	private String code;
	/**父耳号*/
	@Transient
	private String sireCode;
	/**母耳号*/
	@Transient
	private String damCode;
	/**出生日期*/
	@Transient
	private String birthDay;
	/**品种名称*/
	@Transient
	private String breedName;
	/**性别*/
	@Transient
	private String sex;
	/**淘汰子原因*/
	@Transient
	private String reasonName;
	/**淘汰父原因*/
	@Transient
	private String fatherReasonName;
	/**饲养员名称*/
	@Transient
	private String contactName;
	/**是否复合*/
	@Transient
	private String isAudit;
	/**所在圈舍*/
	@Transient
	private String paddockName;
	
	public BreedingWeed update(BreedingWeed breedingWeed) {
		this.reason=breedingWeed.getReason();
		this.fatherReason=breedingWeed.getFatherReason();
		this.date=breedingWeed.getDate();
		this.treat=breedingWeed.getTreat();
		return this;
	}
}
