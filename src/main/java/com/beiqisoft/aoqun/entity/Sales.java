package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.util.MyUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 销售单
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_sales")
public class Sales extends BaseEntity{

	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**客户*/
	@ManyToOne @JoinColumn(name="customer_id")
	private Customer customer;
	
	/**业务员*/
	@ManyToOne @JoinColumn(name="contact_id")
	private Contact contact;
	
	/**编号*/
	@Size(max=20)
	private String code;
	/**出售日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	@Transient
	private Date dateAssistStart;
	@Transient
	private Date dateAssistEnd;
	/**全部数量*/
	@Size(max=20)
	private String totalCount;
	/**全部金额*/
	@Size(max=20)
	private String totalPrice;
	/**预计提货日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date expectedDate;
	/**预付款*/
	@Size(max=20)
	private String prePay;
	/**不清楚*/
	@Size(max=20)
	private String inventoryCode;
	/**销售单是否完成*/
	@Size(max=20)
	private String checkFlag;
	/**价格*/
	@Size(max=20)
	private String price;
	/**类型 1:羊只 2:胚胎*/
	@Size(max=20)
	private String type;
	/**复合人*/
	@Size(max=20)
	private String reviewing;
	
	@Transient
	private Integer checkCount;//已确认数量
	/**
	 * 复合日期
	 * @deprecated
	 * */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date stock;
	
	/**
	 * 复合
	 * */
	public Sales setAudit(Date date,String reviewing) {
		this.date=date;
		this.checkFlag=SystemM.PUBLIC_TRUE;
		this.reviewing=reviewing;
		return this;
	}
	
	public Sales setNotAudit() {
		this.date=null;
		this.checkFlag=SystemM.PUBLIC_FALSE;
		return this;
	}
	
	private void init(){
		if (this.price==null){
			this.price="0";
		}
		if (this.totalCount==null){
			this.totalCount="0";
		}
		if (this.totalPrice==null){
			this.totalPrice="0";
		}
	}

	public Sales add(SalesDatail salesDatail) {
		init();
		this.totalCount=MyUtils.strParseIntPlusOne(this.totalCount);
		this.totalPrice=MyUtils.strPlusStr(this.totalPrice, salesDatail.getPrice());
		
		return this;
	}
	
	public Sales sub(SalesDatail salesDatail){
		this.totalCount=MyUtils.strParseIntSubOne(totalCount);
		this.totalPrice=MyUtils.strSubStr(this.totalPrice, salesDatail.getPrice());
		
		return this;
	}
}
