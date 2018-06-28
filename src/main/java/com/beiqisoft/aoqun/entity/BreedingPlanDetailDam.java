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
 * 母羊选配方案明细实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_breedingPlanDetailDam")
public class BreedingPlanDetailDam extends BaseEntity{

	/**母羊耳号*/
	@Size(max=20)
	private String damCode;
	/**选配方案*/
	@ManyToOne @JoinColumn(name = "breeding_plan_id") @JsonIgnore
	private BreedingPlan breedingPlan;
	/**母羊id*/
	@ManyToOne @JoinColumn(name = "dam_id")
	private BaseInfo dam;
	
	public BreedingPlanDetailDam(BreedingPlanDetailDam breedingPlanDetail,BaseInfo dam){
		this.dam=dam;
		this.damCode=dam.getCode();
		this.breedingPlan=breedingPlanDetail.getBreedingPlan();
		this.recorder=breedingPlanDetail.getRecorder();
	}
}
