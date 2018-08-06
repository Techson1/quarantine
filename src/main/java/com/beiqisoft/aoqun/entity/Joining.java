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
import com.beiqisoft.aoqun.util.MyUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 配种实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_joining")
public class Joining extends BaseEntity{
	/**母羊*/
	@ManyToOne @JoinColumn(name="dam_id")
	private BaseInfo dam;
	/**公羊*/
	@ManyToOne @JoinColumn(name="sire_id")
	private BaseInfo sire;
	/**胎次*/
	@ManyToOne @JoinColumn(name="parity_id")
	private Parity parity;
	/**选配方案*/
	@ManyToOne @JoinColumn(name="breedingPlan_id")
	private BreedingPlan breedingPlan;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**后代品种*/
	@ManyToOne @JoinColumn(name="childBreed_id")
	private Breed childBreed;
	
	//胚胎移植的项目
	/**项目id*/
	@ManyToOne @JoinColumn(name="project_id")
	private EmbryoProject project;
	
	//暂时未实行对接或未知
	@Transient 
	private Long group_id;
	/**基因等级*/
	@Size(max=20)
	private String  geneticLevel;
	/**发情方式*/
	@Size(max=20)
	private String sexStatus;
	
	/**配种序号*/
	@Size(max=20)
	private String joiningSeq;
	/**配种类型*/
	@Size(max=21)
	private String joiningType;
	/**配种时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date joiningDate;
	/**是否核心群 弃用*/
	@Size(max=2)
	private String coreFlag;
	/**自然繁殖 胚胎移植 已弃用*/
	@Size(max=2)
	private String bornType;
	/**是否最新配种*/
	@Size(max=2)
	private String isNewestJoining;
	/**测孕结果*/
	@Size(max=20)
	private String result;
	/**是否在选配方案*/
	@Size(max=20)
	private String isBreedingPlan;
	/**一测日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date onePregnancyDate;
	/**二测日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date twoPregnancyDate;
	@Transient
	private Date joiningDateAssistStart;
	@Transient
	private Date joiningDateAssistEnd;
	@Transient
	private Paddock paddock;
	
	public void dispose(String joiningSeq,Breed childBreed,String coreFlag){
		this.joiningSeq=joiningSeq;
		this.childBreed=childBreed;
		this.coreFlag=coreFlag;
	}
	
	public int getJoiningSeqReturnInteger(){
		return Integer.parseInt(this.joiningSeq);
	}

	public Joining setResultReturnThis(String result) {
		this.result=result;
		return this;
	}

	public Joining(Joining joining){
		this.dam=joining.getDam();
		this.sire=joining.getSire();
		this.parity=joining.getParity();
		this.breedingPlan=joining.getBreedingPlan();
		this.org=joining.getOrg();
		this.childBreed=joining.getChildBreed();
		this.project=joining.getProject();
		this.group_id=joining.getGroup_id();
		this.geneticLevel=joining.getGeneticLevel();
		this.sexStatus=joining.getSexStatus();
		this.joiningSeq=joining.getJoiningSeq();
		this.joiningType=joining.getJoiningType();
		this.joiningDate=joining.getJoiningDate();
		this.coreFlag=joining.getCoreFlag();
		this.bornType=joining.getBornType();
		this.isNewestJoining=joining.getIsNewestJoining();
		this.result=joining.getResult();
		this.isBreedingPlan=joining.getIsBreedingPlan();
		this.recorder=joining.getRecorder();
		this.paddock=joining.getPaddock();
	}
	
	public Joining AI(BaseInfo dam,BaseInfo sire,Parity parity,EmbryoProject project,Date joniningDate) {
		this.dam=dam;
		this.sire=sire;
		this.parity=parity;
		this.project=project;
		this.joiningDate=joniningDate;
		this.isNewestJoining=SystemM.PUBLIC_TRUE;
		this.joiningSeq="1";
		return this;
	}

	public Joining setUpdate(BaseInfo sire, String sexStatus,
			String joiningType, Date joiningDate, String recorder, Breed childBreed) {
		this.sire=sire;
		this.sexStatus=sexStatus;
		this.joiningType=joiningType;
		this.joiningDate=joiningDate;
		this.recorder=recorder;
		this.childBreed=childBreed;
		this.setLevel();
		return this;
	}

	public void setLevel() {
		String damGenetic=this.dam.getRank()!=null?this.dam.getRank().getRank():null;
		String sireGenetic=this.sire.getRank()!=null?this.sire.getRank().getRank():null;
		if (damGenetic!=null && sireGenetic!=null){
			this.geneticLevel=MyUtils.strCmpStr(damGenetic,sireGenetic);
		}
	}

	public void PregnancyDate(int one,int two) {
		this.onePregnancyDate=DateUtils.dateAddInteger(joiningDate,one); //joiningDate+one;
		this.twoPregnancyDate=DateUtils.dateAddInteger(joiningDate, two);
	}
}
