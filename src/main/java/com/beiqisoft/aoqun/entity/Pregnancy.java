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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 测孕实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_pregnancy")
public class Pregnancy extends BaseEntity{
	/**胎次*/
	@ManyToOne @JoinColumn(name = "parity_id")
	private Parity parity;
	/**所属羊场*/
	@ManyToOne @JoinColumn(name = "org_id")
	private Organization org;
	/**母羊*/
	@ManyToOne @JoinColumn(name = "dam_id")
	private BaseInfo dam;
	/**项目id*/
	@ManyToOne @JoinColumn(name="project_id")
	private EmbryoProject project;
	/**饲舍*/
	@ManyToOne @JoinColumn(name="paddock_id")
	private Paddock paddock;
	/**转至饲舍*/
	@ManyToOne @JoinColumn(name="to_paddock_id") 
	private Paddock toPaddock;
	@ManyToOne @JoinColumn(name="joining_id")
	private Joining joining;
	/**孕检结果*/
	@Size(max=20)
	private String result;
	/**孕检日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date pregnancyDate;
	/**繁殖类型*/
	@Size(max=20)
	private String bornType;
	/**测孕序号*/
	@Size(max=20)
	private String pregnancySeq;
	/**测孕同台数*/
	@Size(max=2)
	private String theSameFetus;
	/**预产期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date predictDate;
	/**配种日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date joiningDate;
	@Transient
	private String isAbortion;
	@Transient
	private Integer lambingCount;
	@Transient
	private Date pregnancyDateAssistStart;
	@Transient
	private Date pregnancyDateAssistEnd;
	
	public Pregnancy(Pregnancy pregnancy){
		this.result=pregnancy.getResult();
		this.pregnancyDate=pregnancy.getPregnancyDate();
		this.bornType=pregnancy.getBornType();
		this.pregnancySeq=pregnancy.getPregnancySeq();
		this.parity=pregnancy.getParity();
		this.org=pregnancy.getOrg();
		this.dam=pregnancy.getDam();
		this.project=pregnancy.getProject();
		this.paddock=pregnancy.getPaddock();
		this.recorder=pregnancy.getRecorder();
		this.theSameFetus=pregnancy.getTheSameFetus();
		this.toPaddock=pregnancy.getToPaddock();
	}
	
	/**
	 * 返回繁殖结果
	 * */
	public String returnBreedingState(){
		if(SystemM.RESULTS_PREGNANCY.equals(this.result)){
			return SystemM.BASE_INFO_BREEDING_STATE_GESTATION+this.pregnancySeq;
		}
		return SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY+this.pregnancySeq;
	}
	
	/**
	 * 返回繁殖结果
	 * */
	public String returnEtBreedingState(){
		if(SystemM.RESULTS_PREGNANCY.equals(this.result)){
			return SystemM.BASE_INFO_BREEDING_STATE_GESTATION+this.pregnancySeq;
		}
		return SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT;
	}

	public Pregnancy editReturnThis(Pregnancy pregnancy) {
		this.result=pregnancy.getResult();
		this.pregnancyDate=pregnancy.getPregnancyDate();
		this.theSameFetus=pregnancy.getTheSameFetus();
		this.recorder=pregnancy.getRecorder();
		this.ctime = pregnancy.getCtime();
		if (pregnancy.getToPaddock()!=null && pregnancy.getToPaddock().getId()!=null){
			this.toPaddock=pregnancy.getToPaddock();
		}
		return this;
	}

	/**
	 * 计算日期
	 * */
	public void setPredict(Integer parameter) {
		this.predictDate=DateUtils.dateAddInteger(this.joiningDate, parameter);
	}
}
