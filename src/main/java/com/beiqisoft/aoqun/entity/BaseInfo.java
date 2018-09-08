package com.beiqisoft.aoqun.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 羊只基础信息表实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_baseInfo")
public class BaseInfo extends BaseEntity{
	/**品种*/
	@ManyToOne @JoinColumn(name = "breed_id")
	private Breed breed;
	/**母号id*/
	@ManyToOne @JoinColumn(name = "dam_id")
	private BaseInfo dam;
	/**父号id*/
	@ManyToOne @JoinColumn(name="sire_id")
	private BaseInfo sire;
	/**代养母羊*/
	@ManyToOne @JoinColumn (name="foster_dam_id")
	private BaseInfo fosterDam;
	/**受体母羊*/
	@ManyToOne @JoinColumn(name="ewes_id")
	private BaseInfo ewes;
	/**分厂id*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**母羊产羔*/
	@ManyToOne @JoinColumn(name="lambing_dam_id")
	private LambingDam lambingDam;
	/**购入场*/
	@ManyToOne @JoinColumn(name="source_id")
	private Organization source;
	/**供应商*/
	@ManyToOne @JoinColumn(name="customer_id")
	private Contact customer;
	/**圈舍*/
	@ManyToOne @JoinColumn(name="paddock_id")
	private Paddock paddock;
	/**定级*/
	@ManyToOne @JoinColumn(name="rank_id")
	private RankTest rank;
	@Transient
	private String rankName;
	/**可视耳标*/
	@Size(max=20)
	private String code;
	/**电子耳号*/
	@Size(max=20)
	private String rfid;
	/**出生日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birthDay;
	/**入场日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date enterhDay;
	@Transient
	private Date enterhDayAssistStart;
	@Transient
	private Date enterhDayAssistEnd;
	/**性别*/
	@Size(max=4)
	private String sex;
	/**同胎数*/
	@Size(max=20)
	private String theSameFetus;
	/**品种类型 没有用到*/
	@Size(max=4)
	private String breedingTypeId;
	/**状态*/ 
	@Size(max=4)
	private String lifeStatusId;
	/**出生状态*/
	@Size(max=4)
	private String state;
	/**销售状态*/
	@Size(max=2)
	private String saleStatus;
	/**入场类型
	 * 已由sourceType代替？
	 *@deprecated
	 * */
	@Size(max=2)
	private String enterType;
	/**生长阶段*/
	@Size(max=2)
	private String bornStatus;
	/**出生重*/
	private Double initialWeigh;
	/**是否外购*/
	@Size(max=2)
	private String isOutsourcing;
	/**繁殖状态*/
	@Size(max=20)
	private String breedingState;
	/**羊只来源*/
	private String sourceType;
	/**库存状态*/
	private Long physiologyStatus;
	@Transient
	private String phyCode;
	//TODO 表的设计不合理
	/**出库日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deliveryDate;
	@Transient
	private Date deliveryDateAssistStart;
	@Transient
	private Date deliveryDateAssistEnd;
	@Size(max=20)
	private String reviewing;
	/**复合日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date auditDate;
	/**是否审核*/
	private String isAudit;
	
