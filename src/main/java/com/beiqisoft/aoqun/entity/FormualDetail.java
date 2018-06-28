package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;

/**
 * 日粮配方明细
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_formualDetail")
public class FormualDetail extends BaseEntity{

	/**配方*/
	@ManyToOne @JoinColumn(name="formula_id")
	private Formula formula;
	/**原料*/
	@ManyToOne @JoinColumn(name="material_id")
	private Material material;
	/**百分比*/
	private Double ratio;
	
	
}
