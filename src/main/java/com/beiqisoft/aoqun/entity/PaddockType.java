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
 * 栋栏实体类
 * @deprecated
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_paddockType")
public class PaddockType extends BaseEntity{

	/**栋栏名称*/
	@Size(max=20)
	private String paddockTypeName;
	/**栋栏明细*/
	@Size(max=20)
	private String description;
	/**是否可用*/
	@Size(max=20)
	private String flag;
	/**记录人*/
	@Size(max=20)
	private String recorder;
	/**饲养员*/
	@Size(max=20)
	private String breeder;
	/**分厂*/
	@ManyToOne @JoinColumn(name = "org_id")
	private Organization org;
	
	/**
	 * 修改是否可用字段并且返回自身
	 * */
	public PaddockType setFlagReturnThis(String flag){
		this.flag=flag;
		return this;
	}
	
}
