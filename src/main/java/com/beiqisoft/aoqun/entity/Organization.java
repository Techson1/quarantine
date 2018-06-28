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
 * 分厂实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_organization")
public class Organization extends BaseEntity{
	/**厂区名称*/
	@Size(max=255)
	private String orgName;
	/**地址*/
	@Size(max=255)
	private String address;
	/**是否还在集团内*/
	@Size(max=1)
	private String flag;
	/**简称*/
	@Size(max=20)
	private String brief;
	/**集团*/
	@ManyToOne @JoinColumn(name = "company_id")
	private Company company;
	/**备注*/
	private String ramarks;
	/**管理员账号*/
	@Size(max=40)
	private String userName;
	/**管理员名称*/
	@Size(max=40)
	private String cName;
	
	public Organization(Long id){
		this.id=id;
	}
}