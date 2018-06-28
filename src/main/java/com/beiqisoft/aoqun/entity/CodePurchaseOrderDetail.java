package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.config.SystemM;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 耳标订购明细实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 * @deprecated
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_codePurchaseOrderDetail")
public class CodePurchaseOrderDetail extends BaseEntity{

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
	/**耳标订购单*/
	@ManyToOne @JoinColumn(name = "codePurchaseOrder_id")
	private CodePurchaseOrder codePurchaseOrder;
	
	public CodePurchaseOrderDetail(String prefix,int code){
		this.prefix=prefix;
		this.code=code;
		//设置使用状态为未用
		this.useState=SystemM.PUBLIC_FALSE;
	}
	
	/**
	 * 拼接前缀+耳标号并且返回
	 * @return prefix+code
	 * */
 	public String getPrefixCode(){
 		return this.prefix+this.code;
 	}
	
	
}