	//TODO 加入基因等级，基因等级不是核心等级不能定级为核心群
	/**断奶日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date weaningDate;
	/**断奶重*/
	private Double weaningWeight;
	/**断奶天数*/
	private Integer weaningDay;
	/**生产难易*/
	private String easyFla;
	/**是否归档*/
	@Size(max=2)
	private String flag;
	/**基因等级*/
	@Size(max=2)
	private String geneticLevel;
	/**月龄*/
	private String moonAge;
	/**是否在库*/
	@Size(max=2)
	private String isStore;
	//临时对象字段不存在数据库中
	/**母耳号*/
	@Transient 
	private String damCode;
	/**母品种*/
	@Transient @JsonIgnore
	private Long damBreed;
	/**母出生日期*/
	@Transient
	private Date damBirthDay;
	/**父耳号*/
	@Transient 
	private String sireCode;
	/**父品种*/
	@Transient @JsonIgnore
	private Long sireBreed;
	/**父出生日期*/
	@Transient @JsonIgnore
	private Date sireBirthDay;
	/**代养母羊耳号*/
	@Transient @JsonIgnore
	private String fosterDamCode;
	/**受体母羊耳号*/
	@Transient @JsonIgnore
	private String ewesCode;
	/**品相*/
	@OneToOne(mappedBy="base") 
	private Looks looks;
	@Transient
	private Date birthDayAssistStart;
	@Transient
	private Date birthDayAssistEnd;
	@Transient
	private String breedName;
	/**开始月龄*/
	@Transient
	private String startMoonAge;
	/**结束月龄*/
	@Transient
	private String endMoonAge;
	/**繁殖明细*/
	@Transient
	private String breedingStateDetail;
	@Transient
	private String paddockName;
	public BaseInfo(String code,Date birthDay,Long breedId){
		this.code=code;
		this.birthDay=birthDay;
		this.breed=new Breed(breedId);
	}
	
	public BaseInfo(Long id){
		this.id=id;
	}
	
	/**
	 * 羊只登记添加
	 * @param day
	 * 			成年羊天数
	 * @param youthDay 
	 * @param growDay 
	 * @param sireDay 
	 * @param dam
	 * 			母羊
	 * @param sire
	 * 			公羊
	 * @return BaseInfo
	 * */
	public BaseInfo registerAddReturnThis(int damDay,int sireDay, int growDay, int youthDay, BaseInfo dam,BaseInfo sire){
		this.isOutsourcing=SystemM.PUBLIC_TRUE;
		this.dam=dam;
		this.sire=sire;
		this.flag=SystemM.PUBLIC_FALSE;
		
		int age=DateUtils.dateToDayAge(this.birthDay);
		//判断是否是性成熟母羊
		this.breedingState=SystemM.BASE_INFO_BREEDING_STATE_STATELESS;
		if (SystemM.PUBLIC_SEX_DAM.equals(this.sex) && age>=damDay){
			this.breedingState=SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT;
		}
		if (SystemM.PUBLIC_SEX_SIRE.equals(this.sex) && age>=sireDay){
			this.breedingState=SystemM.BASE_INFO_BREEDING_NOT_SEMEN;
		}
		if (age<youthDay){
			this.bornStatus=SystemM.BASE_INFO_PHYSIOLOGY_STATUS_LAMB;
		}
		if (age>=growDay){
			this.bornStatus=SystemM.BASE_INFO_PHYSIOLOGY_STATUS_GROW;
		}
		else{
			this.bornStatus=SystemM.BASE_INFO_PHYSIOLOGY_STATUS_YOUTH;
		}
		//外购登记修改
		if (this.customer!=null && this.customer.getId()==null){
			this.customer=null;
		}
		if (this.source!=null && this.source.getId()==null){
			this.source=null;
		}
		return this;
	}
	public BaseInfo(String code,Date birthDay,String breedName,String paddockName,String moonAge,String breedingState) {
		this.code = code ;
		this.birthDay = birthDay;
		this.breedName = breedName;
		this.paddockName = paddockName;
		this.moonAge = moonAge;
		this.breedingState = breedingState;
	}
	/**
	 * 判断羊只耳号是否存在
	 * @param code
	 * 			电子耳号或可视耳号
	 * @return 耳号存在返回ture否则返回false
	 * */
	public boolean isEarTag(String code){
		if (this.code.equals(code) || this.rfid.equals(code)){
			return true;
		}
		return false;
	}
	
