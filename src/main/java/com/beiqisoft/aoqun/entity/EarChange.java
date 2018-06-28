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
 * 补戴标
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "T_earChange")
public class EarChange extends BaseEntity{
	/**羊只表*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**可视耳号*/
	@Size(max=20)
	private String code;
	/**电子耳号*/
	@Size(max=20)
	private String rfid;
	/**旧的电子耳号*/
	@Size(max=20)
	private String oldRfid;
	/**是否最新*/
	@Size(max=20)
	private String isNewest;
	/**补标原因*/
	@Size(max=255)
	private String cause;
	
	public EarChange (BaseInfo base,String rfid, String cause,String recorder){
		this.base=base;
		this.code=base.getCode();
		this.oldRfid=base.getRfid();
		this.rfid=rfid;
		this.cause=cause;
		this.recorder=recorder;
	}
}
