package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.entity.guide.LambingDamGuide;

/**
 * 羔羊生产实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 * @deprecated
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_lambing")
public class Lambing extends BaseEntity{
	/**羔羊*/
	@OneToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**母羊*/
	@OneToOne @JoinColumn(name="dam_id")
	private BaseInfo dam;
	/**父羊*/
	@OneToOne @JoinColumn(name="sire_id")
	private BaseInfo sire;
	/**代孕母羊*/
	@OneToOne @JoinColumn(name="foster_dam_id")
	private BaseInfo fosterDam;
	/**产羔母羊关联*/
	@ManyToOne @JoinColumn(name="lambing_dam_id")
	private LambingDamGuide lambingDam;
	/**厂区*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**胎次*/
	@ManyToOne @JoinColumn(name="parity_id")
	private Parity parity;
	@ManyToOne @JoinColumn(name="looks_id")
	private Looks looks;
	
	public Lambing(BaseInfo baseInfo,Looks looks,LambingDamGuide lambingDam){
		this.base=baseInfo;
		this.dam=baseInfo.getDam();
		this.sire=baseInfo.getSire();
		this.fosterDam=baseInfo.getFosterDam();
		this.lambingDam=lambingDam;
		this.org=baseInfo.getOrg();
		this.looks=looks;
	}
}
