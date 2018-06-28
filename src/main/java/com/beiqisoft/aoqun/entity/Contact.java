package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * 联系人实体类/员工实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_contact")
public class Contact extends BaseEntity{

	/**联系人名称*/
	@Size(max=60)
	private String firstName;
	/**手机号*/
	@Size(max=20)
	private String phone;
	/**属于省份*/
	@Size(max=20)
	private String provinceCode;
	/**是否在职*/
	@Size(max=20)
	private String flag;
	/**备注*/
	@Size(max=255)
	private String comment;
	/**简称*/
	@Size(max=20)
	private String abbreviation;
	/**联系人*/
	private String contacts;
	/**员工角色*/
	@ManyToOne @JoinColumn(name = "type_id")
	private ContactType contactType;
	/**分厂id*/
	@ManyToOne @JoinColumn(name = "org_id")
	private Organization org;
	
	@Transient
	private Long count;
	
	public Contact(Contact contact ,Long count){
		this.id=contact.getId();
		this.count=count;
		this.firstName=contact.getFirstName();
		this.phone=contact.getPhone();
		this.provinceCode=contact.getProvinceCode();
		this.flag=contact.getFlag();
		this.comment=contact.getComment();
		this.abbreviation=contact.getAbbreviation();
		this.contacts=contact.getContacts();
		this.contactType=contact.getContactType();
		this.org=contact.getOrg();
	}
	
	/**
	 * 修改是否在职并且返回自身
	 * */
	public Contact setFlagReturnThis(String flag){
		this.flag=flag;
		return this;
	}
}
