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
 * 集团实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_company")
public class Company extends BaseEntity{
	
	/**集团名称*/
	@Size(max=20)
	private String companyName;
	/**简称*/
	@Size(max=20)
	private String brief;
	/**地址*/
	@Size(max=20)
	private String address;
	/**联系人*/
	@Size(max=20)
	private String people;
	/**备注*/
	@Size(max=20)
	private String ramarks;
	
}
