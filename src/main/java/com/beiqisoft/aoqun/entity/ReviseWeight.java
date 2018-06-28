package com.beiqisoft.aoqun.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.beiqisoft.aoqun.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity 
@Table(name = "t_reviseWeight")
public class ReviseWeight extends BaseEntity{

	@ManyToOne @JoinColumn(name="base_id")
	private BaseInfo base;
	/**三月体重*/
	@Size(max=20)
	private String threeWeight;
	/**四月体重*/
	@Size(max=20)
	private String fourWeight;
	/**五月体重*/
	@Size(max=20)
	private String fiveWeight;
	/**六月体重*/
	@Size(max=20)
	private String sixWeight;
	/**七月体重*/
	@Size(max=20)
	private String sevenWeight;
	/**八月体重*/
	@Size(max=20)
	private String eightWeight;
	/**九月体重*/
	@Size(max=20)
	private String nineWeight;
	/**十月体重*/
	@Size(max=20)
	private String tenWeight;
	/**十一月体重*/
	@Size(max=20)
	private String elevenWeight;
	/**十二月体重*/
	@Size(max=20)
	private String twelveWeight;
	/**十八月体重*/
	@Size(max=20)
	private String eighteenWeight;
	/**二十四月体重*/
	@Size(max=20)
	private String twentyFourWeight;
	/**月龄*/
	@Transient
	private String monthAge;
	/**断奶日龄*/
	@Transient
	private String weaningAge;
	/**耳号*/
	@Transient
	private String code;
	/**品种名称*/
	@Transient
	private String breedName;
	/**性别*/
	@Transient
	private String sex;
	/**出生日期*/
	@Transient
	private String birthDay;
	/**出生重*/
	@Transient
	private String initialWeigh;
	/**断奶日龄*/
	@Transient
	private String weaningDay;
	/**断奶重*/
	@Transient
	private String weaningWeight;
	
	public void calculate(int month,String total){
		if (3==month) this.threeWeight=""+total;
		if (4==month) this.fourWeight=""+total;
		if (5==month) this.fiveWeight=""+total;
		if (6==month) this.sixWeight=""+total;
		if (7==month) this.sevenWeight=""+total;
		if (8==month) this.eightWeight=""+total;
		if (9==month) this.nineWeight=""+total;
		if (10==month) this.tenWeight=""+total;
		if (11==month) this.elevenWeight=""+total;
		if (12==month) this.twelveWeight=""+total;
		if (18==month) this.eighteenWeight=""+total;
		if (24==month) this.twentyFourWeight=""+total;
	}
}
