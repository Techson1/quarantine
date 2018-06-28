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

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_salesDatail")
public class SalesDatail extends BaseEntity{
	/**销售单*/
	@ManyToOne @JoinColumn(name="sales_id")
	private Sales sales;
	/**羊只表*/
	@ManyToOne @JoinColumn(name="item_id")
	private BaseInfo item;
	/**价格*/
	@Size(max=20)
	private String price;
	/**胚胎数*/
	@Size(max=20)
	private String embryoCount;
	/**重量*/
	private String weight;
	/**计划价格*/
	private String planPrice;
	
	/**耳号*/
	@Transient
	private String code;
	/**折扣*/
	@Transient
	private String discount;
	/**定级定价*/
	@Transient
	private String rankPrice;
	
	public double priceShift(){
		try{
			return Double.parseDouble(this.price);
		}catch(Exception e){
			return 0.00;
		}
	}
	
	public void discountFigure(double discount){
		this.discount=String.format("%.2f", priceShift()/discount);
		this.rankPrice=String.format("%.2f", discount);
		this.price=String.format("%.2f", priceShift());
	}
}
