package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.MyUtils;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "r_monthRankOnHand")
public class MonthRankOnHand extends BaseEntity{

	/**品种*/
	@ManyToOne @JoinColumn(name="breed_id")
	private Breed breed;
	/**性别*/
	@Size(max=2)
	private String sex;
	/**定级*/
	@ManyToOne @JoinColumn(name="rank_id")
	private RankTest rank;
	/**定级名称*/
	private String rankName;
	/**年龄*/
	private Long age;
	/**月龄*/
	private Long moonAge;
	/**数量*/
	private Long num;
	/**品种名称*/
	private String breedName;
	
	private Long orgId;
		
	public MonthRankOnHand(Breed breed, String sex, RankTest rank, String moonAge, Long num,Long orgId){
		this.ctime = DateUtils.dateAddInteger(this.ctime, -1);
		this.breed = breed;
		this.sex = sex;
		this.rank = rank;
		if (this.rank!=null){
			rankName=this.rank.getRank();
		}
		this.moonAge = MyUtils.strToLong(moonAge)>=72L?72:MyUtils.strToLong(moonAge);
		this.age =(MyUtils.strToLong(moonAge)/12)>=6L?6:(MyUtils.strToLong(moonAge)/12);
		this.num = num;
		this.breedName=breed.getBreedName();
		this.orgId=orgId;
	}
	
	public MonthRankOnHand (Object[] o){
		this.breedName=o[0].toString();
		this.sex=o[1].toString();
		this.rankName=o[2]==null?null:o[2].toString();
		this.age= MyUtils.strToLong(o[3].toString());
		this.moonAge = MyUtils.strToLong(o[4].toString());
		this.num= MyUtils.strToLong(o[5].toString());
	}
}
