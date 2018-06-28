package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.beiqisoft.aoqun.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_aoqunTest")
public class AoqunTest extends BaseEntity{
	@Size(max=20)
	private String name;
	@Size (max=20)
	private String test;
	@Size(max=20)
	private String paw;
	@ManyToOne
	@JoinColumn(name="org_id")
	private Organization org;
	
	public AoqunTest(String name,String paw){
		this.name=name;
		this.test="1";
		this.paw=paw;
	}
	
	public AoqunTest setNameReturnThis(String name){
		this.name=name;
		return this;
	}
	
	public String bbbNames(){
		return this.name;
	}
	
	
	
	public void setName(){
	}
}
