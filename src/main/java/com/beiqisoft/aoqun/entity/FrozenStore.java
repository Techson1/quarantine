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
 * 存储罐号实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_frozenStore")
public class FrozenStore extends BaseEntity{
	/**序号*/
	@Size(max=20)
	private String code;
	/**名称*/
	@Size(max=20)
	private String name;
	/**分厂id*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**是否可用*/
	@Size(max=20)
	private String flag;
	
	/**
	 * 修改是否可用并返回自身
	 * @param flag
	 * 			是否可用
	 * */
	public FrozenStore setFlagReturnThis(String flag) {
		this.flag=flag;
		return this;
	}
}
