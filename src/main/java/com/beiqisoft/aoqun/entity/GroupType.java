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
 * 定级定价实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 * @deprecated
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_group_type")
public class GroupType extends BaseEntity{
	
	/**定级名称*/
	@Size(max=100)
	private String name;
	/**用途*/
	@Size(max=100)
	private String purpose;
	/**性别*/
	@Size(max=4)
	private String sex;
	/**标志*/
	@Size(max=20)
	private String sign;
	/**级别*/
	@Size(max=20)
	private String rank;
	/**定价*/
	@Size(max=20)
	private String pricing;
	/**分场*/
	@ManyToOne @JoinColumn(name = "org_id")
	private Organization org;
	/**品种*/
	@ManyToOne @JoinColumn(name = "breed_id")
	private Breed breed;
}
