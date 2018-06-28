package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.beiqisoft.aoqun.base.BaseEntity;
import com.beiqisoft.aoqun.util.DateUtils;

/**
 * 繁殖状态统计实体类
 * @author 程哲旭
 * @version 1.0
 * @time  2018年1月27日上午14:04:00
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "breedingOnHand")
public class BreedingOnHand extends BaseEntity{

	/**品种*/
	@ManyToOne @JoinColumn(name="breed_id")
	private Breed breed;
	/**分厂*/
	@ManyToOne @JoinColumn(name="org_id")
	private Organization org;
	/**性别*/
	@Size(max=2)
	private String sex;
	/**繁殖状态*/
	@Size(max=4)
	private String breedingState;
	/**数量*/
	private Long sum;
	@Transient 
	private String breedName;
	
	public BreedingOnHand(BaseInfo base, Long sum){
		this.ctime = DateUtils.dateAddInteger(this.ctime, -1);
		this.breed=base.getBreed();
		this.org=base.getOrg();
		this.sex=base.getSex();
		this.breedingState=base.getBreedingState();
		this.sum=sum;
	}
}