	public BaseInfo(BaseInfo baseInfo,String code){
	   this.code=code;
	   this.rfid=baseInfo.getRfid();
	   this.birthDay=baseInfo.getBirthDay();
	   this.enterhDay=baseInfo.getEnterhDay();
	   this.sex=baseInfo.getSex();
	   this.theSameFetus=baseInfo.getTheSameFetus(); 
	   this.breedingTypeId=baseInfo.getBreedingTypeId();
	   this.lifeStatusId=baseInfo.getLifeStatusId();
	   this.saleStatus=baseInfo.getSaleStatus();
	   this.enterType=baseInfo.getEnterType();
	   this.bornStatus=baseInfo.getBornStatus();
	   this.initialWeigh=baseInfo.getInitialWeigh();
	   this.isOutsourcing=baseInfo.getIsOutsourcing();
	   this.breedingState=baseInfo.getBreedingState();
	   this.sourceType=baseInfo.getSourceType();
	   this.breed=baseInfo.getBreed();
	   this.dam=baseInfo.getDam(); 
	   this.sire=baseInfo.getSire();  
	   this.org=baseInfo.getOrg();
	   this.customer=baseInfo.getCustomer(); 
	   this.weaningWeight=baseInfo.getWeaningWeight();
	   this.weaningDay=baseInfo.getWeaningDay();
	   this.easyFla=baseInfo.getEasyFla();
	   this.flag=baseInfo.getFlag();
	   this.source=baseInfo.getSource();
	   this.recorder=baseInfo.getRecorder();
	   this.paddock=baseInfo.getPaddock();
	}
	
	/**
	 * 羊只档案添加
	 * */
	public BaseInfo recordAddReturnThis(String code){
		this.code=code;
		this.flag=SystemM.PUBLIC_FALSE;
		return this;
	}
	
	/**
	 * 修改繁殖参数并返回自身
	 * */
	public BaseInfo setBreedingStateReutrnThis(String breedingState){
		if(breedingState!=null) {
			this.breedingState=breedingState;
		}else {
			this.breedingState = "25";
		}
		return this;
	}
	
	/**
	 * 剥离后缀参数
	 * */
	public String peelBreedingState(){
		return this.breedingState.length()==2?this.breedingState
						:this.breedingState.substring(0,2);
	}

	public BaseInfo update(BaseInfo baseInfo) {
		return this;
	}

	public BaseInfo setRfidReturnThis(String rfid) {
		this.rfid=rfid;
		return this;
	}

	public BaseInfo setGeneticLevelReturnThis(RankTest rank) {
		this.rank=rank;
		return this;
	}

	public BaseInfo audit(String type,String reviewing) {
		this.physiologyStatus=MyUtils.strToLong(type) ;
		this.auditDate=new Date();
		this.isAudit="1";
		this.reviewing=reviewing;
		return this;
	}

	public BaseInfo notAudit(String type) {
		this.physiologyStatus=MyUtils.strToLong(SystemM.type(type));
		this.auditDate=null;
		this.isAudit="0";
		return this;
	}
	
	/**
	 * 获取断奶重或出生重
	 * */
	public Weight getWeight(){
		Weight weigth =new Weight();
		if (this.getWeaningDate()!=null){
			weigth.setWeighthDate(this.getWeaningDate());
			weigth.setWeights(""+this.getWeaningWeight());
			weigth.setDayAge(DateUtils.dateSubDate(this.getWeaningDate(), this.getBirthDay()));
		}
		else{
			weigth.setWeighthDate(this.getBirthDay());
			weigth.setWeights(""+this.getInitialWeigh());
			weigth.setDayAge(0);
		}
		return weigth;
	}

	public void setLevel() {
		String damGenetic=this.dam.getRank()!=null?this.dam.getRank().getRank():null;
		String sireGenetic=this.sire.getRank()!=null?this.sire.getRank().getRank():null;
		if (damGenetic!=null && sireGenetic!=null){
			this.geneticLevel=MyUtils.strCmpStr(damGenetic,sireGenetic);
		}
	}
}
