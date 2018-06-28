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
 * 圈舍id
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_paddock")
public class Paddock extends BaseEntity{
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**员工*/
	@ManyToOne @JoinColumn(name="operators_id")
	private Contact contact;
	/**编号*/
	@Size(max=20)
	private String code;
	/**数量*/
	@Size(max=5)
	private String area;
	/**备注*/
	@Size(max=50)
	private String desciption;
	/**当前状态*/
	@Size(max=2)
	private String currentStatus;
	/**名称*/
	@Size(max=50)
	private String name;
	/**是否可用*/
	@Size(max=2)
	private String flag;
	
	public Paddock(Paddock paddock,Long area){
		this.id=paddock.getId();
		this.org=paddock.getOrg();
		this.area=""+area;
		this.desciption=paddock.getDesciption();
		this.currentStatus=paddock.getCurrentStatus();
		this.name=paddock.getName();
		this.flag=paddock.getFlag();
	}
	
	public Paddock(Paddock paddock){
		this.id=paddock.getId();
		this.org=paddock.getOrg();
		this.desciption=paddock.getDesciption();
		this.currentStatus=paddock.getCurrentStatus();
		this.name=paddock.getName();
		this.flag=paddock.getFlag();
	}
	
	public Paddock(String name,String flag){
		this.name=name;
		this.flag=flag;
		
	}

	public Paddock setFlagReturnThis(String flag) {
		this.flag=flag;
		return this;
	}
}
