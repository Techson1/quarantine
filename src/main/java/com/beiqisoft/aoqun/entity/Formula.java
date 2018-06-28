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
 * 日粮配方实体类
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_formula")
public class Formula extends BaseEntity{
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**配方名称*/
	@Size(max=20)
	private String formulaName;
	/**适应羊群*/
	@Size(max=20)
	private String active;
	/**是否可用*/
	@Size(max=20)
	private String isUsed;
	public Formula setIsUsedReturnThis(String isUsed) {
		this.isUsed=isUsed;
		return this;
	}
}
