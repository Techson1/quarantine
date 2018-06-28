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

/**
 * 精料明细实体类
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_materialDetail")
public class MaterialDetail extends BaseEntity{
	/**原料*/
	@ManyToOne @JoinColumn(name="material_id")
	private Material material;
	/**精料明细*/
	@ManyToOne @JoinColumn(name="burden_id")
	private Material burden;
	/**精料明细名称*/
	@Size(max=20)
	private String name;
	/**百分比*/
	private Double ratio;
}
