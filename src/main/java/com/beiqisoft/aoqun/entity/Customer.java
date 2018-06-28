package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;

/**
 * 客户实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_customer")
public class Customer extends BaseEntity{
	/**联系人*/
	@Size(max=20)
	private String surName;
	/**公司姓名*/
	@Size(max=100)
	private String firstName;
	/**标题*/
	@Size(max=100)
	private String title;
	/**地址*/
	@Size(max=255)
	private String address;
	/**地址2*/
	@Size(max=255)
	private String address2;
	/**电话*/
	@Size(max=20)
	private String phone;
	/**手机*/
	@Size(max=20)
	private String mobile;
	/**手机2*/
	@Size(max=20)
	private String mobile2;
	/**网页*/
	@Size(max=60)
	private String web;
	/**电子邮件*/
	@Size(max=20)
	private String email;
	/**qq*/
	@Size(max=20)
	private String qq;
	/**备注*/
	@Size(max=100)
	private String comment;
	/**省代码*/
	@Size(max=100)
	private String provinceCode;
	/**是否可用*/
	@Size(max=2)
	private String flag;
	/**简称*/
	@Size(max=64)
	private String brief;
	/*@ManyToOne @JoinColumn(name = "org_id")
	private Organization org;*/
	/**购买次数*/
	@Transient
	private String purchase;
	/**购买只数*/
	@Transient
	private String number;
	/**金额*/
	@Transient
	private String money;
	
	public Customer setFlagReturnThis(String flag){
		this.flag=flag;
		return this;
	}
	
	public Customer(Long id){
		this.id=id;
	}
}
