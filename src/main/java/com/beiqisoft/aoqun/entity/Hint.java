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
@Table(name = "t_hint")
public class Hint extends BaseEntity{

	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**参数名称*/
	@Size(max=20)
	private String name;
	/**提示信息*/
	private Integer hint;
	/**参数*/
	private Integer parameter;
	
	public Hint(String name,Integer parameter,Organization org){
		this.name=name;
		this.parameter=parameter;
		this.org=org;
	}

	public Hint setParameterReturnThis(Integer parameter,Integer hint) {
		this.parameter=parameter;
		this.hint = hint;
		return this;
	}
}
