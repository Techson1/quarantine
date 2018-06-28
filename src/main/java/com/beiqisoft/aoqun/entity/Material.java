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
 * 原料实体类
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_material")
public class Material extends BaseEntity{
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**名称*/
	@Size(max=20)
	private String materialName;
	/**单位*/
	@Size(max=20)
	private String unit;
	/**
	 * 是否可用
	 *  	1 可用
	 *  	0 存档
	 * */
	@Size(max=20)
	private String isUsed;
	/**
	 * 类型
	 * 		1:混合
	 * 		2:纯的
	 * */
	@Size(max=20)
	private String type;
	
	/**
	 * 修改可以并返回自身
	 * */
	public Material setIsUsedReturnThis(String isUsed) {
		this.isUsed=isUsed;
		return this;
	}
	
}
