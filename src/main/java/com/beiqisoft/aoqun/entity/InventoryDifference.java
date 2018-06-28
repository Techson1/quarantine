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
 * 盘点差异实体类
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_inventoryDifference")
public class InventoryDifference extends BaseEntity{
	/**盘点单号*/
	@ManyToOne @JoinColumn(name="inventory_id")
	private Inventory inventory;
	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**原因*/
	@Size(max=20)
	private String cause;
}
