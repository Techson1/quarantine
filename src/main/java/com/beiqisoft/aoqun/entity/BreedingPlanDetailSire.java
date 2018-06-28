package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 公羊选配明细实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_breedingPlanDetailSire")
public class BreedingPlanDetailSire extends BaseEntity{

	@Size(max=20)
	private String sireCode;
	@ManyToOne @JoinColumn(name = "breeding_plan_id") @JsonIgnore
	private BreedingPlan breedingPlan;
	@ManyToOne @JoinColumn(name = "sire_id")
	private BaseInfo sire;
	@Size(max=2)
	public String sireType;
	
	public BreedingPlanDetailSire setSireReturnThis(BaseInfo sire) {
		this.sire=sire;
		this.sireCode=sire!=null?sire.getCode():null;
		return this;
	}
}
