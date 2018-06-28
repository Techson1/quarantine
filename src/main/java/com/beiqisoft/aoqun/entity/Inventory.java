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
 * 盘点
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_inventory")
public class Inventory extends BaseEntity{
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**盘点任务单号*/
	@Size(max=20)
	private String name;
	/**盘点状态<BR>可用:1<BR>不可用:0*/
	@Size(max=2)
	private String flag;
	
	/**
	 * 修改判断状态并返回自身
	 * */
	public Inventory setFlagReturnThis(String flag) {
		this.flag=flag;
		return this;
	}
}
