package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_paddockChange")
public class PaddockChange extends BaseEntity{

	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**转出*/
	@ManyToOne @JoinColumn(name="from_paddock_id")
	private Paddock fromPaddock;
	/**转入*/
	@ManyToOne @JoinColumn(name="to_paddock_id")
	private Paddock toPaddock;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**转栏日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	
	/**查询类型*/
	@Transient
	private String type;
	
	public PaddockChange(BaseInfo base,Paddock toPaddock,String recorder, Organization org){
		this.base=base;
		this.fromPaddock=base.getPaddock();
		this.toPaddock=toPaddock;
		this.org=org;
		this.recorder = recorder;
	}
	
	public PaddockChange(BaseInfo base, Date date,Paddock toPaddock){
		this.base=base;
		this.toPaddock=toPaddock;
		this.base.setPaddock(toPaddock);
	}
}
