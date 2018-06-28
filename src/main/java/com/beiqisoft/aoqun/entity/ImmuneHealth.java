package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 防疫保健
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_immuneHealth")
public class ImmuneHealth extends BaseEntity{

	/**羊只*/
	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**饲舍*/
	@ManyToOne @JoinColumn(name="paddock_id")
	private Paddock paddock;
	/**免疫保健项目*/
	@ManyToOne @JoinColumn(name="project_id")
	private ImmuneHealthProject project;
	/**药品名称*/
	@Size(max=20)
	private String name;
	/**生产厂家*/
	@Size(max=20)
	private String manufacturers;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date date;
	/**生产批号*/
	@Size(max=20)
	private String batch;
	
	@Transient
	private Date dateAssistStart;
	@Transient
	private Date dateAssistEnd;
	public ImmuneHealth(ImmuneHealth immuneHealth,BaseInfo base){
		this.base=base;
		this.org=immuneHealth.getOrg();
		this.paddock=immuneHealth.getPaddock();
		this.project=immuneHealth.getProject();
		this.name=immuneHealth.getName();
		this.manufacturers=immuneHealth.getManufacturers();
		this.batch=immuneHealth.getBatch();
		this.recorder=immuneHealth.getRecorder();
		this.date = immuneHealth.getDate();
	}
	
}
