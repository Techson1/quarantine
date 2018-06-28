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
import com.beiqisoft.aoqun.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 胎次实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_parity")
public class Parity extends BaseEntity{
	
	/**母羊*/
	@ManyToOne @JoinColumn(name = "dam_id")
	private BaseInfo dam;
	/**当前总胎次数*/
	private Integer parityMaxNumber;
	/**胎次类型*/
	@Size(max=2)
	private String parityType;
	/**创建日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	/**胎次开始日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	/**是否最新胎次*/
	@Size(max=2)
	private String isNewestParity;
	/**是否关闭*/
	@Size(max=2)
	private String isClosed;
	/**关闭时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date closedDate;
	/**NPD(非生产天数)*/
	private Integer NPD;
	/**妊娠天数*/
	private Integer gestation;
	/**哺乳天数*/
	private Integer lactation;
	/**空怀天数*/
	private Integer nonpregnancy;
	/**配种天数*/
	private Integer postCross;
	/**供体胎次数*/
	private Integer parityDonator;
	/**受体鲜胚胎次数*/
	private Integer parityReceptorFresh;
	/**受体冻胚胎次数*/
	private Integer parityReceptorFrozen;
	/**自然繁殖胎次数*/
	private Integer parityNbNumber;
	
	/**耳号*/
	@Transient
	private String code;
	/**品种*/
	@Transient
	private String breedName;
	/**出生日期*/
	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birthDay;
	/**本胎配种次数*/
	@Transient
	private String joiningSql;
	/**产羔母羊id*/
	private Long lambingDamId;
	/**产羔总数*/
	@Transient
	private Integer bornTimes;
	/**活羔数*/
	@Transient
	private Integer aliveCount;
	/**死胎数*/
	@Transient
	private Integer deadCount;
	/**畸形数*/
	@Transient
	private Integer badCount;
	/**61日龄成活数*/
	@Transient
	private String number;
	/**成活率*/
	@Transient
	private String survival;
	/**结束类型*/
	@Transient
	private String closedTyep;
	@Transient
	private Integer day;
	
	public Parity(BaseInfo dam){
		this.dam=dam;
		this.startDate=new Date();
		this.isNewestParity=SystemM.PUBLIC_TRUE;
		this.createDate=new Date();
		this.parityMaxNumber=1;
		this.parityDonator=0;
		this.parityReceptorFresh=0;
		this.parityReceptorFrozen=0;
		this.parityNbNumber=0;
	}
	public Parity(BaseInfo dam,Parity parity){
		this.dam=dam;
		this.startDate=parity.getClosedDate();
		this.isNewestParity=SystemM.PUBLIC_TRUE;
		this.createDate=new Date();
		this.parityMaxNumber=parity.getParityMaxNumber()+1;
		this.parityDonator=parity.getParityDonator();
		this.parityReceptorFresh=parity.getParityReceptorFresh();
		this.parityReceptorFrozen=parity.getParityReceptorFrozen();
		this.parityNbNumber=parity.getParityNbNumber();
	}
	
	public Parity(Long id,String code,String breedName,Date birthDay,Integer parityMaxNumber,Date startDate,Date closedDate){
		this.id=id;
		this.code=code;
		this.breedName=breedName;
		this.parityMaxNumber=parityMaxNumber;
		this.startDate=startDate;
		this.closedDate=closedDate;
		this.birthDay=birthDay;
		this.day=DateUtils.dateSubDate(closedDate, startDate);
	}
}
