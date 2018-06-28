package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
 * 冻胚移植实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_frozenEmbryoTransplant")
public class FrozenEmbryoTransplant extends BaseEntity{
	
	/**冻胚库存*/
	@ManyToOne @JoinColumn(name="frozen_embryo_id")
	public FrozenEmbryo frozenEmbryo;
	/**受体*/
	@ManyToOne @JoinColumn(name="receptor_id")
	public BaseInfo receptor;
	/**冷冻细管编号*/
	@ManyToOne @JoinColumn(name="embryo_registration_id")
	public EmbryoRegistration embryoRegistration;
	/**项目*/
	@ManyToOne @JoinColumn(name="project_id")
	public EmbryoProject project;
	/**胎次*/
	@ManyToOne @JoinColumn(name="parity_id")
	public Parity parity;
	
	/**受体移植序号*/
	@Size(max=20)
	public String sheetCode;
	/**移植数*/
	public Integer transNum;
	/**移植日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date date;
	
	public FrozenEmbryoTransplant(String sheetCode,Integer transNum,Date date,
			FrozenEmbryo frozenEmbryo,EmbryoProject project){
		this.sheetCode=sheetCode;
		this.transNum=transNum;
		this.date=date;
		this.embryoRegistration=frozenEmbryo.getEmbryoRegistration();
		this.frozenEmbryo=frozenEmbryo;
		this.project=project;
	}
	
	/**
	 * 修改受体并返回自身
	 * */
	public FrozenEmbryoTransplant setReceptorReturnThis(BaseInfo receptor,Parity parity) {
		this.receptor=receptor;
		this.parity=parity;
		return this;
	}
	
	public FrozenEmbryoTransplant setTransplantReturnThis(Integer transNum, Date date,
			FrozenEmbryo frozenEmbryo) {
		this.transNum=transNum;
		this.date=date;
		this.frozenEmbryo=frozenEmbryo;
		return this;
	}
	
}
