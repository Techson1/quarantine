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
 * 我的群组
 * @author 程哲旭
 * @version 1.0
 * @time 2018年1月27日上午9:10:00
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_baseGroup")
public class BaseGroup extends BaseEntity{
	
	/**群组名称*/
	@Size(max=20)
	private String name;
	/**分厂名称*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
}
