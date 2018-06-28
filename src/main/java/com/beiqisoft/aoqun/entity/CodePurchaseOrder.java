package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 耳标订购实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 * @deprecated
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_codePurchaseOrder")
public class CodePurchaseOrder extends BaseEntity{
	/**耳标单名称*/
	@Size(max=20)
	private String name;
	/**入库时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date WarehousingDate;
	/**耳标类型*/
	@Size(max=20)
	private String type;
	/**出生状态*/
	@Size(max=20)
	private String state;
	/**颜色*/
	@Size(max=20)
	private String color;
	/**记录人*/
	@Size(max=20)
	private String recorder;
	/**前缀*/
	@Size(max=20)
	private String prefix;
	/**耳标号*/
	private int code;
	/**可视耳标*/
	private Long visualCode;
	/**使用状态*/
	@Size(max=20)
	private String useState;
	/**使用时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date useDate;
	/**品种id*/
	@ManyToOne @JoinColumn(name = "breed_id")
	private Breed breed;
	/**公司id*/
	@ManyToOne @JoinColumn(name = "org_id")
	private Organization org;
	/**供应商*/
	@ManyToOne @JoinColumn(name = "Customer_id")
	private Customer customer;
	
}
