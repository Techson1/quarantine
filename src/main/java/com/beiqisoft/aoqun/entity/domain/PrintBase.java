package com.beiqisoft.aoqun.entity.domain;

import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.SalesDatail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
public class PrintBase {
	
	private String code;
	private String sex;
	private String breedName;
	private String rankName;
	private String rankMoney;
	private String discount;
	private String discounted;
	
	public PrintBase(SalesDatail salesDatail){
		BaseInfo base = salesDatail.getItem();
		this.code=base.getCode();
		this.sex=base.getSex();
		this.breedName=base.getBreed().getBreedName();
		if(base.getRank()!=null){
			this.rankName=base.getRank().getName();
		}
		this.rankMoney=salesDatail.getPlanPrice();
		this.discount=salesDatail.getDiscount();
		this.discounted=salesDatail.getPrice();
	}

}
