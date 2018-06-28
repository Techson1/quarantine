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
 * 断奶实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_weaning")
public class Weaning extends BaseEntity{
	/**胎次*/
	@ManyToOne @JoinColumn(name="parity_id")
	private Parity parity;
	/**母羊*/
	@ManyToOne @JoinColumn(name="dam_id")
	private BaseInfo dam;
	/**母羊产羔记录*/
	@ManyToOne @JoinColumn(name="lambingDam_id")
	private LambingDam lambingDam;
	/**分厂id*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**断奶日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date weaningDate;
	@Transient
	private Date weaningDateAssistStart;
	@Transient
	private Date weaningDateAssistEnd;
	/**断奶成活数*/
	private Integer weaningAliveCount;
	/**断奶成活率*/
	private Double weaningAliveRate;
	@Transient
	private Paddock paddock;
	
	public Weaning(Weaning weaning){
		this.weaningDate=weaning.getWeaningDate();
		this.weaningAliveCount=weaning.getWeaningAliveCount();
		this.weaningAliveRate=weaning.getWeaningAliveRate();
		this.recorder=weaning.getRecorder();
		this.org=weaning.getOrg();
		this.paddock=weaning.getPaddock();
	}
	
	public Weaning getWeaning(Weaning weaning) {
		this.weaningDate=weaning.getWeaningDate();
		this.weaningAliveCount=weaning.getWeaningAliveCount();
		this.weaningAliveRate=weaning.getWeaningAliveRate();
		this.org=weaning.getOrg();
		this.paddock=weaning.getPaddock();
		this.recorder=weaning.getRecorder();
		return this;
	}
}
