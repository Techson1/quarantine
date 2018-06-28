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
import com.beiqisoft.aoqun.config.SystemM;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 母羊生产实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_lambingDam")
public class LambingDam extends BaseEntity{
	/**母羊*/
	@ManyToOne @JoinColumn(name = "dam_id")
	private BaseInfo dam; 
	/**胎次*/
	@ManyToOne @JoinColumn(name = "parity_id")
	private Parity parity;
	/**羊场*/
	@ManyToOne @JoinColumn(name = "org_id")
	private Organization org;
	/**配种记录*/
	@ManyToOne @JoinColumn(name="joining_id")
	private Joining joining;
	//尚未实现功能
	/**项目*/
	@ManyToOne @JoinColumn(name="project_id")
	private EmbryoProject project;
	/**产羔日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date bornDate;
	@Transient
	private Date bornDateAssistStart;
	@Transient
	private Date bornDateAssistEnd;
	/**产羔总数*/
	private Integer bornTimes;
	/**产公羔数*/
	private Integer bornCountF;
	/**活公羔数*/
	private Integer aliveCountF;
	/**公死胎数*/
	private Integer deadCountF;
	/**畸形公羔数*/
	private Integer badCountF;
	/**产母羔数*/
	private Integer bornCountM;
	/**活母羔数*/
	private Integer aliveCountM;
	/**母死胎数*/
	private Integer deadCountM;
	/**畸形母羔数*/
	private Integer badCountM;
	/**出生难易*/
	@Size(max=20)
	private String easyFlag;
	/**繁殖类型*/
	@Size(max=2)
	private String bornType;
	/**是否能哺乳*/
	@Size(max=2)
	private String canBreed;
	/**母性*/
	@Size(max=2)
	private String motherhood;
	/**乳房炎*/ 
	@Size(max=20)
	private String mastitis;
	/**泌乳等级*/
	@Size(max=3)
	private String breas;
	/**数量*/
	@Transient
	private String number;
	/**成活率*/
	@Transient
	private String survival;
	/**公羊*/
	@Transient
	private BaseInfo sire;
	/**品种*/
	@Transient
	private BaseInfo breed;
	/**同胎数*/
	@Transient
	private String theSameFetus;
	/**受体母羊*/
	@Transient
	private BaseInfo receptor;
	@Transient
	private String joiningSeq;
	
	//String joiningSeq
	public LambingDam(Long id,Integer bornTimes, Integer aliveCountF,Integer aliveCountM,
			Integer deadCountF,Integer deadCountM,Integer badCountF,Integer badCountM){
		this.id=id;
		//this.joiningSeq=joiningSeq;
		this.bornTimes=IntegerTOInt(bornTimes);
		this.aliveCountF=IntegerTOInt(aliveCountF);
		this.aliveCountM=IntegerTOInt(aliveCountM);
		this.deadCountF=IntegerTOInt(deadCountF);
		this.deadCountM=IntegerTOInt(deadCountM);
		this.badCountF=IntegerTOInt(badCountF);
		this.badCountM=IntegerTOInt(badCountM);
	}
	
	private int IntegerTOInt(Integer num1){
		if (num1==null){
			return 0;
		}
		return num1;
	}
	
	public LambingDam update(LambingDam lambingDam){
		this.bornDate=lambingDam.getBornDate();
		this.easyFlag=lambingDam.getEasyFlag();
		this.motherhood=lambingDam.getMotherhood();
		this.mastitis=lambingDam.getMastitis();
		this.breas=lambingDam.getBreas();
		this.canBreed=lambingDam.getCanBreed();
		return this;
	}

	/**
	 * 添加羔羊计算
	 * */
	public LambingDam addBase(BaseInfo baseInfo) {
		init();
		lambingCount(baseInfo,1);
		return this;
	}
	
	/**
	 * 删除羔羊计算
	 * */
	public LambingDam delBase(BaseInfo baseInfo){
		lambingCount(baseInfo,-1);
		return this;
	}
	
	private void init(){
		this.bornTimes=this.bornTimes==null?0:this.bornTimes;
		this.bornCountF=this.bornCountF==null?0:this.bornCountF;
		this.aliveCountF=this.aliveCountF==null?0:this.aliveCountF;
		this.deadCountF=this.deadCountF==null?0:this.deadCountF;
		this.badCountF=this.badCountF==null?0:this.badCountF;
		this.bornCountM=this.bornCountM==null?0:this.bornCountM;
		this.aliveCountM=this.aliveCountM==null?0:this.aliveCountM;
		this.deadCountM=this.deadCountM==null?0:this.deadCountM;
		this.badCountM=this.badCountM==null?0:this.badCountM;
	}
	
	/**
	 * 母羊产羔计算
	 * */
	private void lambingCount(BaseInfo baseInfo,int i){
		this.bornTimes+=i;
		if (SystemM.PUBLIC_SEX_SIRE.equals(baseInfo.getSex())){//计算公羊
			this.bornCountF+=i;
			if(SystemM.CODE_BIRTH_STATE_NORMAL.equals(baseInfo.getBornStatus())){
				this.aliveCountF+=i;
			}
			if (SystemM.CODE_BIRTH_STATE_STILLBIRTH.equals(baseInfo.getBornStatus())){
				this.deadCountF+=i;
			}
			if (SystemM.CODE_BIRTH_STATE_MALFORMATION.equals(baseInfo.getBornStatus())){
				this.badCountF+=i;
			}
		}
		else{//计算母羊
			this.bornCountM+=i;
			if(SystemM.CODE_BIRTH_STATE_NORMAL.equals(baseInfo.getBornStatus())){
				this.aliveCountM+=i;
			}
			if (SystemM.CODE_BIRTH_STATE_STILLBIRTH.equals(baseInfo.getBornStatus())){
				this.deadCountM+=i;
			}
			if (SystemM.CODE_BIRTH_STATE_MALFORMATION.equals(baseInfo.getBornStatus())){
				this.badCountM+=i;
			}
		}
	}
}