package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;

/**
 * 繁殖参数实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_breedParameter")
public class BreedParameter extends BaseEntity{
	/**参数名称*/
	@Size(max=20)
	private String name;
	/**提示信息*/
	@Size(max=20)
	private String hint;
	/**参数*/
	private Integer parameter;
}
