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

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_immuneHealthProject")
public class ImmuneHealthProject extends BaseEntity{
	
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	@Size(max=20)
	private String immName;
	@Size(max=20)
	private String flag;
	
	public ImmuneHealthProject setFlagReturnThis(String flag) {
		this.flag=flag;
		return this;
	}
}
