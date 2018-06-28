package com.beiqisoft.aoqun.entity.rep;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.MyUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "r_damBreedStateRep")
public class DamBreedStateRep extends BaseEntity{
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**品种*/
	@ManyToOne @JoinColumn(name="breed_id")
	private Breed breed;
	/**未成熟母羊*/
	private Long statelessNumber;
	/**未成熟母羊百分比*/
	@Size(max=20)
	private String statelessPercentage;
	/**空怀母羊*/
	private Long nonpregnantNumber;
	/**空怀百分比*/
	@Size(max=20)
	private String nonpregnantPercentage;
	/**已配母羊*/
	private Long crossNumber;
	/**已配母羊百分比*/
	@Size(max=20)
	private String crossPercentage;
	/**未孕母羊*/
	private Long unpregnancyNumber;
	/**未孕母羊百分比*/
	@Size(max=20)
	private String unpregnancyPercentage;
	/**怀孕母羊*/
	private Long gestationNumber;
	/**怀孕母羊百分比*/
	@Size(max=20)
	private String gestationPercentage;
	/**哺乳母羊*/
	private Long lactationNumber;
	/**哺乳母羊百分比*/
	@Size(max=20)
	private String lactationPercentage;
	/**合计*/
	private Long totalNumber;
	/**合计百分比*/
	@Size(max=20)
	private String totalPercentage;
	
	public DamBreedStateRep(Object object){
		Object[] params = (Object[]) object;
		this.org = new Organization(MyUtils.strToLong(params[0].toString()));
		this.breed = new Breed(MyUtils.strToLong(params[1].toString()));
		this.statelessNumber=MyUtils.strToLong(params[2].toString());
		this.nonpregnantNumber=MyUtils.strToLong(params[3].toString());
		this.crossNumber=MyUtils.strToLong(params[4].toString());
		this.unpregnancyNumber=MyUtils.strToLong(params[5].toString());
		this.gestationNumber=MyUtils.strToLong(params[6].toString());
		this.lactationNumber=MyUtils.strToLong(params[7].toString());
		this.totalNumber=statelessNumber+nonpregnantNumber+crossNumber
							+unpregnancyNumber+gestationNumber+lactationNumber;
		this.ctime=DateUtils.dateAddInteger(new Date(), -1);
	}
	
	public DamBreedStateRep statistics(List<DamBreedStateRep> list) {
		Breed breed = new Breed();
		breed.setBreedName("合计");
		this.breed=breed;
		this.statelessNumber=list.stream().mapToLong(DamBreedStateRep::getStatelessNumber).sum();
		this.nonpregnantNumber=list.stream().mapToLong(DamBreedStateRep::getNonpregnantNumber).sum();
		this.crossNumber=list.stream().mapToLong(DamBreedStateRep::getCrossNumber).sum();
		this.unpregnancyNumber=list.stream().mapToLong(DamBreedStateRep::getUnpregnancyNumber).sum();
		this.gestationNumber=list.stream().mapToLong(DamBreedStateRep::getGestationNumber).sum();
		this.lactationNumber=list.stream().mapToLong(DamBreedStateRep::getLactationNumber).sum();
		this.totalNumber=list.stream().mapToLong(DamBreedStateRep::getTotalNumber).sum();
		return this;
	}
	
	public DamBreedStateRep percentage(DamBreedStateRep dam){
		//未成熟
		this.statelessPercentage=MyUtils.percentage(this.statelessNumber, dam.statelessNumber);
		//空怀
		this.nonpregnantPercentage=MyUtils.percentage(this.nonpregnantNumber, dam.nonpregnantNumber);
		//配种
		this.crossPercentage=MyUtils.percentage(this.crossNumber, dam.crossNumber);
		//未孕
		this.unpregnancyPercentage=MyUtils.percentage(this.unpregnancyNumber, dam.unpregnancyNumber);
		//已孕
		this.gestationPercentage=MyUtils.percentage(this.gestationNumber, dam.gestationNumber);
		//哺乳
		this.lactationPercentage=MyUtils.percentage(this.lactationNumber, dam.lactationNumber);
		//合计
		this.totalPercentage=MyUtils.percentage(this.totalNumber, dam.totalNumber);
		return this;
	}
}
