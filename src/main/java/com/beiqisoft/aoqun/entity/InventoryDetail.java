package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;

/**
 * 盘点明细
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_inventoryDetail")
public class InventoryDetail extends BaseEntity{
	/**盘点*/
	@ManyToOne @JoinColumn(name="inventory_id")
	private Inventory inventory;
	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**当前圈舍*/
	@ManyToOne @JoinColumn(name="from_paddock_id")
	private Paddock fromPaddock;
	/**转出圈舍*/
	@ManyToOne @JoinColumn(name="to_paddock_id")
	private Paddock toPaddock;
	
	/**
	 * 盘点明细
	 */
	public InventoryDetail(Long id,String code,String sex,Date birthDay,String toPaddockName,String fromPaddockName){
		BaseInfo base = new BaseInfo();
		base.setCode(code);
		base.setSex(sex);
		base.setBirthDay(birthDay);
		Paddock toPaddock= new Paddock();
		Paddock fromPaddock= new Paddock();
		toPaddock.setName(toPaddockName);
		fromPaddock.setName(fromPaddockName);
		this.id = id;
		this.toPaddock=toPaddock;
		this.fromPaddock=fromPaddock;
		this.base=base;
	}
}
