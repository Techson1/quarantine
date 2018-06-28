package com.beiqisoft.aoqun.entity;

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

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_reviseWigth")
public class ReviseWigth extends BaseEntity{
	
	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**4月龄体重*/
	private String wigth4;
	/**5月龄体重*/
	private String wigth5;
	/**6月龄体重*/
	private String wigth6;
	/**7月龄体重*/
	private String wigth7;
	/**8月龄体重*/
	private String wigth8;
	/**9月龄体重*/
	private String wigth9;
	/**10月龄体重*/
	private String wigth10;
	/**11月龄体重*/
	private String wigth11;
	/**12月龄体重*/
	private String wigth12;
	/**18月龄体重*/
	private String wigth18;
	/**24月龄体重*/
	private String wigth24;
	
	public ReviseWigth(BaseInfo  base){
		this.base=base;
	}
}
