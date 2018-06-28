package com.beiqisoft.aoqun.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 流产实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_abortion")
public class Abortion extends BaseEntity{
	/**胎次*/
	@OneToOne @JoinColumn(name="parity_id")
	private Parity parity;
	/**母羊*/
	@ManyToOne @JoinColumn(name="dam_id")
	private BaseInfo dam;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**饲舍*/
	@ManyToOne @JoinColumn(name="paddock_id")
	private Paddock paddock;
	/**转出饲舍*/
	@ManyToOne @JoinColumn(name="toPaddock")
	private Paddock toPaddock;
	/**登记日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date abortionDate;
	/**操作人*/
	private String operator;
	/**流产原因*/
	private String abortionReasons;
	
	public Abortion(Abortion abortion){
		this.abortionDate=abortion.getAbortionDate();
		this.operator=abortion.getOperator();
		this.abortionReasons=abortion.getAbortionReasons();
		this.toPaddock=abortion.getToPaddock();
		this.org=abortion.getOrg();
		this.recorder=abortion.getRecorder();
	}
	
	public Abortion(Abortion abortion,String code){
		this.org=abortion.getOrg();
		this.abortionDate=abortion.getAbortionDate();
		this.operator=abortion.getOperator();
		this.abortionReasons=abortion.getAbortionReasons();
		this.toPaddock=abortion.getToPaddock();
		this.recorder=abortion.getRecorder();
	}
	
	public void setAbortion(Abortion abortion) {
		this.abortionDate=abortion.getAbortionDate();
		this.operator=abortion.getOperator();
		this.abortionReasons=abortion.getAbortionReasons();
		this.recorder=abortion.getRecorder();
		this.toPaddock=abortion.getToPaddock();
	}
}
