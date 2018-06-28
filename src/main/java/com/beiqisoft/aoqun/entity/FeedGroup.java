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
 * 饲喂组群明细
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_feedGroup")
public class FeedGroup extends BaseEntity{

	/**品种*/
	@ManyToOne @JoinColumn(name="breed_id")
	private Breed breed;
	/**饲喂组群*/
	
	
	/**名称*/
	@Size(max=20)
	private String name;
	/**性别*/
	@Size(max=20)
	private String sex;
	/**开始月龄*/
	@Size(max=20)
	private String fromMonth;
	/**结束月龄*/
	@Size(max=20)
	private String toMonth;
	/**生理状态*/
	@Size(max=20)
	private String breedingStatus;
	/**未知*/
	@Size(max=20)
	private String active;
}
