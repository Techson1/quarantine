package com.beiqisoft.aoqun.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 选配方案实体类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_breedingPlan")
public class BreedingPlan extends BaseEntity{

	/**创建日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdTime;
	/**修改日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastModifyTime=new Date();
	/**名称*/
	@Size(max=20)
	private String name;
	/**状态*/
	@Size(max=20)
	private String stauts;
	@ManyToOne @JoinColumn(name = "dam_breed_id")
	private Breed damBreed;
	@ManyToOne @JoinColumn(name = "sire_breed_id")
	private Breed sireBreed;
	@Size(max=2)
	private String flag;
	
	@OneToMany(mappedBy = "breedingPlan",cascade={CascadeType.PERSIST})
	@OrderBy("ctime desc")
	private List<BreedingPlanDetailDam> breedingPlanDetailDams;
	
	@OneToMany(mappedBy = "breedingPlan",cascade={CascadeType.PERSIST})
	private List<BreedingPlanDetailSire> breedingPlanDetailSires;
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	
	@Transient
	private BreedingPlanDetailSire one;
	@Transient
	private BreedingPlanDetailSire two;
	@Transient
	private BreedingPlanDetailSire three;
	@Transient
	private BreedingPlanDetailSire four;
	/**母羊数*/
	private String breedingDamCount;
	
	//对象字段不存在数据库中
	public BreedingPlan setFlagReturnThis(String flag) {
		this.flag=flag;
		return this;
	}
	public BreedingPlan isRequest() {
		for (BreedingPlanDetailSire b:this.breedingPlanDetailSires){
			if ("0".equals(b.getSireType())){
				this.one=b;
			}
			if ("1".equals(b.getSireType())){
				this.two=b;
			}
			if ("2".equals(b.getSireType())){
				this.three=b;
			}
			if ("3".equals(b.getSireType())){
				this.four=b;
			}
		}
		return this;
	}
}
