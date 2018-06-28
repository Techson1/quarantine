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
 * 调拨实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年1月27日上午9:10:00
 * */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_allot")
public class Allot extends BaseEntity{
	/**批次名称*/
	@Size(max=60)
	private String name;
	/**转出厂*/
	@ManyToOne @JoinColumn(name="from_org_id")
	private Organization fromOrg;
	/**转入厂*/
	@ManyToOne @JoinColumn(name="to_org_id")
	private Organization toOrg;
	/**调拨日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date allotDate;
	/**羊只数量*/
	@Size(max=20)
	private String baseCount;
	/**是否可以修改 1可以修改 0不可修改*/
	@Size(max=2)
	private String flag;

	public Allot setFlagReturnThis(String flag) {
		this.flag=flag;
		return this;
	}
}
