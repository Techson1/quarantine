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
 * 免疫计划实体类
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_immunePlan")
public class ImmunePlan extends BaseEntity{
	/**分厂id*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**免疫保健项目*/
	@ManyToOne @JoinColumn(name="project_id")
	private ImmuneHealthProject project;
	/**覆盖栏位*/
	@ManyToOne @JoinColumn(name="paddock_id")
	private Paddock paddock;
	/**防疫计划*/
	@Size(max=20)
	private String name;
	/**是否可用*/
	@Size(max=20)
	private String flag;
	/**保健日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	
	public ImmunePlan setFlagReturnThis(String flag){
		this.flag=flag;
		return this;
	}
}
